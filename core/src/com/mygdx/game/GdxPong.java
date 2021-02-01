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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class GdxPong extends ApplicationAdapter {
    //Game stuff
    private SpriteBatch batch;
    private Paddle paddle1;
    private Paddle paddle2;
    private ArrayList<Wall> walls;
    private ArrayList<Polygon> polygons;
    private Player p1;
    private Player p2;
    private Ball ball;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private float SCREEN_WIDTH;
    private float SCREEN_HEIGHT;
    private String winText;
    /*
    private Skin mySkin;
    private Stage stage;
    private TextButton restartKnapp;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
   */
    @Override
    public void create() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        winText = "";
        /*
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        restartKnapp = new TextButton("Button1", textButtonStyle);
        restartKnapp.setSize(40,10);
        restartKnapp.setPosition(10,Gdx.graphics.getHeight()-10*3);
        restartKnapp.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("ferdig da, restart");
            }
        });
        */
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        walls = new ArrayList<>();
        polygons = new ArrayList<>();

        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10, 200);

        paddle1 = new Paddle(100, 10, 10, SCREEN_HEIGHT /2);
        paddle2 = new Paddle(100, 10, SCREEN_WIDTH - 20, SCREEN_HEIGHT /2);

        float wallHeight = 10;
        walls.add(new Wall(wallHeight, SCREEN_WIDTH-40, 20, 0+wallHeight));
        walls.add(new Wall(wallHeight, SCREEN_WIDTH-40, 20, SCREEN_HEIGHT-wallHeight*2));



        polygons.add(paddle1);
        polygons.add(paddle2);
        polygons.addAll(walls);

        p1 = new Player(paddle1, Input.Keys.W, Input.Keys.S);
        p2 = new Player(paddle2, Input.Keys.UP, Input.Keys.DOWN);
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


    private void score(Player player, String name) {
        if (player.getScore() < 11) {
            player.setScore(player.getScore()+1);
            ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10, 200);
        } else if (player.getScore() == 11){
            winText = "The winner is: " + name;
                //TODO
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

        //stage.draw();

        batch.begin();
        font.draw(batch, ""+p1.getScore(), (SCREEN_WIDTH/2)-50, SCREEN_HEIGHT-50);
        font.draw(batch, ""+p2.getScore(), (SCREEN_WIDTH/2)+50, SCREEN_HEIGHT-50);
        font.draw(batch, winText, (SCREEN_WIDTH/2), SCREEN_HEIGHT/2);
        batch.end();

        //Horribelt, jeg spyr, men det funker enn så lenge
        //Hooribelt? watch this
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(paddle1.getY() < SCREEN_HEIGHT-paddle1.getHeight()) paddle1.move(0,200 * Gdx.graphics.getDeltaTime());
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(paddle1.getY() > 0) paddle1.move(0,-200 * Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(paddle2.getY() < SCREEN_HEIGHT-paddle1.getHeight()) paddle2.move(0,200 * Gdx.graphics.getDeltaTime());
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(paddle2.getY() > 0) paddle2.move(0,-200 * Gdx.graphics.getDeltaTime());
        }

        //:nauseated_face:
        switch (getScoreState()) {
            case 1:
                score(p1, "player2");
                break;
            case 2:
                score(p2, "player1");
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
