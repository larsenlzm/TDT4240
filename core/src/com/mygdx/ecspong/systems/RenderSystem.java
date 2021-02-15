package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.PolygonComponent;
import com.mygdx.ecspong.components.PositionComponent;
import com.badlogic.ashley.core.Engine;

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> polygonEntities;
    private ImmutableArray<Entity> circleEntities;
    private ShapeRenderer shapeRenderer;

    public RenderSystem () {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void addedToEngine (Engine engine) {
        polygonEntities = engine.getEntitiesFor(Family.all(PolygonComponent.class).get());
        circleEntities = engine.getEntitiesFor(Family.all(CircleComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Entity entity : polygonEntities) {
            shapeRenderer.polygon(entity.getComponent(PolygonComponent.class).bounds.getTransformedVertices()); //walltop
        }

        for (Entity circle : circleEntities){
            Circle ballBounds = circle.getComponent(CircleComponent.class).bounds;
            shapeRenderer.circle(ballBounds.x, ballBounds.y, ballBounds.radius);
        }

        shapeRenderer.end();
    }
}
