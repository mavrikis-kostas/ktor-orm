package com.example.games

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.entity.*

class Repository {
    private val database = Database.connect("jdbc:mysql://localhost:3306/ktor_orm", user = "root", password = "")

    /**
     * Select statement with WHERE clause.
     * SELECT name FROM t_video_game WHERE genre = @genre AND name LIKE '%@name%';
     */
    fun findGamesByNameAndGenre(name: String, genre: String): List<String> = database
        .sequenceOf(VideoGames, withReferences = false)
        .filter { it.genre eq genre and (it.name like "%$name%") }
        .mapNotNull { it.name }

    /**
     * Select statement with JOIN clause.
     * SELECT * FROM t_video_game
     * JOIN t_video_game_publisher ON t_video_game.publisher_id = t_video_game_publisher.id;
     */
    fun findAllGames(): List<VideoGame> = database
        .videoGames
        .toList()

    /**
     * Select statement with JOIN, GROUP BY and HAVING clauses.
     * SELECT p.name
     * FROM t_video_game g
     * JOIN t_video_game_publisher p ON g.publisher_id = p.id
     * GROUP BY p.name
     * HAVING SUM(g.sales) > 5000;
     */
    fun findPublishersWithSalesGreaterThan(sales: Int): List<String> {
        TODO("Not yet implemented")
    }

    /**
     * Simple insert statement.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location);
     */
    fun insertVideoGamePublisher(publisherName: String, publisherLocation: String) {
        database.videoGamePublishers.add(
            VideoGamePublisher {
                name = publisherName
                location = publisherLocation
            }
        )
    }

    /**
     * Insert statement with ON DUPLICATE KEY UPDATE clause.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location)
     * ON DUPLICATE KEY UPDATE location = @location;
     */
    fun insertOrUpdateVideoGamePublisher(publisherName: String, publisherLocation: String) {
        try {
            val publisher = VideoGamePublisher {
                name = publisherName
                location = publisherLocation
            }
            database.videoGamePublishers.add(publisher)
            publisher.flushChanges()
        } catch (e: Exception) {
            val publisher = database.videoGamePublishers.find { it.name eq publisherName } ?: return
            publisher.location = publisherLocation
            publisher.flushChanges()
        }
    }

    /**
     * Simple delete statement.
     * DELETE FROM t_video_game_publisher WHERE id = @publisherId;
     */
    fun deleteVideoGamePublisher(publisherId: Int): Boolean {
        val publisher: VideoGamePublisher? = database.videoGamePublishers.find { it.id eq publisherId }
        publisher?.delete()
        return publisher != null
    }

    companion object {
        val Database.videoGamePublishers get() = this.sequenceOf(VideoGamePublishers)
        val Database.videoGames get() = this.sequenceOf(VideoGames)

    }
}
