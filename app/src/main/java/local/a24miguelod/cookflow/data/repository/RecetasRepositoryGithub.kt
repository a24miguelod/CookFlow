package local.a24miguelod.cookflow.data.repository


import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import local.a24miguelod.cookflow.data.github.model.DirectoryGithub
import local.a24miguelod.cookflow.domain.model.RecetaPaso

private const val TAG = "RecetasRepositoryGithub"

class RecetasRepositoryGithub : RecetasRepository {

    private var githubReporecetasUrl: String = "http://192.168.1.138:8000"
    private var githubBaseDir: String = "http://192.168.1.138:8000/files/"
    private val client:HttpClient = HttpClient(CIO) {
        expectSuccess = true        // https://ktor.io/docs/client-create-and-configure.html#configure-client
        install(ContentNegotiation) {
            json(Json{
                // https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json-builder/
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    // Obtener la lista de recetas
    override suspend fun getRecetasConFlow(): Flow<List<Receta>> = flow {

        val resultadoParcial = mutableListOf<Receta>()

        // Leo los ficheros del directorio en particular y creo una receta para cada una
        // de las entradas
        val lsDirectory: List<DirectoryGithub> = client.get(githubReporecetasUrl).body()
        Log.d(TAG, lsDirectory.toString())

        lsDirectory.forEach {
            val recetaFile:String = client.get(it.downloadUrl!!).body()
            val receta = parseMarkdownReceta(recetaFile, it.name.toString(), )
            resultadoParcial.add(receta)
            emit(resultadoParcial.toList()) // Emitimos una copia
        }

    }

    private fun parseMarkdownReceta(texto:String, id:String):Receta {

        val lineas = texto.lines() + "### Paso"

        var titulo = ""
        var urlImagen: String? = null
        val descripcion = StringBuilder()
        val ingredientes = mutableListOf<IngredienteReceta>()
        val pasos = mutableListOf<RecetaPaso>()

        var seccionActual = "titulo"

        var pasoTitulo = ""
        var pasoDescripcion = StringBuilder()
        var pasoDuracion = 0f


        for (linea in lineas.map { it.trim() } ) {
//            Log.d(TAG, "PARSEO: $linea")
//            Log.d(TAG, "seccion actual:$seccionActual")
//            Log.d(TAG, "Titulo $titulo, descripcion $descripcion")
//            Log.d(TAG, "lista de ingredietnes: ${ingredientes.toString()}")
//            Log.d(TAG, "lista de pasos: ${pasos.toString()}")
//            Log.d(TAG, "paso titulo $pasoTitulo")
//            Log.d(TAG, "paso titulo ${pasoDuracion.toString()}")
//            Log.d(TAG, "paso titulo $pasoDescripcion")

            when {
                linea.startsWith("# ") && (seccionActual == "titulo") -> {
                    titulo = linea.removePrefix("# ").trim()
                    seccionActual = "descripcion"
                }

                linea.startsWith("![") -> {
                    val match = Regex("!\\[(.*?)\\]\\((.*?)\\)").find(linea)
                    urlImagen = match?.groups?.get(1)?.value ?: match?.groups?.get(2)?.value
                    Log.d(TAG, "IMagen!")
                    Log.d(TAG, urlImagen.toString())
                }

                linea.startsWith("## Ingredientes") -> {
                    seccionActual = "ingredientes"
                }

                linea.startsWith("* ") && (seccionActual == "ingredientes") -> {
                    val (nombre, cantidad) = linea.removePrefix("* ")
                        .split(":", limit = 2)
                        .map { it.trim() }
                        .let {
                            val nombre = it.getOrNull(0) ?: ""
                            val cantidad = it.getOrNull(1) ?: ""
                            nombre to cantidad
                        }
                    ingredientes.add(
                        IngredienteReceta(
                            ingrediente = Ingrediente(nombre),
                            cantidad = cantidad
                        )
                    )
                }

                linea.startsWith("## Preparacion") && seccionActual == "ingredientes" -> {
                    seccionActual = "pasos"
                }

                linea.startsWith("### ") && seccionActual == "pasos" -> {

                    if (pasoDuracion != 0f) { // No es el primer paso
                        pasos.add(
                            RecetaPaso(
                                resumen = pasoTitulo,
                                duracion = pasoDuracion,
                                detallelargo = aplanaParrafos(pasoDescripcion),
                            )
                        )
                    }
                    pasoTitulo = linea.removePrefix("### ").trim()
                    pasoDuracion = 0f
                    pasoDescripcion.clear()

                }
                linea.startsWith("Duracion: ") && (seccionActual == "pasos") -> {
                    pasoDuracion = linea.removePrefix("Duracion: ")
                        .split(" ", limit = 2).get(0).toFloat()
                }

                //else
                seccionActual =="descripcion" -> {
                    descripcion.appendLine(linea)
                }
                seccionActual =="pasos" -> {
                    pasoDescripcion.appendLine(linea)
                }
            }
        }

        val receta = Receta(
            nombre = titulo,
            id = id,
            descripcion = aplanaParrafos(descripcion),
            ingredientes = ingredientes,
            pasos = pasos,
            urlimagen = urlImagen.toString()
        )
        Log.d(TAG, receta.toString())
        return receta
    }

    private fun aplanaParrafos(texto:StringBuilder):String {
        val parrafos = mutableListOf<String>()
        val actual = StringBuilder()
        val lineas = texto.toString().lines()


        for (linea in lineas.map { it.trim() }) {
            if (linea.isEmpty()) {
                if (actual.isNotEmpty()) {
                    parrafos.add(actual.toString().trim())
                    actual.clear()
                }
            } else {
                if (actual.isNotEmpty()) actual.append(" ")
                actual.append(linea)
            }
        }

        // Agrega el último párrafo si quedó algo pendiente
        if (actual.isNotEmpty()) {
            parrafos.add(actual.toString().trim())
        }

        return parrafos.joinToString("\n\n")
    }

    override suspend fun getReceta(id: String): Receta {

        val recetaFile:String = client.get("$githubBaseDir$id").body()
        val receta = parseMarkdownReceta(recetaFile,id )

        Log.d(TAG, "getReceta $githubBaseDir$id")
        return receta
    }


}