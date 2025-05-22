package me.hamed.untildawn.view;

public class CollisionRect {
    float x, y;
    float width, height;
    public CollisionRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;

    }
    public boolean collidesWith(CollisionRect other) {
        return x < other.width + other. x && y < other.width + other.y && x + width > other.x && y + height > other.y;
    }
}
