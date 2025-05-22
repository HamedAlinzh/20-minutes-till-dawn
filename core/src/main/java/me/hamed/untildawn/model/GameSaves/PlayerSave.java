package me.hamed.untildawn.model.GameSaves;

import java.io.Serializable;

public class PlayerSave implements Serializable {
    public float x, y;
    public int health;
    public boolean facingLeft;
    public int maxHealth;
}
