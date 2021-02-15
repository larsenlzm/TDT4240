package com.mygdx.ecspong;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.GameComponent;
import com.mygdx.ecspong.components.PlayerComponent;
import com.mygdx.ecspong.components.PolygonComponent;
import com.mygdx.ecspong.components.VelocityComponent;
import com.mygdx.ecspong.systems.CollisionSystem;
import com.mygdx.ecspong.systems.GameSystem;
import com.mygdx.ecspong.systems.InputSystem;
import com.mygdx.ecspong.systems.MovementSystem;
import com.mygdx.ecspong.systems.RenderSystem;

public class ECSPong extends ApplicationAdapter {

	private float SCREEN_WIDTH;
	private float SCREEN_HEIGHT;

	private PooledEngine engine;

	private Entity game;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		engine = new PooledEngine();

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		engine.addSystem(new RenderSystem(shapeRenderer, font, batch));
		engine.addSystem(new MovementSystem());
		engine.addSystem(new InputSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new GameSystem());

		game = engine.createEntity();
		game.add(new GameComponent().create());
		engine.addEntity(game);

		float wallHeight = 10f;
		float wallWidth = SCREEN_WIDTH - 40;

		Entity wallTop = engine.createEntity();
		wallTop.add(new PolygonComponent().create(wallWidth, wallHeight).setPos(new Vector2(20, SCREEN_HEIGHT - wallHeight*2)));
		engine.addEntity(wallTop);

		Entity wallBottom = engine.createEntity();
		wallBottom.add(new PolygonComponent().create(wallWidth, wallHeight).setPos(new Vector2(20, wallHeight)));
		engine.addEntity(wallBottom);

		float paddleHeight = 100f;
		float paddleWidth = 10f;

		Entity paddleLeft = engine.createEntity();
		paddleLeft.add(new PolygonComponent().create(paddleWidth, paddleHeight).setPos(new Vector2(paddleWidth, SCREEN_HEIGHT/2)));
		paddleLeft.add(new VelocityComponent().create());
		paddleLeft.add(new PlayerComponent().create(Input.Keys.W, Input.Keys.S, 1));
		engine.addEntity(paddleLeft);

		Entity paddleRight = engine.createEntity();
		paddleRight.add(new PolygonComponent().create(paddleWidth, paddleHeight).setPos(new Vector2(SCREEN_WIDTH-paddleWidth*2, SCREEN_HEIGHT/2)));
		paddleRight.add(new VelocityComponent().create());
		paddleRight.add(new PlayerComponent().create(Input.Keys.UP, Input.Keys.DOWN, 2));
		engine.addEntity(paddleRight);

		Entity ball = engine.createEntity();
		ball.add(new CircleComponent().create(10f).setPos(new Vector2(SCREEN_WIDTH/2, SCREEN_HEIGHT/2)));
		ball.add(new VelocityComponent().set(new Vector2(Math.random() > 0.5 ? 100 : -100, 100)));
		engine.addEntity(ball);

		game.getComponent(GameComponent.class).setState(GameComponent.GameState.GAME_PLAYING);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}

}
