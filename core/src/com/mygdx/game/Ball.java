package com.mygdx.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Circle {
    private float xSpeed;
    private float ySpeed;

    public Ball (float xSpawn, float ySpawn, float radius, float speed) {
        super(xSpawn, ySpawn, radius);

        if (Math.random() > 0.5) {
            this.xSpeed = speed;
        } else {
            this.xSpeed = -speed;
        }

        this.ySpeed = speed;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isCollidingWithPolygon(Polygon poly) {
        float[] vertices = poly.getTransformedVertices();
        Vector2 center = new Vector2(this.x, this.y);
        float squareRadius = this.radius*this.radius;
        for (int i = 0; i < vertices.length; i += 2){
            if (i == 0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return poly.contains(this.x, this.y);
    }
}
