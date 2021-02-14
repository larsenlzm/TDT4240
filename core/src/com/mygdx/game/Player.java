package com.mygdx.game;

public class Player {
    private final Paddle paddle;
    private final int keyUp;
    private final int keyDown;

    public Player(Paddle paddle, int keyUp, int keyDown) {
        this.paddle = paddle;
        this.keyDown = keyDown;
        this.keyUp = keyUp;
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
