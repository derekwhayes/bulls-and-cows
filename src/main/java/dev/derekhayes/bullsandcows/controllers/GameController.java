package dev.derekhayes.bullsandcows.controllers;

import dev.derekhayes.bullsandcows.daos.NumberGameDaoImpl;
import dev.derekhayes.bullsandcows.models.Game;
import dev.derekhayes.bullsandcows.models.Round;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final NumberGameDaoImpl dao;

    public GameController(NumberGameDaoImpl dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Game> allGames() {
        List<Game> results = dao.getAllGames();

        for (Game game : results) {
            if (!game.isFinished()) {
                game.setAnswer("Finish game to reveal answer");
            }
        }
        return results;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game>  findGameById(@PathVariable int id) {
        Game result = dao.findGameById(id);
        if (result == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!result.isFinished()) {
            result.setAnswer("Finish game to reveal answer");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(Game game) {
        game = new Game(getRandomAnswer(), false);
        return dao.addGame(game);
    }

    @PostMapping("/guess")
    public Round guess(@RequestParam("guess") String guess, @RequestParam("game_id") int gameId) {
        Timestamp timestamp = new Timestamp(currentTimeMillis());
        Game game = dao.findGameById(gameId);

        String result = checkGuess(guess, game.getAnswer());


        Round round = new Round(timestamp, guess, result, gameId);

        if (result.charAt(2) == '4') {
            game.setFinished(true);
            dao.saveGame(game);
        }

        return dao.guessNum(round);
    }

    @GetMapping("/guess/{id}")
    public List<Round>  findRoundByGameId(@PathVariable int id) {
        List<Round> results = dao.findRoundsByGameId(id);

        return results;
    }

    private String getRandomAnswer() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nums.add(i);
        }

        Collections.shuffle(nums);

        String result = "";
        for (int i = 0; i < 4; i++) {
            result += nums.get(i).toString();
        }
        return result;
    }

    private String checkGuess(String guess, String answer) {
        int exactMatch = 0;
        int partialMatch = 0;

        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                exactMatch++;
            } else {
                for (int j = 0; j < 4; j++) {
                    if (i != j && guess.charAt(i) == answer.charAt(j)) {
                        partialMatch++;
                    }
                }
            }

        }
        return "e:" + exactMatch + ":p:" + partialMatch;
    }
}
