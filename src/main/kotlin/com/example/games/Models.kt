package com.example.games

import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object VideoGames : Table<Nothing>("t_video_game") {
    val id = int("id").primaryKey()
    val name = varchar("name")
    val genre = varchar("genre")
    val publisherId = int("publisher_id")
    val sales = int("sales")
}

object VideoGamePublishers : Table<Nothing>("t_video_game_publisher") {
    val id = int("id").primaryKey()
    val name = varchar("name")
    val location = varchar("location")
}

data class VideoGamePublisher(
    val id: Int,
    val name: String,
    val location: String,
) {
    companion object {
        fun fromRow(row: QueryRowSet): VideoGamePublisher {
            return VideoGamePublisher(
                id = row[VideoGamePublishers.id]!!,
                name = row[VideoGamePublishers.name]!!,
                location = row[VideoGamePublishers.location]!!,
            )
        }
    }
}

data class VideoGame(
    val id: Int,
    val name: String,
    val genre: String,
    val publisher: VideoGamePublisher,
    val sales: Int,
) {
    companion object {
        fun fromRow(row: QueryRowSet): VideoGame {
            return VideoGame(
                id = row[VideoGames.id]!!,
                name = row[VideoGames.name]!!,
                genre = row[VideoGames.genre]!!,
                publisher = VideoGamePublisher.fromRow(row),
                sales = row[VideoGames.sales]!!,
            )
        }
    }
}
