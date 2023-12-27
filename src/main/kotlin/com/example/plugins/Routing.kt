package com.example.plugins

import com.example.*
import com.example.games.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repository = Repository()

    routing {
        /**
         * Returns video game titles filtered by name and genre.
         */
        get("/v1/video-games") {
            val name = call.request.queryParameters["name"]!!
            val genre = call.request.queryParameters["genre"]!!
            val games: List<String> = repository.findGamesByNameAndGenre(name, genre)
            call.respond(games)
        }

        /**
         * Returns video games with publisher information.
         */
        get("/v2/video-games") {
            val games: List<VideoGameApiModel> = repository.findAllGames().map { it.toApiModel() }
            call.respond(games)
        }

        /**
         * Returns video game publishers with sales greater than the specified value.
         */
        get("/v3/video-games") {
            val sales = call.request.queryParameters["sales"]!!.toInt()
            val publishers: List<String> = repository.findPublishersWithSalesGreaterThan(sales)
            call.respond(publishers)
        }

        /**
         * Inserts a new video game publisher.
         */
        post("/v1/video-game-publishers") {
            val publisher: VideoGamePublisherApiModel = call.receive<VideoGamePublisherApiModel>()
            try {
                repository.insertVideoGamePublisher(publisher.name, publisher.location)
                call.respond(HttpStatusCode.OK, "Inserted")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Failed to insert")
            }
        }

        /**
         * Inserts or updates a video game publisher.
         */
        post("/v2/video-game-publishers") {
            val publisher: VideoGamePublisherApiModel = call.receive<VideoGamePublisherApiModel>()
            try {
                repository.insertOrUpdateVideoGamePublisher(publisher.name, publisher.location)
                call.respond(HttpStatusCode.OK, "Inserted")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Failed to insert")
            }
        }

        /**
         * Deletes a video game publisher.
         */
        delete("/v1/video-game-publishers/{publisherId}") {
            val publisherId = call.parameters["publisherId"]!!.toInt()
            if (repository.deleteVideoGamePublisher(publisherId)) {
                call.respond(HttpStatusCode.OK, "Deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Failed to delete")
            }
        }
    }
}
