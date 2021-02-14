package com.mygdx.game;

public final class Stats {

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int GOALSCORE = 1;
    private static final Stats statsInstanse = new Stats();

    private Stats(){}

    public static Stats getInstance(){
        return statsInstanse;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }
    public int getPlayerTwoScore() {
        return playerTwoScore;
    }
    public void playerOneScores() {
        playerOneScore+=GOALSCORE;
    }
    public void playerTwoScores() {
        playerTwoScore+=GOALSCORE;
    }
}
