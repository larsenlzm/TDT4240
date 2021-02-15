package com.mygdx.ecspong.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ecspong.components.PlayerComponent;
import com.mygdx.ecspong.components.VelocityComponent;

public class InputSystem  extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity e : entities) {
            PlayerComponent p = pm.get(e);
            VelocityComponent v = vm.get(e);

            Vector2 newVel = new Vector2();
            float paddleSpeed = 666;

            if (Gdx.input.isKeyPressed(p.upKey)) {
                newVel.y += paddleSpeed;
            }
            if (Gdx.input.isKeyPressed(p.downKey)) {
                newVel.y -= paddleSpeed;
            }

            v.set(newVel);
        }
    }
}
