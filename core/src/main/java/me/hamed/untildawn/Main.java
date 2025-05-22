package me.hamed.untildawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.view.LoadingMenu;
import me.hamed.untildawn.view.PreGameMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    public static SpriteBatch batch;
    private me.hamed.untildawn.model.Game game = new me.hamed.untildawn.model.Game();
    public static Music backgroundMusic;
    private static float soundEffects = 0.5f;
    private boolean autoReload = true;
    private boolean blackAndWhite = false;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
//        main.setScreen(new PreGameMenu(GameAssetsManager.getInstance().getSkin()));
        main.setScreen(new LoadingMenu());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public me.hamed.untildawn.model.Game getGame() {
        return game;
    }

    public void setGame(me.hamed.untildawn.model.Game game) {
        this.game = game;
    }

    public static float getSoundEffects() {
        return soundEffects;
    }

    public static void setSoundEffects(float soundEffects) {
        Main.soundEffects = soundEffects;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }
}
