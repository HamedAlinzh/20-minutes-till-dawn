package me.hamed.untildawn.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public abstract class Weapon {
    protected float fireRate;
    protected int maxAmmo;
    protected float damage;
    protected float reloadTime;
    protected int projectile;

    protected float cooldown = 0;

    public abstract void shoot(Vector2 origin, float angle, ArrayList<Bullet> bullets);

    public boolean canShoot() {
        return cooldown <= 0;
    }

    public void resetCooldown() {
        cooldown = 1f / fireRate;
    }
    public float getDamage() {
        return damage;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public int getProjectile() {
        return projectile;
    }

    public  int getMaxAmmo() {
        return maxAmmo;
    }

    @Override
    public String toString() {
        if (this instanceof Revolver) {
            return "Revolver";
        } else if (this instanceof Shotgun) {
            return "Shotgun";
        } else if (this instanceof DualSMG) {
            return "DualSMG";
        }
        return super.toString();
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }
    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }
}
