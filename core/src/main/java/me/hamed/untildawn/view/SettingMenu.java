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
    private Label music;
    private Label soundEffect;
    private Slider soundEffectsVolumeSlider;
    private TextButton keyboard;
    private TextButton autoReload;
    private TextButton blackAndWhite;
    private TextButton back;


    public SettingMenu(Skin skin) {
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(0.5f); // Default volume (50%)
        table = new Table(skin);
        music = new Label("Music", skin);
        soundEffect = new Label("Sound Effects", skin);
        soundEffectsVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundEffectsVolumeSlider.setValue(0.5f);
        keyboard = new TextButton("Change Keyboard", skin);
        autoReload = new TextButton("Auto Reload: " + (Main.getMain().isAutoReload() ? "ON" : "OFF"), skin);
        blackAndWhite = new TextButton("Black and White:" + (Main.getMain().isBlackAndWhite() ? "ON" : "OFF"), skin);
        back = new TextButton("Back", skin);
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
                    Main.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/AudioClip/Pretty Dungeon LOOP.wav"));
                    Main.backgroundMusic.play();
                    Main.backgroundMusic.setLooping(true);
                } else if (item.equals("Cristal Castles Empathy")) {
                    Main.backgroundMusic.stop();
                    Main.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/Crystal Castles - Empathy.mp3"));
                    Main.backgroundMusic.play();
                    Main.backgroundMusic.setLooping(true);
                }
            }
        });
        musics.setItems(new Array<>(new String[]{"Original Theme", "Cristal Castles Empathy"}));
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
                float volume = volumeSlider.getValue();
                Main.setSoundEffects(volume);
            }
        });
        table.row().pad(15);
        table.add(keyboard).size(550, 110);
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
            Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
        }

        if (keyboard.isChecked()) {
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
