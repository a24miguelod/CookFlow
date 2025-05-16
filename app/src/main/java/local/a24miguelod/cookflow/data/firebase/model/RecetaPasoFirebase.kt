package local.a24miguelod.cookflow.data.firebase.model

data class RecetaPasoFirebase(
    var uuidRecetaPaso: String,
    val paso: String,
    val comentarios: String,
    val duracion: Float         // En minutos
) {
    // Constructor sin argumentos necesario para la deserialización
    constructor() : this(
        "", "", "", 1.0f
    )
}
