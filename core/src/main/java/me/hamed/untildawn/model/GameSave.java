package me.hamed.untildawn.model;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import me.hamed.untildawn.model.GameSaves.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSave implements Serializable {

    public float worldX, worldY;
    public int level;
    public int currentXp;
    public int xpToNextLevel;
    public PlayerSave player;
    public ArrayList<MonsterSave> monsters;
    public ArrayList<BulletSave> bullets;
    public ArrayList<XpDropSave> xpDrops;
    public ArrayList<TreeSave> trees;
    public WeaponSave weapon;
    public float shootTimer;
    public float reloadTimer;
    public boolean reloading;
    public float countdownTime;
    public float enemySpawnTimer;
    public float eyeBatSpawnTimer;
    public String heroName;
    public String weaponName;
    public float spawnInterval;
    public float eyeBatSpawnInterval;
    public float gameTime;
    public int kills;
    public float fireRate;
    public int maxAmo;
    public float reloadRate;
    public int projectiles;
}
