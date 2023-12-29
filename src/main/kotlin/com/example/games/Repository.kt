package com.example.games

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.support.mysql.insertOrUpdate

class Repository {
    private val database = Database.connect("jdbc:mysql://localhost:3306/ktor_orm", user = "root", password = "")

    /**
     * Select statement with WHERE clause.
     * SELECT name FROM t_video_game WHERE genre = @genre AND name LIKE '%@name%';
     */
    fun findGamesByNameAndGenre(name: String, genre: String): List<String> = database
        .from(VideoGames)
        .select(VideoGames.name)
        .where { (VideoGames.name like "%$name%") and (VideoGames.genre eq genre) }
//        .mapNotNull { row -> row.getString(1) }
        .mapNotNull { row -> row[VideoGames.name] }

    /**
     * Select statement with JOIN clause.
     * SELECT * FROM t_video_game
     * JOIN t_video_game_publisher ON t_video_game.publisher_id = t_video_game_publisher.id;
     */
    fun findAllGames(): List<VideoGame> = database
        .from(VideoGames)
        .innerJoin(VideoGamePublishers, on = VideoGames.publisherId eq VideoGamePublishers.id)
        .select()
        .mapNotNull { row -> VideoGame.fromRow(row) }

    /**
     * Select statement with JOIN, GROUP BY and HAVING clauses.
     * SELECT p.name
     * FROM t_video_game g
     * JOIN t_video_game_publisher p ON g.publisher_id = p.id
     * GROUP BY p.name
     * HAVING SUM(g.sales) > 5000;
     */
    fun findPublishersWithSalesGreaterThan(sales: Int): List<String> = database
        .from(VideoGames)
        .innerJoin(VideoGamePublishers, on = VideoGames.publisherId eq VideoGamePublishers.id)
        .select(VideoGamePublishers.name)
        .groupBy(VideoGamePublishers.name)
        .having { sum(VideoGames.sales) gt sales }
//        .mapNotNull { row -> row.getString(1) }
        .mapNotNull { row -> row[VideoGamePublishers.name] }


    /**
     * Simple insert statement.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location);
     */
    fun insertVideoGamePublisher(publisherName: String, publisherLocation: String) {
        database.insert(VideoGamePublishers) {
            set(it.name, publisherName)
            set(it.location, publisherLocation)
        }
    }

    /**
     * Insert statement with ON DUPLICATE KEY UPDATE clause.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location)
     * ON DUPLICATE KEY UPDATE location = @location;
     */
    fun insertOrUpdateVideoGamePublisher(publisherName: String, publisherLocation: String) {
        database.insertOrUpdate(VideoGamePublishers) {
            set(it.name, publisherName)
            set(it.location, publisherLocation)

            onDuplicateKey {
                set(it.location, publisherLocation)
            }
        }
    }

    /**
     * Simple delete statement.
     * DELETE FROM t_video_game_publisher WHERE id = @publisherId;
     */
    fun deleteVideoGamePublisher(publisherId: Int): Boolean {
        val rowsDeleted = database.delete(VideoGamePublishers) {
            it.id eq publisherId
        }
        return rowsDeleted > 0
    }
}
