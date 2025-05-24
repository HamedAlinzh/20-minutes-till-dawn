package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.hamed.untildawn.view.CollisionRect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;


public abstract class Monster {
    protected float x, y;
    protected float width, height;
    protected float speed;
    protected int health;
    protected Animation<TextureRegion> animation;
    protected float stateTime;
    protected boolean isFlipped = false;
    protected CollisionRect rect;
    protected TextureRegion[] textureRegion;
    protected boolean remove = false;

    public Monster(float x, float y, float speed, int health, TextureRegion[] frames) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;

        this.textureRegion = frames;
        this.animation = new Animation<>(0.2f, cloneFrames(textureRegion));
        this.animation.setPlayMode(Animation.PlayMode.LOOP);
        this.width = frames[0].getRegionWidth() * 1.5f;
        this.height = frames[0].getRegionHeight() * 1.5f;

        this.rect = new CollisionRect(x, y, width, height);
    }

    public void update(float delta, float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1f) {
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
        }

        if (dx < 0 && !isFlipped) {
            flipAnimation(true);
            isFlipped = true;
        } else if (dx > 0 && isFlipped) {
            flipAnimation(false);
            isFlipped = false;
        }

        rect.move(x, y);
        stateTime += delta;
    }

    public void draw(Batch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime);
        batch.draw(currentFrame, x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    protected void flipAnimation(boolean flipX) {
        for (TextureRegion frame : animation.getKeyFrames()) {
            if (frame.isFlipX() != flipX) {
                frame.flip(true, false);
            }
        }
    }

    private TextureRegion[] cloneFrames(TextureRegion[] original) {
        TextureRegion[] clone = new TextureRegion[original.length];
        for (int i = 0; i < original.length; i++) {
            clone[i] = new TextureRegion(original[i]);
        }
        return clone;
    }

    public boolean isRemove() { return remove; }
    public void setRemove(boolean remove) { this.remove = remove; }
    public float getX() { return x; }
    public float getY() { return y; }
    public void moveRelativeToPlayer(float dx, float dy) {
        this.x -= dx;
        this.y -= dy;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public int getHb() {
        return health;
    }

    public void setHb(int hb) {
        this.health = hb;
    }

    public void moveBy(float dx, float dy) {
        setPosition(getX() + dx, getY() + dy);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setAnimation(TextureRegion[] frames) {
        this.animation = new Animation<>(0.2f, frames);
        this.animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
