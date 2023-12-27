package com.example.games

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface VideoGame : Entity<VideoGame> {
    val id: Int
    var name: String
    var genre: String
    var publisher: VideoGamePublisher
    var sales: Int

    companion object : Entity.Factory<VideoGame>()
}

object VideoGames : Table<VideoGame>("t_video_game") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name } // unique
    val genre = varchar("genre").bindTo { it.genre }
    val publisherId = int("publisher_id").references(VideoGamePublishers) { it.publisher }
    val sales = int("sales").bindTo { it.sales }
}

interface VideoGamePublisher : Entity<VideoGamePublisher> {
    val id: Int
    var name: String
    var location: String

    companion object : Entity.Factory<VideoGamePublisher>()
}

object VideoGamePublishers : Table<VideoGamePublisher>("t_video_game_publisher") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name } // unique
    val location = varchar("location").bindTo { it.location }
}
