package com.example.games

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class Repository {
    init {
        Database.connect("jdbc:mysql://localhost:3306/ktor_orm", user = "root")
        transaction {
            SchemaUtils.create(VideoGames, VideoGamePublishers)
        }
    }

    /**
     * Select statement with WHERE clause.
     * SELECT name FROM t_video_game WHERE genre = @genre AND name LIKE '%@name%';
     */
    fun findGamesByNameAndGenre(name: String, genre: String): List<String> = transaction {
        VideoGames.slice(VideoGames.name)
            .select { VideoGames.genre eq genre and (VideoGames.name like "%$name%") }
            .map { it[VideoGames.name] }
    }

    /**
     * Select statement with JOIN clause.
     * SELECT * FROM t_video_game
     * JOIN t_video_game_publisher ON t_video_game.publisher_id = t_video_game_publisher.id;
     */
    fun findAllGames(): List<VideoGame> = transaction {
        (VideoGames innerJoin VideoGamePublishers)
            .selectAll()
            .map { VideoGame.fromRow(it) }
    }

    /**
     * Select statement with JOIN, GROUP BY and HAVING clauses.
     * SELECT p.name
     * FROM t_video_game g
     * JOIN t_video_game_publisher p ON g.publisher_id = p.id
     * GROUP BY p.name
     * HAVING SUM(g.sales) > 5000;
     */
    fun findPublishersWithSalesGreaterThan(sales: Int): List<String> = transaction {
        (VideoGames innerJoin VideoGamePublishers)
            .slice(VideoGamePublishers.name)
            .selectAll()
            .groupBy(VideoGamePublishers.name)
            .having { VideoGames.sales.sum() greater sales }
            .map { it[VideoGamePublishers.name] }
    }

    /**
     * Simple insert statement.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location);
     */
    fun insertVideoGamePublisher(publisherName: String, publisherLocation: String) {
        transaction {
            VideoGamePublishers.insert {
                it[name] = publisherName
                it[location] = publisherLocation
            }
        }
    }

    /**
     * Insert statement with ON DUPLICATE KEY UPDATE clause.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location)
     * ON DUPLICATE KEY UPDATE location = @location;
     */
    fun insertOrUpdateVideoGamePublisher(publisherName: String, publisherLocation: String) {
        transaction {
            VideoGamePublishers.upsert {
                it[name] = publisherName
                it[location] = publisherLocation
            }
        }
    }

    /**
     * Simple delete statement.
     * DELETE FROM t_video_game_publisher WHERE id = @publisherId;
     */
    fun deleteVideoGamePublisher(publisherId: Int): Boolean = transaction {
        VideoGamePublishers.deleteWhere { VideoGamePublishers.id eq publisherId } > 0
    }
}
