package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class CircleComponent implements Component {
    public Circle bounds = new Circle();

    public CircleComponent create(float radius) {
        this.bounds = new Circle(0, 0, radius);
        return this;
    }

    public CircleComponent setPos(Vector2 pos) {
        bounds.setPosition(pos);
        return this;
    }

    public Vector2 getPos() {
        return new Vector2(bounds.x, bounds.y);
    }
}
