package me.hamed.untildawn.model;

public class Game {
    private User loggedInUser;
    private Player player;
    private int time;
    public User getLoggedInUser() {
        return loggedInUser;
    }
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
}
