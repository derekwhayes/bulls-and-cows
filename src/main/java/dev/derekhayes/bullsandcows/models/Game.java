package dev.derekhayes.bullsandcows.models;

public class Game {

    private int gameId;
    private String answer;
    private boolean finished;

    public Game() {
    }

    public Game(String answer, boolean finished) {
        this.answer = answer;
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
