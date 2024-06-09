package dev.derekhayes.bullsandcows.daos;

import dev.derekhayes.bullsandcows.GameMapper;
import dev.derekhayes.bullsandcows.RoundMapper;
import dev.derekhayes.bullsandcows.models.Game;
import dev.derekhayes.bullsandcows.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class NumberGameDaoImpl implements NumberGameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NumberGameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game addGame(Game game) {
        final String sql = "INSERT INTO game(answer, finished) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.isFinished());
            return statement;
        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT game_id, answer, finished FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game findGameById(int game_id) {
        final String sql = "SELECT game_id, answer, finished FROM game WHERE game_id = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), game_id);
    }

    @Override
    public List<Round> findRoundsByGameId(int game_id) {

        final String sql = "SELECT round_id, time_stamp, guess, result, game_id FROM round WHERE game_id = ? ORDER BY time_stamp DESC;";

        return jdbcTemplate.query(sql, new RoundMapper(), game_id);
    }

    @Override
    public Round guessNum(Round guess) {
        final String sql = "INSERT INTO round(time_stamp, guess, result, game_id) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, guess.getTimestamp());
            statement.setString(2, guess.getGuess());
            statement.setString(3, guess.getResult());
            statement.setInt(4, guess.getGameId());
            return statement;
        }, keyHolder);

        guess.setRoundId(keyHolder.getKey().intValue());

        return guess;
    }

    @Override
    public void saveGame(Game game) {
        jdbcTemplate.update(
                "UPDATE game SET finished = ? WHERE game_id = ?;",
                game.isFinished(), game.getGameId());
    }


}

