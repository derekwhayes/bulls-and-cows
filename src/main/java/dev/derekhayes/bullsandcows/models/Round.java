package dev.derekhayes.bullsandcows.models;

import java.sql.Timestamp;

public class Round {

    int roundId;
    Timestamp timestamp;
    String guess;
    String result;
    int gameId;

    public Round() {
    }

    public Round(Timestamp timestamp, String guess, String result, int gameId) {
        this.timestamp = timestamp;
        this.guess = guess;
        this.result = result;
        this.gameId = gameId;
    }

    public Round(String guess) {
        this.guess = guess;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
