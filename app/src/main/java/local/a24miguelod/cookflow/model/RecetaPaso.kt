package local.a24miguelod.cookflow.model

data class RecetaPaso(
    val paso: String,
    val comentarios: String,
    val duracion: Float         // En minutos
)
{
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this("", "", 0f)
}

