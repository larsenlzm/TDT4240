package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public Vector2 velocity = new Vector2();

    public VelocityComponent create() {
        return new VelocityComponent();
    }

    public VelocityComponent set(Vector2 speed) {
        velocity = speed;
        return this;
    }
}
