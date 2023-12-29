package com.example

import com.example.games.VideoGame
import com.example.games.VideoGamePublisher
import kotlinx.serialization.Serializable

@Serializable
data class VideoGameApiModel(
    val name: String,
    val genre: String,
    val publisher: VideoGamePublisherApiModel,
    val sales: Int,
)

@Serializable
data class VideoGamePublisherApiModel(
    val name: String,
    val location: String,
)

fun VideoGame.toApiModel(): VideoGameApiModel {
    return VideoGameApiModel(
        name = this.name,
        genre = this.genre,
        publisher = this.publisher.toApiModel(),
        sales = this.sales
    )
}

fun VideoGamePublisher.toApiModel(): VideoGamePublisherApiModel {
    return VideoGamePublisherApiModel(
        name = this.name,
        location = this.location
    )
}
