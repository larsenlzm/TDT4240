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
    private Player p1;
    private Player p2;
    private Ball ball;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private float SCREEN_WIDTH;
    private float SCREEN_HEIGHT;

    @Override
    public void create() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

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

        p1 = new Player(paddles.get(0), Input.Keys.W, Input.Keys.S);
        p2 = new Player(paddles.get(1), Input.Keys.UP, Input.Keys.DOWN);
    }

    public int getScoreState() {
        if (ball.x > SCREEN_WIDTH){
            return 1;
        } else if (ball.x < 0) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Polygon object : polygons) {
            shapeRenderer.polygon(object.getTransformedVertices());
        }
        shapeRenderer.circle(ball.x, ball.y, ball.radius);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, ""+p1.getScore(), (SCREEN_WIDTH/2)-50, SCREEN_HEIGHT-50);
        font.draw(batch, ""+p2.getScore(), (SCREEN_WIDTH/2)+50, SCREEN_HEIGHT-50);
        batch.end();

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

        //:nauseated_face:
        switch (getScoreState()) {
            case 1:
                p1.setScore(p1.getScore()+1);
                ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
                break;
            case 2:
                p2.setScore(p2.getScore()+1);
                ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
                break;
            default:
                break;

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


    }


    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        batch.dispose();
    }
}
