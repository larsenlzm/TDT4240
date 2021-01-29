package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class GdxPong extends ApplicationAdapter {
    //Game stuff
    private SpriteBatch batch;
    private ArrayList<Paddle> paddles;
    private ArrayList<Wall> walls;
    private ArrayList<Polygon> polygons;
    private Ball ball;
    private ShapeRenderer shapeRenderer;
    private float stateTime;


    @Override
    public void create() {
        float SCREEN_WIDTH = Gdx.graphics.getWidth();
        float SCREEN_HEIGHT = Gdx.graphics.getHeight();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        paddles = new ArrayList<>();
        walls = new ArrayList<>();
        polygons = new ArrayList<>();

        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);

        paddles.add(new Paddle(100, 10, 10, SCREEN_HEIGHT /2));
        paddles.add(new Paddle(100, 10, SCREEN_WIDTH - 20, SCREEN_HEIGHT /2));

        float wallHeight = 10;
        walls.add(new Wall(wallHeight, SCREEN_WIDTH-20, 10, 0+wallHeight));
        walls.add(new Wall(wallHeight, SCREEN_WIDTH-20, 10, SCREEN_HEIGHT-wallHeight*2));

        polygons.addAll(paddles);
        polygons.addAll(walls);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Polygon object : polygons) {
            shapeRenderer.polygon(object.getTransformedVertices());
        }
        shapeRenderer.circle(ball.x, ball.y, ball.radius);
        shapeRenderer.end();

        //Horribelt, jeg spyr, men det funker enn så lenge
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            paddles.get(0).move(0,200 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            paddles.get(0).move(0,-200 * Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            paddles.get(1).move(0,200 * Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            paddles.get(1).move(0,-200 * Gdx.graphics.getDeltaTime());
        }

        //Ball til paddle/vegg kollisjon, flipper x eller y speed
        for (Polygon poly : polygons) {
            if (ball.isCollidingWithPolygon(poly)) {
                if (poly instanceof Wall) {
                    ball.setySpeed(-ball.getySpeed());
                } else {
                    ball.setxSpeed(-ball.getxSpeed());
                }
            }
        }
        ball.set(ball.x+(ball.getxSpeed()*Gdx.graphics.getDeltaTime()), ball.y+(ball.getySpeed()*Gdx.graphics.getDeltaTime()), ball.radius);

        /* For sprites, trenger ikke afaik
        batch.begin();


        batch.end();
        */


    }


    @Override
    public void dispose() {
        shapeRenderer.dispose();

        batch.dispose();
    }
}
