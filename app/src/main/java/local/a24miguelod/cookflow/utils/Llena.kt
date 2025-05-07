package local.a24miguelod.cookflow.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


import kotlin.random.Random

private const val TAG = "Llena.kt"

// 1. Lista de ingredientes predefinidos
val ingredientes = listOf(
    "Aceite de oliva",
    "Sal",
    "Pimienta",
    "Azúcar",
    "Harina",
    "Huevos",
    "Leche",
    "Mantequilla",
    "Ajo",
    "Cebolla",
    "Tomate",
    "Pimiento",
    "Pepino",
    "Zanahoria",
    "Papa",
    "Limón",
    "Vinagre",
    "Arroz",
    "Pasta",
    "Pan",
    "Queso",
    "Yogur",
    "Pollo",
    "Carne molida",
    "Pescado",
    "Atún",
    "Aceitunas",
    "Almendras",
    "Nueces",
    "Miel",
    "Canela",
    "Orégano",
    "Albahaca",
    "Perejil",
    "Cilantro",
    "Comino",
    "Curry",
    "Chocolate",
    "Vainilla",
    "Levadura"
)

// 2. Función para cargar los ingredientes en Firestore
fun cargarIngredientes() {
    val db = FirebaseFirestore.getInstance()

    try {
        // Usamos batch para operación atómica (opcional, pero recomendado)
        val batch = db.batch()

        ingredientes.forEach { nombreIngrediente ->
            val ingredienteRef = db.collection("ingredientes").document()
            val data = hashMapOf(
                "nombre" to nombreIngrediente
            )
            batch.set(ingredienteRef, data)
        }

        batch.commit()
        println("✅ ${ingredientes.size} ingredientes creados exitosamente")
    } catch (e: Exception) {
        println("❌ Error al crear ingredientes: ${e.message}")
    }
}


// 1. Datos de ejemplo
val nombresRecetas = listOf(
    "Paella valenciana",
    "Tacos al pastor",
    "Pasta carbonara",
    "Ensalada César",
    "Curry de garbanzos",
    "Sushi casero",
    "Risotto de champiñones",
    "Tarta de manzana"
)

val descripciones = listOf(
    "Una deliciosa receta tradicional...",
    "Perfecta para compartir con amigos...",
    "Clásico italiano que nunca falla...",
    "Fresca y llena de sabor...",
    "Vegetariano y lleno de proteínas..."
)

val imagenes = listOf(
    "https://picsum.photos/200/300",
    "https://picsum.photos/201/300",
    "https://picsum.photos/202/300",
    "https://picsum.photos/203/300",
    "https://picsum.photos/200/300",
    "https://picsum.photos/210/300",
    "https://picsum.photos/220/300",
    "https://picsum.photos/250/300",
    "https://picsum.photos/300/300",
    "https://picsum.photos/200/320",
    "https://picsum.photos/200/200",
    "https://picsum.photos/200/220",
    "https://picsum.photos/200/111",

    )

// 2. Función para generar pasos aleatorios
fun generarPasosAleatorios(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "paso" to "Preparar los ingredientes",
            "comentarios" to "Lavar y cortar todo antes de empezar",
            "duracion" to 10.5f
        ),
        mapOf(
            "paso" to "Cocinar a fuego medio",
            "comentarios" to "Revolver constantemente para evitar que se pegue",
            "duracion" to 15.0f
        ),
        mapOf(
            "paso" to "Decorar y servir",
            "comentarios" to "Añadir hierbas frescas al final",
            "duracion" to 5.0f
        )
    )
}

// 3. Función para generar referencias a ingredientes (asume que ya existen en Firestore)
fun generarIngredientesAleatorios(db: FirebaseFirestore): List<Map<String, String>> {
    val ingredientesRefs = mutableListOf<Map<String, String>>()
    repeat(Random.nextInt(3, 6)) { // Entre 3 y 5 ingredientes por receta
        ingredientesRefs.add(
            mapOf(
                "ingredienteId" to "ID_ALEATORIO_${Random.nextInt(100)}", // Reemplaza con IDs reales
                "cantidad" to "${Random.nextInt(1, 5)} ${listOf("unidades", "cucharadas", "gramos").random()}"
            )
        )
    }
    return ingredientesRefs
}

fun cargarRecetas(cantidad: Int) {
    val db = FirebaseFirestore.getInstance()
    try {
        val batch = db.batch() // Operación atómica

        repeat(cantidad) {
            val recetaRef = db.collection("recetas").document()
            val recetaData = mapOf(
                "nombre" to nombresRecetas.random(),
                "descripcion" to descripciones.random(),
                "urlimagen" to imagenes.random(),
                "pasos" to generarPasosAleatorios(),
                "ingredientes" to generarIngredientesAleatorios(db),
            )

            batch.set(recetaRef, recetaData)
        }

        batch.commit()
        Log.d(TAG,"$cantidad recetas creadas")
    } catch (e: Exception) {
        Log.d(TAG,"Error al crear recetas: ${e.message}")
    }
}


