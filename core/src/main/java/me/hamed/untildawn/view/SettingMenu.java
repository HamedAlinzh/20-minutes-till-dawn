package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.TextButtonDropdown;

public class SettingMenu implements Screen {
    private Stage stage;
    private Slider volumeSlider;
    private Table table;
    private TextButtonDropdown<String> musics;
    private TextButtonDropdown<String> language;
    private Label music;
    private Label soundEffect;
    private Slider soundEffectsVolumeSlider;
    private TextButton keyboard;
    private TextButton autoReload;
    private TextButton blackAndWhite;
    private TextButton back;
    private GameScreen gameScreen;
    private boolean inGame;


    public SettingMenu(Skin skin, boolean inGame, GameScreen gameScreen) {
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(Main.getSoundVolume()); // Default volume (50%)
        this.inGame = inGame;
        table = new Table(skin);
        music = new Label((Main.getMain().getLanguage().equals("english")) ? Texts.MUSIC.getEnglish() : Texts.MUSIC.getFrench(), skin);
        soundEffect = new Label((Main.getMain().getLanguage().equals("english")) ? Texts.SOUND_EFFECTS.getEnglish() : Texts.SOUND_EFFECTS.getFrench(), skin);
        soundEffectsVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundEffectsVolumeSlider.setValue(Main.getSoundEffects());
        keyboard = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.CHANGE_KEYBOARD.getEnglish() : Texts.CHANGE_KEYBOARD.getFrench(), skin);
        autoReload = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.AUTO_RELOAD.getEnglish() : Texts.LOGIN_MENU.getFrench() + " " + (Main.getMain().isAutoReload() ? (Main.getMain().getLanguage().equals("english")) ? Texts.ON.getEnglish() : Texts.ON.getFrench() : (Main.getMain().getLanguage().equals("english")) ? Texts.OFF.getEnglish() : Texts.OFF.getFrench()), skin);
        blackAndWhite = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.BLACK_AND_WHITE.getEnglish() : Texts.BLACK_AND_WHITE.getFrench() + " " + (Main.getMain().isBlackAndWhite() ? (Main.getMain().getLanguage().equals("english")) ? Texts.ON.getEnglish() : Texts.ON.getFrench() : (Main.getMain().getLanguage().equals("english")) ? Texts.OFF.getEnglish() : Texts.OFF.getFrench()), skin);
        back = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.BACK.getEnglish() : Texts.BACK.getFrench(), skin);
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        musics = new TextButtonDropdown<>("Musics", GameAssetsManager.getInstance().getSkin(), stage, new TextButtonDropdown.SelectionListener<String>() {
            @Override
            public void onSelected(String item) {
                if (item.equals("Original Theme")) {
                    Main.backgroundMusic.stop();
                    Main.backgroundMusic = GameAssetsManager.getInstance().menuMusic();
                    Main.backgroundMusic.play();
                    Main.backgroundMusic.setVolume(Main.getSoundVolume());
                    Main.backgroundMusic.setLooping(true);
                } else if (item.equals("Cristal Castles Empathy")) {
                    Main.backgroundMusic.stop();
                    Main.backgroundMusic = GameAssetsManager.getInstance().otherMusic();
                    Main.backgroundMusic.play();
                    Main.backgroundMusic.setVolume(Main.getSoundVolume());
                    Main.backgroundMusic.setLooping(true);
                } else if (item.equals("The Last Of Us Theme")) {
                    Main.backgroundMusic.stop();
                    Main.backgroundMusic = GameAssetsManager.getInstance().tlouMusic();
                    Main.backgroundMusic.play();
                    Main.backgroundMusic.setVolume(Main.getSoundVolume());
                    Main.backgroundMusic.setLooping(true);
                }
            }
        });

        language = new TextButtonDropdown<>("Language", GameAssetsManager.getInstance().getSkin(), stage, new TextButtonDropdown.SelectionListener<String>() {

            @Override
            public void onSelected(String item) {

                if (item.equals("English")) {
                    Main.getMain().setLanguage("english");
                    Main.getMain().setScreen(new SettingMenu(GameAssetsManager.getInstance().getSkin(), inGame, gameScreen));
                } else if (item.equals("French")) {
                    Main.getMain().setLanguage("french");
                    Main.getMain().setScreen(new SettingMenu(GameAssetsManager.getInstance().getSkin(), inGame, gameScreen));
                }
            }
        });
        language.setItems(new Array<>(new String[]{"English", "French"}));
        musics.setItems(new Array<>(new String[]{"Original Theme", "Cristal Castles Empathy", "The Last Of Us Theme"}));
        musics.setSize(550, 110);
        table.add(musics).size(550, 110);
        table.row().pad(20);
        table.add(music);
        table.row().pad(10);
        table.add(volumeSlider).width(650);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                if (Main.backgroundMusic != null) {
                    Main.backgroundMusic.setVolume(volume);
                    Main.setSoundVolume(volume);
                }
            }
        });
        table.row().pad(15);
        table.add(soundEffect);
        table.row().pad(10);
        table.add(soundEffectsVolumeSlider).width(650);
        soundEffectsVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = soundEffectsVolumeSlider.getValue();
                Main.setSoundEffects(volume);
            }
        });
        table.row().pad(15);
        table.add(keyboard).size(550, 110);
        table.row().pad(15);
        language.setSize(550, 110);
        table.add(language).size(550, 110);
        table.row().pad(15);
        autoReload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = autoReload.isChecked();
                autoReload.setText("Auto Reload: " + (enabled ? "OFF" : "ON"));
                Main.getMain().setAutoReload(!enabled);
            }
        });
        table.add(autoReload).size(550, 110);
        table.row().pad(15);
        blackAndWhite.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = blackAndWhite.isChecked();
                blackAndWhite.setText("Black and White: " + (enabled ? "ON" : "OFF"));
                Main.getMain().setBlackAndWhite(enabled);
            }
        });

        table.add(blackAndWhite).size(550, 110);
        table.row().pad(15);
        table.add(back).size(550, 110);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();

        if (back.isChecked()) {
            if (inGame) {
                Main.getMain().setScreen(new PauseMenu(GameAssetsManager.getInstance().getSkin(), gameScreen, GameAssetsManager.getInstance().getPauseBg()));
            } else
                Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
        }

        if (!inGame && keyboard.isChecked()) {
            Main.getMain().setScreen(new KeyBoardBindingScreen(GameAssetsManager.getInstance().getSkin()));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
