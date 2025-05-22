package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    HeroActor hero;
    TextureRegion weaponTexture;
    public Vector2 position;
    private Weapon weapon;
    private int health;
    private String heroName;
    private float gameTime;

    public Player(HeroActor hero, TextureRegion weaponTexture, float x, float y, String name, float gameTime) {
        this.hero = hero;
        this.weaponTexture = weaponTexture;
        this.gameTime = gameTime;
        position = new Vector2(x, y);
        switch (name) {
            case "Revolver": {
                weapon = new Revolver();
                break;
            }
            case "Shotgun": {
                weapon = new Shotgun();
                break;
            }
            case "Dual SMGs": {
                weapon = new DualSMG();
                break;
            }
        }
        this.health = hero.getHealth();
        this.heroName = hero.getName();
    }

    public HeroActor getHero() {
        return hero;
    }

    public void setHero(HeroActor hero) {
        this.hero = hero;
    }

    public Texture getWeapon() {
        return weaponTexture.getTexture();
    }

    public TextureRegion getWeaponRegion() {
        return weaponTexture;
    }

    public Weapon getCurrentWeapon() {
        return weapon;
    }

    public int getHealth() {
        return health;
    }

    public TextureRegion getWeaponTexture() {
        return weaponTexture;
    }

    public void setWeaponTexture(TextureRegion weaponTexture) {
        this.weaponTexture = weaponTexture;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public float getGameTime() {
        return gameTime;
    }
}
