package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MonsterDeath {
    public static final float FRAME_LENGTH = 0.1f;
    public static final int OFFSET = 0;
    public static final int SIZE = 32;

    private static Animation<TextureRegion> animation = new Animation<>(0.1f, GameAssetsManager.getInstance().getIdleFrames("Monster Death"));
    float x, y;
    float stateTime = 0;
    public boolean remove = false;

    public MonsterDeath(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) {
            remove = true;
        }
    }
    public void draw(Batch batch) {
        batch.draw(animation.getKeyFrame(stateTime), x, y, SIZE, SIZE);
    }
}
