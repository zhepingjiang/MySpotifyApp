package com.laioffer

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticBasePackage
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

// similar to java record
@Serializable
data class Playlist (
    val id: Int,
    val songs: List<Song>
)

@Serializable
data class Song(
    val name: String,
    val lyric: String,
    val src: String,
    val length: String
)

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
    // TODO: adding the routing configuration here
    routing {
        // Endpoints

        // RESTful API: GET
        get("/") {
            call.respondText("Hello World!")
        }

        get("/feed") {
            val jsonString: String? = this::class.java.classLoader.getResource("feed.json")?.readText()
            call.respondText(jsonString ?: "null")
        }

        get("/playlists") {
            val jsonString = this::class.java.classLoader.getResource("playlists.json").readText()
            call.respondText(jsonString ?: "null")
        }

        get("/playlist/{id}") {
            // Steps: jsonString -> List -> for (item in list) -> item.id == {id} -> item

            // json -> object: deserialize / decode
            // object -> json: serialize
            this::class.java.classLoader.getResource("playlists.json")?.readText()?.let { jsonString ->
                val playlists: List<Playlist> = Json.decodeFromString(
                    ListSerializer(Playlist.serializer()),
                    jsonString
                )

                val id = call.parameters["id"]
//                for (item in playlists) {
//                    if (item.id.toString() == id) {
//                        call.respond(item)
//                    }
//                }
//                val playlist = playlists.firstOrNull { playlist -> playlist.id.toString() == id }
                val playlist = playlists.firstOrNull { it.id.toString() == id }
                call.respondNullable(playlist)
            } ?: call.respondText("null")

//            if (jsonString != null) {
//                val playlists: List<Playlist> = Json.decodeFromString(
//                    ListSerializer(Playlist.serializer()),
//                    jsonString
//                )
//
//                val id = call.parameters["id"]
//                for (item in playlists) {
//                    if (item.id.toString() == id) {
//                        call.respond(item)
//                    }
//                }
//            }
//
//            call.respondText("null")
        }

        static("/") {
            staticBasePackage = "static"
            // this is the url path
            static("songs") {
                // this is the songs dir under resources
                resources("songs")
            }
        }
    }

//    // Understand the syntax
//    myRouting(block = {println("Hello World!")})
//    myRouting {
//        println("Hello World!")
//    }
//    myGet(path = "/", block = {})
//    myGet(path = "/") {
//
//    }
//    myGet("/") {
//
//    }
}

//fun myRouting(block: () -> Unit) {
//
//}
//// first param is String, the last argument if a () -> Unit
//fun myGet(path: String, block: () -> Unit) {
//
//}