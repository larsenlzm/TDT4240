package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.GameComponent;
import com.mygdx.ecspong.components.PlayerComponent;
import com.mygdx.ecspong.components.VelocityComponent;

public class GameSystem extends EntitySystem {
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> circles;
    private ImmutableArray<Entity> games;

    private Engine engine;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<CircleComponent> cm = ComponentMapper.getFor(CircleComponent.class);
    private ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);


    public void addedToEngine(Engine engine) {
        this.engine = engine;
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        circles = engine.getEntitiesFor(Family.all(CircleComponent.class).get());
        games = engine.getEntitiesFor(Family.all(GameComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity game: games) {
            GameComponent gameComponent = gm.get(game);
            if (gameComponent.gameState == GameComponent.GameState.GAME_PLAYING) {
                for (Entity e : circles) {
                    CircleComponent circle = cm.get(e);

                    if (circle.bounds.x > Gdx.graphics.getWidth()) {
                        pointScored(1, e, gameComponent);
                    }

                    if (circle.bounds.x < 0) {
                        pointScored(2, e, gameComponent);
                    }
                }
            }
        }
    }

    private void pointScored(int playerNum, Entity e, GameComponent gameComponent) {
        engine.removeEntity(e);
        Entity newBall = engine.createEntity();
        newBall.add(new CircleComponent().create(10f).setPos(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2)));
        newBall.add(new VelocityComponent().set(new Vector2(Math.random() > 0.5 ? 100 : -100, 100)));
        engine.addEntity(newBall);
        for (Entity player : players) {
            PlayerComponent p = pm.get(player);
            if (p.playerNum == playerNum) {
                p.setScore(p.score += 1);
            }
            if (p.score >= 10) {
                engine.removeAllEntities();
                if (p.playerNum == 1) {
                    Entity newGameState = engine.createEntity();
                    newGameState.add(new GameComponent().create().setState(GameComponent.GameState.GAME_WIN_P1));
                    engine.addEntity(newGameState);
                }
                if (p.playerNum == 2) {
                    Entity newGameState = engine.createEntity();
                    newGameState.add(new GameComponent().create().setState(GameComponent.GameState.GAME_WIN_P2));
                    engine.addEntity(newGameState);
                }

            }
        }
    }
}
