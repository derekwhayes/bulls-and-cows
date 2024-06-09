package dev.derekhayes.bullsandcows;

import dev.derekhayes.bullsandcows.models.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int index) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setFinished(rs.getBoolean("finished"));
        game.setAnswer(rs.getString("answer"));
        return game;
    }
}
