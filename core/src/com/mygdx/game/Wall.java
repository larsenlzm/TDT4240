package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Wall extends Polygon {

    private final float height;
    private final float width;
    private final float xPos;
    private final float yPos;

    public Wall(float height, float width, float spawnX, float spawnY) {
        super(new float[]{0,0,width, 0, width, height, 0, height});
        setPosition( spawnX, spawnY);

        this.height = height;
        this.width = width;
        this.xPos = spawnX;
        this.yPos = spawnY;
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

    public float getyPos() {
        return yPos;
    }
}
