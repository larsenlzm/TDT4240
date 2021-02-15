package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.GameComponent;
import com.mygdx.ecspong.components.PlayerComponent;
import com.mygdx.ecspong.components.PolygonComponent;
import com.badlogic.ashley.core.Engine;

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> polygonEntities;
    private ImmutableArray<Entity> circleEntities;
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> games;

    private final ShapeRenderer shapeRenderer;
    private final BitmapFont fontRenderer;
    private final SpriteBatch spriteBatch;


    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);

    public RenderSystem (ShapeRenderer sr, BitmapFont fr, SpriteBatch sb) {
        this.shapeRenderer = sr;
        this.fontRenderer = fr;
        this.spriteBatch = sb;
    }

    @Override
    public void addedToEngine (Engine engine) {
        polygonEntities = engine.getEntitiesFor(Family.all(PolygonComponent.class).get());
        circleEntities = engine.getEntitiesFor(Family.all(CircleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        games = engine.getEntitiesFor(Family.all(GameComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        for (Entity game : games) {
            GameComponent gameComponent = gm.get(game);
            System.out.println(gameComponent.gameState);
            if (gameComponent.gameState == GameComponent.GameState.GAME_PLAYING) {

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                for (Entity entity : polygonEntities) {
                    shapeRenderer.polygon(entity.getComponent(PolygonComponent.class).bounds.getTransformedVertices()); //walltop
                }

                for (Entity circle : circleEntities) {
                    Circle ballBounds = circle.getComponent(CircleComponent.class).bounds;
                    shapeRenderer.circle(ballBounds.x, ballBounds.y, ballBounds.radius);
                }

                int p1Score = 0;
                int p2Score = 0;

                for (Entity player : playerEntities) {
                    PlayerComponent pc = pm.get(player);
                    if (pc.playerNum == 1) p1Score = pc.score;
                    if (pc.playerNum == 2) p2Score = pc.score;
                }

                shapeRenderer.end();
                spriteBatch.begin();
                fontRenderer.draw(spriteBatch, "" + p1Score, (Gdx.graphics.getWidth() / 2) - 50, Gdx.graphics.getHeight() - 50);
                fontRenderer.draw(spriteBatch, "" + p2Score, (Gdx.graphics.getWidth() / 2) + 50, Gdx.graphics.getHeight() - 50);
                spriteBatch.end();

            } else if (gameComponent.gameState == GameComponent.GameState.GAME_WIN_P1) {
                spriteBatch.begin();
                fontRenderer.draw(spriteBatch, "Player 1 is the winner!", (Gdx.graphics.getWidth() / 2) - 50, Gdx.graphics.getHeight() - 50);
                spriteBatch.end();
            } else if (gameComponent.gameState == GameComponent.GameState.GAME_WIN_P2) {
                spriteBatch.begin();
                fontRenderer.draw(spriteBatch, "Player 2 is the winner!", (Gdx.graphics.getWidth() / 2) - 50, Gdx.graphics.getHeight() - 50);
                spriteBatch.end();
            }
        }
    }
}
