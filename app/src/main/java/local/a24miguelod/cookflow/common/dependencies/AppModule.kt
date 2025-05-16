package local.a24miguelod.cookflow.common.dependencies

import android.content.Context
import local.a24miguelod.cookflow.data.repository.RecetasRepository
import local.a24miguelod.cookflow.data.repository.RecetasRepositoryGithub

// https://www.youtube.com/watch?v=eX-y0IEHJjM&t=690s
interface AppModule {
    val repository: RecetasRepository

}

class AppModuleImpl (
    private val appContext:Context,

):AppModule {
    override val repository: RecetasRepository by lazy {
        RecetasRepositoryGithub()
    }
}