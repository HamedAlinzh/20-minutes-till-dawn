package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Heart {
    int health;
    int maxHealth;
    float stateTime;
    protected Animation<TextureRegion> animation;
    private Texture emptyHeart = GameAssetsManager.getInstance().emptyHeart();

    // Position and size of one heart
    float heartWidth = 48;
    float heartHeight = 48;

    public Heart(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.animation = new Animation<>(0.2f, GameAssetsManager.getInstance().getIdleFrames("Heart"));
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void drawHearts(Batch batch, float startX, float startY) {
        TextureRegion fullHeartFrame = animation.getKeyFrame(stateTime, true);

        for (int i = 0; i < maxHealth; i++) {
            float x = startX + i * (heartWidth + 4);

            if (i < health) {
                batch.draw(fullHeartFrame, x, startY, heartWidth, heartHeight);
            } else {
                batch.draw(emptyHeart, x, startY, heartWidth, heartHeight);
            }
        }
    }

    // Optional: update health
    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }
}
