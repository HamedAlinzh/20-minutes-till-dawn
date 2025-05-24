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

    boolean isBoss;
    public static int bosses = 0;

    public BrainMonster(float x, float y, boolean isBoss) {
        super(x, y, 150, 25, GameAssetsManager.getInstance().getIdleFrames("Brain Monster"));
        this.isBoss = isBoss;
    }

    public static void spawn(ArrayList<BrainMonster> brainMonsters, ArrayList<Monster> monsters, boolean isElder) {
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

        if (isElder) {
            if (bosses == 1) {
                BrainMonster monster = new BrainMonster(x, y, true);
                monster.setHb(1000);
                Animation<TextureRegion> animation1 = new Animation(0.2f, GameAssetsManager.getInstance().getIdleFrames("ShubNiggurath"));
                animation1.setPlayMode(Animation.PlayMode.LOOP);
                monster.setAnimation(GameAssetsManager.getInstance().getIdleFrames("ShubNiggurath"));
                monster.setWidth(200);
                monster.setHeight(125);
                monsters.add(monster);
                brainMonsters.add(monster);
            } else {
                BrainMonster monster = new BrainMonster(x, y, false);
                monster.setHb(700);
                Animation<TextureRegion> animation1 = new Animation(10f, GameAssetsManager.getInstance().getIdleFrames("Elder Brain"));
                animation1.setPlayMode(Animation.PlayMode.LOOP);
                monster.setAnimation(GameAssetsManager.getInstance().getIdleFrames("Elder Brain"));
                monster.setWidth(120);
                monster.setHeight(160);
                monsters.add(monster);
                brainMonsters.add(monster);
                bosses++;
            }
        } else {
            BrainMonster monster = new BrainMonster(x, y, false);
            brainMonsters.add(monster);
            monsters.add(monster);
        }
    }

    public boolean isBoss() {
        return isBoss;
    }
}
