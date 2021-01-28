package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Helicopter {
    //Helicopter stuff
    private Texture[] helicopterTextures;
    private Animation<Texture> helicopterAnimation;
    private final Sprite helicopterSprite;
    private final Polygon helicopter;
    private float heli_speed;
    private float heli_rotation;
    private float heliHeight;
    private float heliWidth;
    private boolean hasRotatedAfterCollision;
    private boolean isColliding;
    private int FRAMES;

    public Helicopter(float xMax, float yMax, float spawnX, float spawnY) {

        FRAMES = 4;

        helicopterTextures = new Texture[FRAMES];
        int index = 1;
        for(int i=0;i<FRAMES;i++){
            helicopterTextures[i] = new Texture(Gdx.files.internal("heli"+ index +".png"));
            index++;
        }

        helicopterAnimation = new Animation<Texture>(0.1f, helicopterTextures);
        heliWidth = helicopterTextures[0].getWidth();
        heliHeight = helicopterTextures[0].getHeight();

        helicopter = new Polygon(new float[]{0,0,heliWidth, 0, heliWidth, heliHeight, 0, heliHeight});
        hasRotatedAfterCollision = false;
        isColliding = false;

        helicopter.setOrigin(heliWidth/2,heliHeight/2);
        helicopter.setPosition((float) spawnX, (float) spawnY);

        heli_speed = (float) Math.max(Math.random()*3, 1);
        heli_rotation = (float)Math.random()*360;
        helicopter.setRotation(heli_rotation);

        helicopterSprite = new Sprite(helicopterTextures[0]);
    }

    public Animation<Texture> getHelicopterAnimation() {
        return helicopterAnimation;
    }

    public Texture[] getHelicopterTextures() {
        return helicopterTextures;
    }

    public Sprite getHelicopterSprite() {
        return helicopterSprite;
    }

    public Polygon getHelicopter() {
        return helicopter;
    }

    public float getHeli_speed() {
        return heli_speed;
    }

    public float getHeli_rotation() {
        return heli_rotation;
    }

    public float getHeliHeight() {
        return heliHeight;
    }

    public float getHeliWidth() {
        return heliWidth;
    }

    public boolean isHasRotatedAfterCollision() {
        return hasRotatedAfterCollision;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setHeli_rotation(float heli_rotation) {
        this.heli_rotation = heli_rotation;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }
}