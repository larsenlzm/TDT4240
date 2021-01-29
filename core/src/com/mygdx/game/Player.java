package com.mygdx.game;

public class Player {
    private int score;
    private final Paddle paddle;
    private final int keyUp;
    private final int keyDown;

    public Player(Paddle paddle, int keyUp, int keyDown) {
        this.paddle = paddle;
        this.score = 0;
        this.keyDown = keyDown;
        this.keyUp = keyUp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public int getKeyUp() {
        return keyUp;
    }

    public int getKeyDown() {
        return keyDown;
    }
}
