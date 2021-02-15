package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.CircleComponent;
import com.mygdx.ecspong.components.PolygonComponent;
import com.mygdx.ecspong.components.VelocityComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<CircleComponent> cm = ComponentMapper.getFor(CircleComponent.class);
    private ComponentMapper<PolygonComponent> pm = ComponentMapper.getFor(PolygonComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            CircleComponent c = cm.get(entity);
            PolygonComponent p = pm.get(entity);
            VelocityComponent v = vm.get(entity);

            if (p == null) {
                Vector2 cPos = c.getPos();

                cPos.x += v.velocity.x * deltaTime;
                cPos.y += v.velocity.y * deltaTime;

                c.setPos(cPos);
            } else {
                Vector2 pPos = p.getPos();

                pPos.x += v.velocity.x * deltaTime;
                pPos.y += v.velocity.y * deltaTime;

                p.setPos(pPos);
            }

        }
    }
}
