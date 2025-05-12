package local.a24miguelod.cookflow

import android.app.Application
import local.a24miguelod.cookflow.data.dependencies.AppInyectorContainer
import local.a24miguelod.cookflow.data.dependencies.AppInyectorContainerImpl
import coil3.ImageLoader
import coil3.SingletonImageLoader


class CookFlowApp: Application() {
    /**
     * La instancia de AppInyectorContainer es utilizada por el resto de las clases para
     * obtener dependencias
     * */
    lateinit var contenedor: AppInyectorContainer
        private set // Solo se puede modificar desde esta clase

    /**
     * Inicializa el contenedor de inyección de dependencias.
     * Se llama al crear la aplicación y está disponible para el resto de las clases.
     * A partir de aquí se puede usar el contenedor para obtener las dependencias necesarias para
     * acceder a la API de fotos.
     */
    override fun onCreate() {
        super.onCreate()
        contenedor = AppInyectorContainerImpl()

    }
}