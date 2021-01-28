package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Paddle extends Polygon {

    private float height;
    private float width;
    private float spawnX;
    private float spawnY;
    private float speed;
    private boolean isColliding;

    public Paddle(float heigth, float width, float spawnX, float spawnY) {
        super(new float[]{0,0,width, 0, width, heigth, 0, heigth});
        setPosition( spawnX, spawnY);

        this.height = heigth;
        this.width = width;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        isColliding = false;
        speed = (float) Math.max(Math.random()*3, 1);
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed){this.speed = speed;}
    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }
    public boolean isColliding() {
        return isColliding;
    }
    public void isColliding(boolean isColliding) {
        this.isColliding = isColliding;
    }

}
