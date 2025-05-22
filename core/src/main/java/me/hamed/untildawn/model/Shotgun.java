package me.hamed.untildawn.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Shotgun extends Weapon {
    public Shotgun() {
        this.fireRate = 1.0f; // slower
        this.maxAmmo = 2;
        this.damage = 10f;
        this.projectile = 4;
        this.reloadTime = 1;
    }

    @Override
    public void shoot(Vector2 origin, float angle, ArrayList<Bullet> bullets) {
        if (!canShoot()) return;
        resetCooldown();

        Vector2 dir = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).nor();
        bullets.add(new Bullet(origin.x, origin.y, dir));
    }
}
