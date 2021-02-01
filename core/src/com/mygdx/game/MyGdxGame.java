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

import java.util.ArrayList;
import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {

	//Animation

	//Game stuff
	private SpriteBatch batch;
	private ArrayList<Helicopter> helis = new ArrayList<>();
	private float SCREEN_HEIGHT;
	private float SCREEN_WIDTH;
	private ShapeRenderer shapeRenderer;
	private float stateTime;


		@Override
		public void create() {
			SCREEN_WIDTH = Gdx.graphics.getWidth();
			SCREEN_HEIGHT = Gdx.graphics.getHeight();

			shapeRenderer = new ShapeRenderer();
			batch = new SpriteBatch();
			int amtCopters = 4;
			float splitX = SCREEN_WIDTH/amtCopters;
			float splitY = SCREEN_HEIGHT/amtCopters;

			for (int i = 0; i < amtCopters; i++) {
				helis.add(new Helicopter(SCREEN_WIDTH, SCREEN_HEIGHT, splitX*(i), 150));
			}
		}

		private void checkRotation(Helicopter heli) {
			float pushSpeed = 10;

			float[] heli_vertices = heli.getHelicopter().getTransformedVertices();

			//This is very ugly
			for (int i = 0; i < heli_vertices.length; i++) {
				if (i%2 == 0) {
					if (heli_vertices[i] > SCREEN_WIDTH || heli_vertices[i] < 0) {
						heli.setColliding(true);
						float heli_rotation = heli.getHeli_rotation();
						heli.getHelicopter().translate( -(float) Math.cos(Math.toRadians(heli_rotation)) * pushSpeed,
														-(float) Math.sin(Math.toRadians(heli_rotation)) * pushSpeed);
						heli.setHeli_rotation(180-heli_rotation);
						break;
					}
				} else {
					if (heli_vertices[i] > SCREEN_HEIGHT || heli_vertices[i] < 0) {
						heli.setColliding(true);
						float heli_rotation = heli.getHeli_rotation();
						heli.getHelicopter().translate( -(float) Math.cos(Math.toRadians(heli_rotation)) * pushSpeed,
														-(float) Math.sin(Math.toRadians(heli_rotation)) * pushSpeed);
						heli.setHeli_rotation(360-heli_rotation);
						break;
					}
				}
			}
		}

		@Override
		public void render() {
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			stateTime += Gdx.graphics.getDeltaTime();

			for (Helicopter heli : helis) {
				heli.getHelicopterSprite().setTexture(heli.getHelicopterAnimation().getKeyFrame(stateTime, true));
			}


			/*
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.polygon(helicopter.getTransformedVertices());
			shapeRenderer.end();
			*/

			batch.begin();

			for (Helicopter heli : helis) {
				heli.getHelicopterSprite().draw(batch);
				heli.getHelicopterSprite().setPosition(heli.getHelicopter().getX(), heli.getHelicopter().getY());
				heli.getHelicopterSprite().setRotation(180+heli.getHeli_rotation());
			}

			batch.end();

			for (Helicopter heli : helis) {
				heli.setColliding(false);
				checkRotation(heli);
			}

			for (int i = 0; i < helis.size(); i++) {
				Helicopter currHeli = helis.get(i);
				for (int j = i+1; j < helis.size(); j++) {
					Helicopter otherHeli = helis.get(j);
					if (Intersector.overlapConvexPolygons(currHeli.getHelicopter(), otherHeli.getHelicopter())) {
						currHeli.setHeli_rotation(currHeli.getHeli_rotation()-180);
						otherHeli.setHeli_rotation(otherHeli.getHeli_rotation()-180);
					}
				}
			}

			for (Helicopter heli : helis) {
				if (!heli.isColliding()) {
					float heli_rotation = heli.getHeli_rotation();
					heli.getHelicopter()
							.translate(
									(float) Math.cos(Math.toRadians(heli_rotation)) * heli.getHeli_speed(),
									(float) Math.sin(Math.toRadians(heli_rotation)) * heli.getHeli_speed());
					heli.setColliding(false);
				}
			}
		}


		@Override
		public void dispose() {
			shapeRenderer.dispose();
			for (Helicopter heli : helis) {
				for(Texture texture : heli.getHelicopterTextures()){
					texture.dispose();
				}
			}
			batch.dispose();
		}
	}
