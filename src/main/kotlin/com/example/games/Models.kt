package com.example.games

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object VideoGames : IntIdTable("t_video_game") {
    val name = varchar("name", 255)
    val genre = varchar("genre", 255)
    val publisherId = reference("publisher_id", VideoGamePublishers)
    val sales = integer("sales")
}

object VideoGamePublishers : IntIdTable("t_video_game_publisher") {
    val name = varchar("name", 255).uniqueIndex()
    val location = varchar("location", 255)
}

class VideoGame(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<VideoGame>(VideoGames)

    var name by VideoGames.name
    var genre by VideoGames.genre
    var publisher by VideoGamePublisher referencedOn VideoGames.publisherId
    var sales by VideoGames.sales
}

class VideoGamePublisher(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<VideoGamePublisher>(VideoGamePublishers)

    var name by VideoGamePublishers.name
    var location by VideoGamePublishers.location
}
