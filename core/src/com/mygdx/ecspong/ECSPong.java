package com.mygdx.ecspong;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.PolygonComponent;

public class ECSPong extends ApplicationAdapter {

	private float SCREEN_WIDTH;
	private float SCREEN_HEIGHT;

	private ShapeRenderer shapeRenderer;

	private PooledEngine engine;

	private Entity paddleLeft;
	private Entity paddleRight;

	private Entity wallTop;
	private Entity wallBottom;

	private Entity ball;
	
	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		shapeRenderer = new ShapeRenderer();

		engine = new PooledEngine();

		//engine.addSystem(new MovementSystem());

		float wallHeight = 10f;
		float wallWidth = SCREEN_WIDTH - 40;

		wallTop = engine.createEntity();
		wallTop.add(new PolygonComponent().create(wallWidth, wallHeight).set(new Vector2(20, SCREEN_HEIGHT - wallHeight*2)));
		engine.addEntity(wallTop);

		wallBottom = engine.createEntity();
		wallBottom.add(new PolygonComponent().create(wallWidth, wallHeight).set(new Vector2(20, wallHeight)));
		engine.addEntity(wallBottom);

		ball = engine.createEntity();
		ball.add(new CircleComponent().create(10f).set(new Vector2(SCREEN_WIDTH/2, SCREEN_HEIGHT/2)));
		engine.addEntity(ball);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.polygon(wallTop.getComponent(PolygonComponent.class).bounds.getTransformedVertices());
		shapeRenderer.polygon(wallBottom.getComponent(PolygonComponent.class).bounds.getTransformedVertices());
		Circle ballBounds = ball.getComponent(CircleComponent.class).bounds;
		shapeRenderer.circle(ballBounds.x, ballBounds.y, ballBounds.radius);
		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
