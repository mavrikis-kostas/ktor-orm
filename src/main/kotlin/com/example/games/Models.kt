package com.example.games

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

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

data class VideoGame(
    val name: String,
    val genre: String,
    val publisher: VideoGamePublisher,
    val sales: Int,
) {
    companion object {
        fun fromRow(row: ResultRow): VideoGame {
            return VideoGame(
                name = row[VideoGames.name],
                genre = row[VideoGames.genre],
                publisher = VideoGamePublisher(
                    name = row[VideoGamePublishers.name],
                    location = row[VideoGamePublishers.location],
                ),
                sales = row[VideoGames.sales],
            )
        }
    }
}

data class VideoGamePublisher(
    val name: String,
    val location: String,
)
