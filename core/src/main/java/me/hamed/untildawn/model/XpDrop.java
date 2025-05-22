package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import me.hamed.untildawn.view.CollisionRect;

import java.util.ArrayList;

public class XpDrop {
    private float x, y;
    private static final float WIDTH = 20, HEIGHT = 20;
    private boolean remove = false;
    private Texture texture = GameAssetsManager.getInstance().getXpTexture();

    public XpDrop(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void spawn(float x, float y, ArrayList<XpDrop> xpDrops) {
        XpDrop xpDrop = new XpDrop(x, y);
        xpDrops.add(xpDrop);
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, WIDTH, WIDTH);
    }

    public boolean remove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void moveRelativeToPlayer(float playerDx, float playerDy) {
        this.x -= playerDx;
        this.y -= playerDy;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX() - WIDTH * 2, getY() - HEIGHT * 2, WIDTH * 4, HEIGHT * 4);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
