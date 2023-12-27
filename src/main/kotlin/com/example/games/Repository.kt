package com.example.games

class Repository {
    /**
     * Select statement with WHERE clause.
     * SELECT name FROM t_video_game WHERE genre = @genre AND name LIKE '%@name%';
     */
    fun findGamesByNameAndGenre(name: String, genre: String): List<String> {
        TODO("Not yet implemented")
    }

    /**
     * Select statement with JOIN clause.
     * SELECT * FROM t_video_game
     * JOIN t_video_game_publisher ON t_video_game.publisher_id = t_video_game_publisher.id;
     */
    fun findAllGames(): List<VideoGame> {
        TODO("Not yet implemented")
    }

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
        TODO("Not yet implemented")
    }

    /**
     * Insert statement with ON DUPLICATE KEY UPDATE clause.
     * INSERT INTO t_video_game_publisher (id, name, location) VALUES (@id, @name, @location)
     * ON DUPLICATE KEY UPDATE location = @location;
     */
    fun insertOrUpdateVideoGamePublisher(publisherName: String, publisherLocation: String) {
        TODO("Not yet implemented")
    }

    /**
     * Simple delete statement.
     * DELETE FROM t_video_game_publisher WHERE id = @publisherId;
     */
    fun deleteVideoGamePublisher(publisherId: Int): Boolean {
        TODO("Not yet implemented")
    }
}
