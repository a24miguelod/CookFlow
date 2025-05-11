package local.a24miguelod.cookflow.data.dependencies

import local.a24miguelod.cookflow.data.repository.RecetasRepository

interface AppInyectorContainer {
    val recetasRepository: RecetasRepository
}