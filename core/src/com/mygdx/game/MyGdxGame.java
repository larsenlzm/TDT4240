package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	//Helicopter stuff
	private Texture helicopterTexture;
	private Sprite helicopterSprite;
	private Polygon helicopter;
	private float heli_speedX;
	private float heli_speedY;
	private float heli_rotation;
	private int HELI_HEIGTH;
	private int HELI_WIDTH;

	//Game shit
	private SpriteBatch batch;
	private Polygon bounding_walls;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;
	private ShapeRenderer shapeRenderer;

		@Override
		public void create() {
			SCREEN_WIDTH = Gdx.graphics.getWidth();
			SCREEN_HEIGHT = Gdx.graphics.getHeight();

			shapeRenderer = new ShapeRenderer();

			batch = new SpriteBatch();

			helicopterTexture = new Texture(Gdx.files.internal("attackhelicopter.png"));
			HELI_WIDTH = helicopterTexture.getWidth();
			HELI_HEIGTH = helicopterTexture.getHeight();


			helicopter = new Polygon(new float[]{0,0,HELI_WIDTH, 0, HELI_WIDTH, HELI_HEIGTH, 0, HELI_HEIGTH});
			bounding_walls = new Polygon(new float[]{0,0,SCREEN_WIDTH, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, SCREEN_HEIGHT});

			helicopter.setOrigin((float)HELI_WIDTH/2, (float)HELI_HEIGTH/2);

			helicopter.setPosition( SCREEN_WIDTH/2 , SCREEN_HEIGHT/2);

			heli_speedX = 0;
			heli_speedY = 0;

			helicopterSprite = new Sprite(helicopterTexture);
			heli_rotation = 0f;
			//updateRotation();
		}

		@Override
		public void render() {
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.polygon(helicopter.getTransformedVertices());
			shapeRenderer.end();

			helicopter.setRotation(heli_rotation);

			/*
			batch.begin();

			helicopterSprite.draw(batch);
			helicopterSprite.setPosition(helicopter.getX(),helicopter.getY());
			helicopterSprite.setRotation(180+heli_rotation);

			batch.end();
			/*
			if(helicopter.getX() < 0) {
				heli_speedX = 200;
				heli_rotation = 180f;
			}
			else if (helicopter.getX() > SCREEN_WIDTH - HELI_WIDTH) {
				heli_speedX = -200;
				heli_rotation = 0f;
			}
			else if(helicopter.getY() < 0) {
				heli_speedY = 200;
				heli_rotation = 270f;
			}
			else if(helicopter.getY() > SCREEN_HEIGHT - HELI_HEIGTH) {
				heli_speedY = -200;
				heli_rotation = 90f;
			}*/

			//helicopter.translate(heli_speedX * Gdx.graphics.getDeltaTime(), heli_speedY * Gdx.graphics.getDeltaTime());

			helicopter.translate((float) Math.cos(heli_rotation*(Math.PI/180)), (float) Math.sin(heli_rotation*(Math.PI/180)));

			if(Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

				heli_rotation = 180 + (float) (Math.atan2((touchPos.y - helicopter.getY() - (float)HELI_HEIGTH/2), -(touchPos.x - helicopter.getX() - (float)HELI_WIDTH/2)) * 180 / Math.PI);

				System.out.println("klikket på : " + touchPos.x + ", " + touchPos.y);
				System.out.println("Angle: " + heli_rotation + ", origin:" + helicopter.getX() + ", " + helicopter.getY());
			}
		}


		@Override
		public void dispose() {
			helicopterTexture.dispose();
			batch.dispose();
		}
	}
