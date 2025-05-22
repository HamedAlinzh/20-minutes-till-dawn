package me.hamed.untildawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.Game;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.HeroActor;
import me.hamed.untildawn.model.Player;
import me.hamed.untildawn.view.GameScreen;

import java.io.IOException;

public class PreGameMenuController {
    public static void play(HeroActor hero, TextureRegion weapon, String timeSelected, Label errorLabel, String weaponName, boolean playAsGuest) throws IOException {
        if (hero == null) {
            errorLabel.setText("Select a Hero!");
            errorLabel.setVisible(true);
            return;
        }
        if (weapon == null) {
            errorLabel.setText("Select a Weapon!");
            errorLabel.setVisible(true);
            return;
        }
        Game game = Main.getMain().getGame();
        switch (timeSelected) {
            case "20 minutes": game.setTime(20 * 60); break;
            case "10 minutes": game.setTime(10 * 60); break;
            case "5 minutes": game.setTime(5 * 60); break;
            case "2.5 minutes": game.setTime(2 * 75); break;
        }
        Player player = new Player(hero, weapon, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, weaponName, game.getTime());
        game.setPlayer(player);
        Main.backgroundMusic.stop();
        Main.backgroundMusic = GameAssetsManager.getInstance().gameMusic();
        Main.backgroundMusic.setLooping(true);
        Main.backgroundMusic.setVolume(0.7f);
        Main.backgroundMusic.play();
        Main.getMain().setScreen(new GameScreen(false, playAsGuest));
    }
}
