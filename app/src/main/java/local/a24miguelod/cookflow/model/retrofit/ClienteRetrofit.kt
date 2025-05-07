package local.a24miguelod.cookflow.model.retrofit

import com.google.gson.GsonBuilder
import local.a24miguelod.cookflow.model.retrofit.gson.Recetas
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object ClienteRetrofit {
//    private const val BASE_URL = "https://api.balldontlie.io/v1/"
//    private const val API_KEY = "TU API"
//
//    val apiEquipo: EquipoAPI by lazy {
//        val gson = GsonBuilder().setPrettyPrinting().
//        registerTypeAdapter(Recetas::class.java, RecetasTypeAdapter())
//            .registerTypeAdapter(Receta::class.java, RecetaTypeAdapter()).create()
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient(API_KEY).build())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//        retrofit.create(EquipoAPI::class.java)
//    }
//
//    private fun okHttpClient(apiKey: String) = OkHttpClient().newBuilder()
//        .addInterceptor(
//            object : Interceptor {
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val request: Request = chain.request()
//                        .newBuilder()
//                        .header("accept", "application/json")
//                        .header("Authorization", "${apiKey}")
//                        .build()
//                    return chain.proceed(request)
//                }
//            }
//        )
//}