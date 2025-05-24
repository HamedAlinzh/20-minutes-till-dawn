package me.hamed.untildawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimatedBorder {
    private Animation<TextureRegion> horizontalAnim;
    private Animation<TextureRegion> verticalAnim;
    private float stateTime = 0;
    private int x = -600;
    private int y = -600;
    private final float width = Gdx.graphics.getWidth() * 1.3f;
    private final float height = Gdx.graphics.getHeight() * 1.3f;
    float thickness = 10f;
    ArrayList<Rectangle> hitBoxes = new ArrayList<>();

    private Rectangle top, bottom, left, right;


    public AnimatedBorder() {
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            frames[i] = new TextureRegion(GameAssetsManager.getInstance().getRunFrames("Azina")[i]);
        }
        horizontalAnim = new Animation<>(0.1f, frames);

        TextureRegion[] vFrames = new TextureRegion[frames.length];
        for (int i = 0; i < frames.length; i++) {
            vFrames[i] = new TextureRegion(frames[i]);
            vFrames[i] = rotateTexture(vFrames[i].getTexture());
        }
        verticalAnim = new Animation<>(0.1f, vFrames);

        top = new Rectangle(x, y + height - thickness, width, thickness);
        bottom = new Rectangle(x, y, width, thickness);
        left = new Rectangle(x, y, thickness, height);
        right = new Rectangle(x + width - thickness, y, thickness, height);
        hitBoxes.add(top);
        hitBoxes.add(bottom);
        hitBoxes.add(left);
        hitBoxes.add(right);
    }

    public void draw(Batch batch, float delta) {
        stateTime += delta;

        TextureRegion topFrame = horizontalAnim.getKeyFrame(stateTime, true);
        TextureRegion bottomFrame = horizontalAnim.getKeyFrame(stateTime, true);
        batch.draw(topFrame, x, y + height - topFrame.getRegionHeight(), width, topFrame.getRegionHeight());
        batch.draw(bottomFrame, x, y, width, bottomFrame.getRegionHeight());

        TextureRegion leftFrame = verticalAnim.getKeyFrame(stateTime, true);
        TextureRegion rightFrame = verticalAnim.getKeyFrame(stateTime, true);
        batch.draw(leftFrame, x, y, leftFrame.getRegionWidth(), height);
        batch.draw(rightFrame, x + width - rightFrame.getRegionWidth(), y, rightFrame.getRegionWidth(), height);
    }

    public TextureRegion rotateTexture(Texture originalTexture) {
        originalTexture.getTextureData().prepare();
        Pixmap originalPixmap = originalTexture.getTextureData().consumePixmap();
        Pixmap rotatedPixmap = new Pixmap(originalPixmap.getHeight(), originalPixmap.getWidth(), originalPixmap.getFormat());
        for (int x = 0; x < originalPixmap.getWidth(); x++) {
            for (int y = 0; y < originalPixmap.getHeight(); y++) {
                int pixel = originalPixmap.getPixel(x, y);
                rotatedPixmap.drawPixel(originalPixmap.getHeight() - y - 1, x, pixel);
            }
        }
        Texture rotatedTexture = new Texture(rotatedPixmap);
        return new TextureRegion(rotatedTexture);
    }

    public void moveRelativeToPlayer(float playerDx, float playerDy, float delta) {
        this.x -= playerDx;
        this.y -= playerDy;
        stateTime += delta;
        top.setPosition(x, y + height - thickness);
        bottom.setPosition(x, y);
        left.setPosition(x, y);
        right.setPosition(x + width - thickness, y);
    }

    public ArrayList<Rectangle> getHitboxes() {
        return hitBoxes;
    }
}
