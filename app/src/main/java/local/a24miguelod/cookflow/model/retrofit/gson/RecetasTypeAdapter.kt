package local.a24miguelod.cookflow.model.retrofit.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import local.a24miguelod.cookflow.model.Receta
import java.io.IOException

//class EquiposTypeAdapter : TypeAdapter<Recetas?>() {
//    @Throws(IOException::class)
//    override fun write(jsonWriter: JsonWriter, equipos: Recetas?) {
//    }
//
//    @Throws(IOException::class)
//    override fun read(jsonReader: JsonReader): Recetas? {
//        val recetas = Recetas()
//
//        if(jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
//            jsonReader.beginObject()
//            while (jsonReader.hasNext()) {
//                when (jsonReader.nextName()) {
//                    "data" -> {
//                        jsonReader.beginArray()
//                        while (jsonReader.hasNext()) {
//                            val receta = Receta()
//                            jsonReader.beginObject()
//                            while (jsonReader.hasNext()) {
//                                when (jsonReader.nextName()) {
//                                    "id" -> receta.idEquipo = jsonReader.nextInt()
//                                    "name" -> equipo.nombre = jsonReader.nextString().orEmpty()
//                                    "division" -> equipo.division = enumByNameIgnoreCase<Division>(jsonReader.nextString()) ?: Division.PACIFIC
//                                    "conference" -> equipo.conferencia = enumByNameIgnoreCase<Conferencia>(jsonReader.nextString()) ?: Conferencia.WEST
//                                    "city" -> equipo.ciudad = jsonReader.nextString().orEmpty()
//                                    "abbreviation" -> equipo.abreviatura = jsonReader.nextString().orEmpty()
//                                    "full_name" -> equipo.nombreCompleto = jsonReader.nextString().orEmpty()
//                                    else -> jsonReader.skipValue()
//                                }
//                            }
//                            jsonReader.endObject()
//                            equipos.equipos.add(equipo)
//                        }
//                        jsonReader.endArray()
//                    }
//                    else -> jsonReader.skipValue()
//                }
//            }
//            jsonReader.endObject()
//        } else if(jsonReader.peek()== JsonToken.NAME) {
//            Log.i("TOKEN: ", jsonReader.nextName())
//        }
//
//        return equipos
//    }
//
//
//}