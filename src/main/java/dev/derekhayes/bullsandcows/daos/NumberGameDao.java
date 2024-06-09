package dev.derekhayes.bullsandcows.daos;

import dev.derekhayes.bullsandcows.models.Game;
import dev.derekhayes.bullsandcows.models.Round;

import java.util.List;

public interface NumberGameDao {

    Game addGame(Game game);

    List<Game> getAllGames();

    Game findGameById(int game_id);

    List<Round> findRoundsByGameId(int game_id);

    Round guessNum(Round guess);

    void saveGame(Game game);
}
