package me.hamed.untildawn.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private Image avatar;
    private int score;

    public User(String username, String password, Image avatar, int score) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.score = score;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
