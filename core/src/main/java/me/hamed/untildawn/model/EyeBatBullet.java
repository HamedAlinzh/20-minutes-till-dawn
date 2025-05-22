package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EyeBatBullet {
    private Vector2 velocity;
    private Vector2 position;
    private float speed = 100f;
    private float lifetime = 5f;
    private boolean active = true;
    private static float width = 15;
    private static float height = 15;
    private Texture texture = GameAssetsManager.getInstance().getEyeBatBulletTexture();

    public EyeBatBullet(float x, float y, Vector2 direction) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(direction).nor();
    }

    public void update(float delta) {
        if (!active) {
            return;
        }
        position.x += velocity.x * speed * delta;
        position.y += velocity.y * speed * delta;
        lifetime -= delta;
        if (lifetime <= 0) {
            active = false;
        }
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

    public void moveRelativeToPlayer(float dx, float dy) {
        this.position.x -= dx;
        this.position.y -= dy;
    }
}
