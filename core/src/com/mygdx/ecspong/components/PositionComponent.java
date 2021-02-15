package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public Vector2 pos = new Vector2();

    public static PositionComponent create(){
        return new PositionComponent();
    }

    public PositionComponent set(int x, int y) {
        this.pos = new Vector2(x,y);
        return this;
    }
}
