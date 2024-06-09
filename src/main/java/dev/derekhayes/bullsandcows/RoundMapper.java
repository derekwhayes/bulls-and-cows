package dev.derekhayes.bullsandcows;

import dev.derekhayes.bullsandcows.models.Round;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundMapper implements RowMapper<Round> {

    @Override
    public Round mapRow(ResultSet rs, int index) throws SQLException {
        Round round = new Round();
        round.setRoundId(rs.getInt("round_id"));
        round.setTimestamp(rs.getTimestamp("time_stamp"));
        round.setGuess(rs.getString("guess"));
        round.setResult(rs.getString("result"));
        round.setGameId(rs.getInt("game_id"));
        return round;
    }
}
