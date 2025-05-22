package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.view.CollisionRect;

public class Bullet {
    private float decelerationRate = 1000f;
    private Vector2 position;
    private Vector2 velocity;
    private float speed = 1500;
    private float lifetime = 1.5f;
    private boolean active = true;
    private CollisionRect rect;

    private static float width = 8;
    private static float height = 4;

    private Texture texture;

    public Bullet(float x, float y, Vector2 direction) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(direction).nor(); // normalized direction

        this.texture = GameAssetsManager.getInstance().getBulletTexture();
        this.rect = new CollisionRect(x, y, width, height);
    }


    public void update(float delta) {
        if (!active) return;
            // Reduce speed over time
        speed -= decelerationRate * delta;

            // Clamp to prevent negative speed
        if (speed < 0) speed = 0;

            // Move bullet based on reduced speed
        position.x += velocity.x * speed * delta;
        position.y += velocity.y * speed * delta;
        lifetime -= delta;

        if (lifetime <= 0f) {
            active = false;
        }
        rect.move(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        if (!active) return;
        float angle = velocity.angleDeg();
        batch.draw(
            texture,
            position.x,
            position.y,
            width / 2f,
            height / 2f,
            width * 2,
            height * 2,
            1f,
            1f,
            angle,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public void deactivate() {
        active = false;
    }

    public int getX() {
        return (int) position.x;
    }
    public int getY() {
        return (int) position.y;
    }
    public Vector2 getVelocity() {
        return velocity;
    }


}
