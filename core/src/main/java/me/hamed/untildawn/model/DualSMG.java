package me.hamed.untildawn.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class DualSMG extends Weapon{
    public DualSMG() {
        this.fireRate = 4.0f; // slower
        this.maxAmmo = 24;
        this.damage = 8f;
        this.projectile = 1;
        this.reloadTime = 2;
    }

    @Override
    public void shoot(Vector2 origin, float angle, ArrayList<Bullet> bullets) {
        if (!canShoot()) return;
        resetCooldown();

        Vector2 dir = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).nor();
        bullets.add(new Bullet(origin.x, origin.y, dir));
    }
}
