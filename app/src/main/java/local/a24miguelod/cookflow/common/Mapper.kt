package local.a24miguelod.cookflow.common

interface Mapper<F,T> {
    fun mapFrom(from:F):T
}