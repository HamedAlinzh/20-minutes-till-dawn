package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.hamed.untildawn.model.*;

import java.util.ArrayList;
import java.util.Iterator;

public class EyeBat extends Monster {

    private ArrayList<EyeBatBullet> bullets = new ArrayList<>();
    private float timeSinceLastShot = 0f;
    private float shootInterval = 3f; // 3 seconds

    private Texture texture = GameAssetsManager.getInstance().getEyeBatBulletTexture();


    public EyeBat(float x, float y) {
        super(x, y, 100, 50, GameAssetsManager.getInstance().getIdleFrames("Eye Bat"));
    }

    public static void spawn(ArrayList<EyeBat> eyeBats, ArrayList<Monster> monsters) {
        float x = 0, y = 0;
        int edge = MathUtils.random(3);

        switch (edge) {
            case 0:
                x = MathUtils.random(0, Gdx.graphics.getWidth());
                y = Gdx.graphics.getHeight();
                break;
            case 1:
                x = MathUtils.random(0, Gdx.graphics.getWidth());
                y = 0;
                break;
            case 2:
                x = 0;
                y = MathUtils.random(0, Gdx.graphics.getHeight());
                break;
            case 3:
                x = Gdx.graphics.getWidth();
                y = MathUtils.random(0, Gdx.graphics.getHeight());
                break;
        }

        EyeBat eyeBat = new EyeBat(x, y);
        eyeBats.add(eyeBat);
        monsters.add(eyeBat);
    }

    public ArrayList<EyeBatBullet> getBullets() {
        return bullets;
    }

    public void update(float delta, float playerX, float playerY) {
        super.update(delta, playerX, playerY); // if your Monster class has logic

        timeSinceLastShot += delta;

        if (timeSinceLastShot >= shootInterval) {
            shootAtPlayer(playerX, playerY);
            timeSinceLastShot = 0f;
        }

        // Update bullets
        for (EyeBatBullet bullet : bullets) {
            bullet.update(delta);
        }
    }


    private void shootAtPlayer(float playerX, float playerY) {
        Vector2 batCenter = new Vector2(getX() + width / 2f, getY() + height / 2f);
        Vector2 playerCenter = new Vector2(playerX, playerY);
        Vector2 direction = playerCenter.sub(batCenter).nor();

        EyeBatBullet bullet = new EyeBatBullet(batCenter.x, batCenter.y, direction); // you can customize damage
        bullets.add(bullet);
    }


}
