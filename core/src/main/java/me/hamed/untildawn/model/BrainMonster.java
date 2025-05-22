package me.hamed.untildawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.hamed.untildawn.view.CollisionRect;
import me.hamed.untildawn.view.GameScreen;

import java.util.ArrayList;

public class BrainMonster extends Monster {

    public BrainMonster(float x, float y) {
        super(x, y, 150, 25, GameAssetsManager.getInstance().getIdleFrames("Brain Monster"));
    }

    public static void spawn(ArrayList<BrainMonster> brainMonsters, ArrayList<Monster> monsters) {
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

        BrainMonster monster = new BrainMonster(x, y);
        brainMonsters.add(monster);
        monsters.add(monster);
    }
}
