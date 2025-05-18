package local.a24miguelod.cookflow.utils

import android.util.Log
import local.a24miguelod.cookflow.domain.model.Ingrediente
import local.a24miguelod.cookflow.domain.model.IngredienteReceta
import local.a24miguelod.cookflow.domain.model.Receta
import local.a24miguelod.cookflow.domain.model.RecetaPaso

/*
  Funciones muy ad hoc para obtener un objeto Receta del dominio
  a partir de un texto en markdown.

  Solo a efectos de demostracion para tener una fuente de recetas

  El parse no es (ni pretende) ser robusto.
 */

fun parseMarkdownReceta(texto:String, id:String):Receta {

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

        when {
            linea.startsWith("# ") && (seccionActual == "titulo") -> {
                titulo = linea.removePrefix("# ").trim()
                seccionActual = "descripcion"
            }

            linea.startsWith("![") -> {
                val match = Regex("!\\[(.*?)\\]\\((.*?)\\)").find(linea)
                urlImagen = match?.groups?.get(2)?.value ?: match?.groups?.get(2)?.value
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
                        ingrediente = Ingrediente(
                            nombre = nombre
                        ),
                        cantidad = cantidad
                    )
                )
            }

            linea.startsWith("## Prepa") && seccionActual == "ingredientes" -> {
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
            Regex("^Duraci[oó]n:", RegexOption.IGNORE_CASE).find(linea) != null && (seccionActual == "pasos") -> {
                pasoDuracion = linea.split(" ", limit = 3)[1].toFloat()
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
    Log.d("FOTOS", urlImagen.toString())
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