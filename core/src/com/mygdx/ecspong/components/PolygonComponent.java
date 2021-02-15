package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class PolygonComponent implements Component {
    public Polygon bounds = new Polygon();

    public PolygonComponent create(float width, float height) {
        this.bounds = new Polygon(new float[]{0, 0, width, 0, width, height, 0, height});
        return this;
    }

    public PolygonComponent set(Vector2 pos) {
        bounds.setPosition(pos.x, pos.y);
        return this;
    }
}
