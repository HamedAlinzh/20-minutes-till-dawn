package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.controller.PreGameMenuController;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.HeroActor;
import java.io.IOException;


public class PreGameMenu implements Screen {
    private Stage stage;
    private boolean playAsGuest;
    private HeroActor selectedHero = null;
    private HeroActor selectedHeroForPreview = null;
    private HeroActor selectedWeapon = null;
    private static Table table;
    private static Table backTable;
    private final Texture background;
    private Texture[] heros = GameAssetsManager.getInstance().getHeros();
    TextureRegion[] shanaIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Shana");
    TextureRegion[] shanaRunFrames = GameAssetsManager.getInstance().getRunFrames("Shana");
    TextureRegion[] diamondIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Diamond");
    TextureRegion[] diamondRunFrames = GameAssetsManager.getInstance().getRunFrames("Diamond");
    TextureRegion[] scarlettIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Scarlett");
    TextureRegion[] scarlettRunFrames = GameAssetsManager.getInstance().getRunFrames("Scarlett");
    TextureRegion[] lilithIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Lilith");
    TextureRegion[] lilithRunFrames = GameAssetsManager.getInstance().getRunFrames("Lilith");
    TextureRegion[] dasherIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Dasher");
    TextureRegion[] dasherRunFrames = GameAssetsManager.getInstance().getRunFrames("Dasher");

    TextureRegion[] revolverIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Revolver");
    TextureRegion[] revolverRunFrames = GameAssetsManager.getInstance().getRunFrames("Revolver");
    TextureRegion[] shotgunIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Shotgun");
    TextureRegion[] shotgunRunFrames = GameAssetsManager.getInstance().getRunFrames("Shotgun");
    TextureRegion[] dualSMGsIdleFrames = GameAssetsManager.getInstance().getIdleFrames("Dual SMGs");
    TextureRegion[] dualSMGRunFrames = GameAssetsManager.getInstance().getRunFrames("Dual SMGs");

    static HeroActor shana;
    static HeroActor diamond;
    static HeroActor scarlett;
    static HeroActor lilith;
    static HeroActor dasher;

    HeroActor revolver;
    HeroActor shotgun;
    HeroActor dualSMGs;

    private SelectBox<String> time;
    private Label timeLabel;
    private Label errorLabel;
    private TextButton backButton;
    private TextButton playButton;
    private Label nameLabel;

    public PreGameMenu(Skin skin, boolean playAsGuest) {
        table = new Table(skin);
        backTable = new Table(skin);
        background = new Texture("ChatGPT Image May 5, 2025, 01_30_18 PM.png");
        shana = new HeroActor(shanaIdleFrames, shanaRunFrames, 0.1f, 2, 1, heros[0], "Shana", 4);
        shana.setPosition(150, 600);
        diamond = new HeroActor(diamondIdleFrames, diamondRunFrames, 0.1f, 2, 1, heros[1], "Diamond", 7);
        diamond.setPosition(300, 600);
        scarlett = new HeroActor(scarlettIdleFrames, scarlettRunFrames, 0.1f, 2, 1, heros[2], "Scarlett", 3);
        scarlett.setPosition(450, 600);
        lilith = new HeroActor(lilithIdleFrames, lilithRunFrames, 0.1f, 2, 1, heros[3], "Lilith", 5);
        lilith.setPosition(600, 600);
        dasher = new HeroActor(dasherIdleFrames, dasherRunFrames, 0.1f, 2, 1, heros[4], "Dasher", 2);
        dasher.setPosition(750, 600);

        revolver = new HeroActor(revolverIdleFrames, revolverRunFrames, 0.2f, 3, 2, heros[0], "Revolver", -1);
        revolver.setPosition(300, 400);
        shotgun = new HeroActor(shotgunIdleFrames, shotgunRunFrames, 0.2f, 3, 2, heros[1], "Shotgun", -1);
        shotgun.setPosition(450, 400);
        dualSMGs = new HeroActor(dualSMGsIdleFrames, dualSMGRunFrames, 0.2f, 3, 2, heros[2], "Dual SMGs", -1);
        dualSMGs.setPosition(600, 400);

        time = new SelectBox<>(skin);
        time.setItems("20 minutes", "10 minutes", "5 minutes", "2.5 minutes");
        timeLabel = new Label("Select Game Duration:", skin);
        playButton = new TextButton("Play", skin);
        backButton = new TextButton("Back", skin);
        errorLabel = new Label("", skin);
        nameLabel = new Label("", skin);
        nameLabel.setColor(Color.RED);
        errorLabel.setColor(Color.RED);
        this.playAsGuest = playAsGuest;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        shana.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedHero != null) selectedHero.setSelected(false);
                selectedHero = shana;
                selectedHero.setSelected(true);

                selectedHeroForPreview = shana;
            }
        });
        diamond.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedHero != null) selectedHero.setSelected(false);
                selectedHero = diamond;
                selectedHero.setSelected(true);

                selectedHeroForPreview = diamond;
            }
        });
        scarlett.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedHero != null) selectedHero.setSelected(false);
                selectedHero = scarlett;
                selectedHero.setSelected(true);

                selectedHeroForPreview = scarlett;
            }
        });
        lilith.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedHero != null) selectedHero.setSelected(false);
                selectedHero = lilith;
                selectedHero.setSelected(true);

                selectedHeroForPreview = lilith;
            }
        });
        dasher.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedHero != null) selectedHero.setSelected(false);
                selectedHero = dasher;
                selectedHero.setSelected(true);

                selectedHeroForPreview = dasher;
            }
        });
        revolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedWeapon != null) selectedWeapon.setSelected(false);
                selectedWeapon = revolver;
                selectedWeapon.setSelected(true);
            }
        });
        shotgun.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedWeapon != null) selectedWeapon.setSelected(false);
                selectedWeapon = shotgun;
                selectedWeapon.setSelected(true);
            }
        });
        dualSMGs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedWeapon != null) selectedWeapon.setSelected(false);
                selectedWeapon = dualSMGs;
                selectedWeapon.setSelected(true);
            }
        });
        table.padBottom(250).padLeft(950);
        table.add(errorLabel);
        table.row().pad(30);
        table.add(timeLabel);
        table.row().pad(20);
        table.add(time);
        table.add(playButton);
        backTable.setPosition(backTable.getWidth() * 3, Gdx.graphics.getHeight() - backTable.getHeight() * 2);
        backTable.padLeft(250).padTop(200);
        backTable.add(backButton).pad(50);
        backTable.row().pad(20);
        backTable.add(nameLabel).center();
        stage.addActor(shana);
        stage.addActor(diamond);
        stage.addActor(scarlett);
        stage.addActor(lilith);
        stage.addActor(dasher);
        stage.addActor(revolver);
        stage.addActor(shotgun);
        stage.addActor(dualSMGs);
        stage.addActor(table);
        stage.addActor(backTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.batch.begin();
        Main.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (selectedHeroForPreview != null) {
            Texture bigTex = selectedHeroForPreview.getSelectedHeroTexture();
            Main.batch.draw(bigTex, Gdx.graphics.getWidth() * 5 / 8f, Gdx.graphics.getHeight() / 8f, Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 1.5f);
            nameLabel.setText(selectedHero.getName());
        }

        Main.batch.end();

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    PreGameMenuController.play(selectedHero, selectedWeapon.getFrames()[0], time.getSelected(), errorLabel, selectedWeapon.getName(), playAsGuest);
                } catch (IOException e) {
                    e.printStackTrace(); // or show an error message on screen
                }
            }
        });

        if (backButton.isChecked()) {
            if (playAsGuest) {
                Main.getMain().setScreen(new SignUpMenu(GameAssetsManager.getInstance().getSkin()));
            } else
                Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
        }

        stage.act(delta);
        stage.draw();
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
