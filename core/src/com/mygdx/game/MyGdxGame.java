package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {

	//Helicopter stuff
	private Polygon helicopter;
	private float heli_speed;
	private float heli_rotation;
	private int HELI_HEIGTH;
	private int HELI_WIDTH;
	private boolean hasRotatedAfterCollision;
	private boolean isColliding;
	private int FRAMES;

	//Animation

	private Texture[] helicopterTextures;
	private Animation<Texture> helicopterAnimation;
	private Sprite helicopterSprite;

	//Game stuff
	private SpriteBatch batch;
	private Polygon bounding_walls;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private float stateTime;


		@Override
		public void create() {
			SCREEN_WIDTH = Gdx.graphics.getWidth();
			SCREEN_HEIGHT = Gdx.graphics.getHeight();
			FRAMES = 4;

			shapeRenderer = new ShapeRenderer();

			batch = new SpriteBatch();
			font = new BitmapFont();

			helicopterTextures = new Texture[FRAMES];
			int index = 1;
			for(int i=0;i<FRAMES;i++){
				helicopterTextures[i] = new Texture(Gdx.files.internal("heli"+ index +".png"));
				index++;
			}

			helicopterAnimation = new Animation<Texture>(0.1f, helicopterTextures);

			HELI_WIDTH = helicopterTextures[0].getWidth();
			HELI_HEIGTH = helicopterTextures[0].getHeight();


			helicopter = new Polygon(new float[]{0,0,HELI_WIDTH, 0, HELI_WIDTH, HELI_HEIGTH, 0, HELI_HEIGTH});
			bounding_walls = new Polygon(new float[]{0,0,SCREEN_WIDTH, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, SCREEN_HEIGHT});
			hasRotatedAfterCollision = false;
			isColliding = false;

			helicopter.setOrigin(HELI_WIDTH/2,HELI_HEIGTH/2);
			helicopter.setPosition( SCREEN_WIDTH/2 - HELI_WIDTH/2 , SCREEN_HEIGHT/2 - HELI_HEIGTH);

			heli_speed = 3;

			heli_rotation = (float)Math.random()*360;
			helicopterSprite = new Sprite(helicopterTextures[0]);
		}

		@Override
		public void render() {
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stateTime += Gdx.graphics.getDeltaTime();

			helicopterSprite.setTexture(helicopterAnimation.getKeyFrame(stateTime, true));

			String coordinatesString = "x: " + Float.toString(helicopter.getX()) + " y: " + Float.toString(helicopter.getY());

			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.polygon(helicopter.getTransformedVertices());
			shapeRenderer.end();

			helicopter.setRotation(heli_rotation);

			batch.begin();

			font.draw(batch, coordinatesString, 10, SCREEN_HEIGHT-10);

			helicopterSprite.draw(batch);
			helicopterSprite.setPosition(helicopter.getX(),helicopter.getY());
			helicopterSprite.setRotation(180+heli_rotation);

			batch.end();

			/*
			if(helicopter.getX() < 0) {
				heli_rotation = 45f;
			}
			else if (helicopter.getX() > SCREEN_WIDTH - HELI_WIDTH) {
				heli_rotation += 45f;
			}
			else if(helicopter.getY() < 0) {
				heli_rotation += 45f;
			}
			else if(helicopter.getY() > SCREEN_HEIGHT - HELI_HEIGTH) {
				heli_rotation += 45f;
			}
			*/

			float speed = 5f;
			float pushSpeed = speed*3;

			float[] heli_vertices = helicopter.getTransformedVertices();

			//This is very ugly
			for (int i = 0; i < heli_vertices.length; i++) {
				if (i%2 == 0) {
					if (heli_vertices[i] > SCREEN_WIDTH || heli_vertices[i] < 0) {
						System.out.println("Horizontal collision");
						isColliding = true;
						helicopter.translate(-(float) Math.cos(Math.toRadians(heli_rotation)) * pushSpeed, -(float) Math.sin(Math.toRadians(heli_rotation)) * pushSpeed);
						heli_rotation = 180 - heli_rotation;
						break;
					}
				} else {
					if (heli_vertices[i] > SCREEN_HEIGHT || heli_vertices[i] < 0) {
						System.out.println("Vertical collision, vertex: " + heli_vertices[0]);
						isColliding = true;
						helicopter.translate(-(float) Math.cos(Math.toRadians(heli_rotation)) * pushSpeed, -(float) Math.sin(Math.toRadians(heli_rotation)) * pushSpeed);
						heli_rotation = 360 - heli_rotation;
						break;
					}
				}
			}


			helicopter.translate((float) Math.cos(Math.toRadians(heli_rotation))*speed, (float) Math.sin(Math.toRadians(heli_rotation))*speed);
			isColliding = false;

			if(Gdx.input.isTouched()) {

				Vector2 touchPos = new Vector2(Math.abs(Gdx.input.getX() ), Math.abs(Gdx.input.getY() - SCREEN_HEIGHT));

				heli_rotation = 180 + (float) (Math.atan2(-(touchPos.y - Math.abs(helicopter.getY()) - helicopter.getOriginY()),-(touchPos.x - Math.abs(helicopter.getX())- helicopter.getOriginX()))*180/Math.PI);

			}
		}


		@Override
		public void dispose() {
			shapeRenderer.dispose();
			font.dispose();
			batch.dispose();
		}
	}
