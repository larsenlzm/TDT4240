package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Paddle extends Polygon {

    private final float height;
    private final float width;
    private float xPos;
    private float yPos;
    private float speed;
    private boolean isColliding;

    public Paddle(float height, float width, float spawnX, float spawnY) {
        super(new float[]{0,0,width, 0, width, height, 0, height});
        setPosition( spawnX, spawnY);

        this.height = height;
        this.width = width;
        this.xPos = spawnX;
        this.yPos = spawnY;
        isColliding = false;

        speed = (float) Math.max(Math.random()*3, 1);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public void move(float xOffset, float yOffset) {
        this.translate(xOffset, yOffset);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }
}
