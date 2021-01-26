package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

		@Override
		public void create() {
			SCREEN_WIDTH = Gdx.graphics.getWidth();
			SCREEN_HEIGHT = Gdx.graphics.getHeight();

			batch = new SpriteBatch();

			helicopterTexture = new Texture(Gdx.files.internal("attackhelicopter.png"));
			HELI_WIDTH = helicopterTexture.getWidth();
			HELI_HEIGTH = helicopterTexture.getHeight();

			helicopter = new Polygon(new float[]{0,0,HELI_WIDTH, 0, HELI_WIDTH, HELI_HEIGTH, 0, HELI_HEIGTH});
			bounding_walls = new Polygon(new float[]{0,0,SCREEN_WIDTH, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, SCREEN_HEIGHT});

			helicopter.setOrigin((float)HELI_WIDTH/2, (float)HELI_HEIGTH/2);

			helicopter.setPosition( SCREEN_WIDTH/2 , SCREEN_HEIGHT/2);

			heli_speedX = 0;
			heli_speedY = 200;

			helicopterSprite = new Sprite(helicopterTexture);
			heli_rotation = 270f;
			//updateRotation();
		}

		@Override
		public void render() {
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.begin();

			helicopterSprite.draw(batch);
			helicopterSprite.setPosition(helicopter.getX(),helicopter.getY());
			helicopterSprite.setRotation(heli_rotation);

			batch.end();

			System.out.println("X: " + helicopter.getX() + ", Y:" +helicopter.getY());

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
			}


			helicopter.translate(heli_speedX * Gdx.graphics.getDeltaTime(), heli_speedY * Gdx.graphics.getDeltaTime());
		}

		/*public void updateRotation(){
			heli_rotation = (float)(Math.acos( heli_speedX / Math.sqrt(heli_speedY*heli_speedY + heli_speedX*heli_speedX)) * 180 / Math.PI);
		}
		 */

		@Override
		public void dispose() {
			helicopterTexture.dispose();
			batch.dispose();
		}
	}
