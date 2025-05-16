package local.a24miguelod.cookflow.common.dependencies

import local.a24miguelod.cookflow.data.repository.RecetasRepository

interface AppInyectorContainer {
    val recetasRepository: RecetasRepository
}