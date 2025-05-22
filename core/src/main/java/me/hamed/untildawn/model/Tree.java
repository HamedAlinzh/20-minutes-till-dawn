package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Tree {
    private float x, y;
    private float width = 70 * 2, height = 92 * 2;
    private Texture texture = GameAssetsManager.getInstance().getIdleFrames("Tree")[0].getTexture();
    protected Animation<TextureRegion> animation;
    private boolean playingAnimation = false;
    private float stateTime = 0f;
    private boolean playAnimation = false;

    public Tree(float x, float y) {
        this.animation = new Animation<>(0.5f, cloneFrames(GameAssetsManager.getInstance().getIdleFrames("Tree")));
        this.animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.x = x;
        this.y = y;
    }

    private TextureRegion[] cloneFrames(TextureRegion[] original) {
        TextureRegion[] clone = new TextureRegion[original.length];
        for (int i = 0; i < original.length; i++) {
            clone[i] = new TextureRegion(original[i]);
        }
        return clone;
    }

    public void draw(Batch batch) {
        if (playingAnimation) {
            TextureRegion currentFrame = animation.getKeyFrame(stateTime);
            batch.draw(currentFrame, x, y, width, height);
        } else {
            batch.draw(texture, x, y, width, height); // draw static tree
        }
    }

    public void playAnimation() {
        this.playingAnimation = true;
        this.playAnimation = true;
        this.stateTime = 0f;
    }

    public void moveRelativeToPlayer(float playerDx, float playerDy, float delta) {
        this.x -= playerDx;
        this.y -= playerDy;
        stateTime += delta;
    }

    public static void spawnTree(float x, float y, ArrayList<Tree> trees) {
        Tree tree = new Tree(x, y);
        trees.add(tree);
    }

    public void update(float delta) {
        if (playingAnimation) {
            stateTime += delta;
            if (animation.isAnimationFinished(stateTime)) {
                playingAnimation = false;
                playAnimation = false;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + width / 3f, y, width / 3f, height / 3);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public static ArrayList<Tree> generateTrees(int count) {
        ArrayList<Tree> trees = new ArrayList<>();

        int attempts = 0;
        int maxAttempts = count * 10;

        while (trees.size() < count && attempts < maxAttempts) {
            float x = MathUtils.random(-15000, 15000);
            float y = MathUtils.random(-15000, 15000);
            boolean tooClose = false;

            for (Tree tree : trees) {
                float dx = tree.getX() - x;
                float dy = tree.getY() - y;
                float distanceSquared = dx * dx + dy * dy;

                if (distanceSquared < 300 * 300) {
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                Tree.spawnTree(x, y, trees);
            }

            attempts++;
        }

        return trees;
    }

    public boolean isPlayAnimation() {
        return playAnimation;
    }

    public void setPlayAnimation(boolean playAnimation) {
        this.playAnimation = playAnimation;
    }
}
