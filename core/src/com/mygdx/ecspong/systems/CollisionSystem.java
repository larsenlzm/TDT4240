package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.PlayerComponent;
import com.mygdx.ecspong.components.PolygonComponent;
import com.mygdx.ecspong.components.VelocityComponent;

public class CollisionSystem extends EntitySystem {
    private ImmutableArray<Entity> polys;
    private ImmutableArray<Entity> circles;

    private ComponentMapper<PolygonComponent> pm = ComponentMapper.getFor(PolygonComponent.class);
    private ComponentMapper<CircleComponent> cm = ComponentMapper.getFor(CircleComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);


    public void addedToEngine(Engine engine) {
        polys = engine.getEntitiesFor(Family.all(PolygonComponent.class).get());
        circles = engine.getEntitiesFor(Family.all(CircleComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity poly : polys) {
            PolygonComponent p = pm.get(poly);

            if (p.bounds.getY() > Gdx.graphics.getHeight()-p.height){
                p.setPos(new Vector2(p.bounds.getX(), Gdx.graphics.getHeight()-p.height));
            }
            if (p.bounds.getY() <= 0) {
                p.setPos(new Vector2(p.bounds.getX(), 1));
            }
        }
        for (Entity ec : circles) {
            CircleComponent circle = cm.get(ec);
            VelocityComponent v = vm.get(ec);
            for (Entity ep : polys) {
                PolygonComponent poly = pm.get(ep);

                if (isCircleCollidingWithPoly(poly.bounds, circle.bounds)){
                    //poly is wall
                    if (poly.width >= 200) {
                        v.set(new Vector2(v.velocity.x, -v.velocity.y));
                    } else { //poly is paddle
                        v.set(new Vector2(-v.velocity.x, v.velocity.y));
                    }
                }
            }
        }
    }

    public boolean isCircleCollidingWithPoly(Polygon poly, Circle circle) {
        float[] vertices = poly.getTransformedVertices();
        Vector2 center = new Vector2(circle.x, circle.y);
        float squareRadius = circle.radius*circle.radius;
        for (int i = 0; i < vertices.length; i += 2){
            if (i == 0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return poly.contains(circle.x, circle.y);
    }
}
