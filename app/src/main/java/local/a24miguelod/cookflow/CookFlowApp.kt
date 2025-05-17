package local.a24miguelod.cookflow

import android.app.Application
import local.a24miguelod.cookflow.common.dependencies.AppInyectorContainer
import local.a24miguelod.cookflow.common.dependencies.AppInyectorContainerImpl

class CookFlowApp: Application() {


    /**
     * Inicializa el contenedor de inyección de dependencias.
     * Se llama al crear la aplicación y está disponible para el resto de las clases.
     * A partir de aquí se puede usar el contenedor para obtener las dependencias necesarias para
     * acceder a la API de fotos.
     */
    override fun onCreate() {
        super.onCreate()
        contenedor = AppInyectorContainerImpl(this)
    }

    companion object {
        /**
         * La instancia de AppInyectorContainer es utilizada por el resto de las clases para
         * obtener dependencias
         * */
        lateinit var contenedor: AppInyectorContainer
            private set // Solo se puede modificar desde esta clase
    }
}