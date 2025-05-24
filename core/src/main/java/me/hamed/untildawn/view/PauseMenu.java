package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.GameSave;

public class PauseMenu implements Screen {

    Stage uiStage = new Stage(new ScreenViewport());

    TextButton resumeButton;
    TextButton settingsButton;
    TextButton giveUp;
    Texture pausedBg;
    SpriteBatch batch = new SpriteBatch();
    TextureRegion pause;
    ShaderProgram blurShader;
    GameScreen gameScreen;
    Table table;
    boolean gaveUp = false;
    boolean remove = false;
    private float stateTime;
    TextureRegion texture;
    Animation deathAnimation = new Animation(0.35f, GameAssetsManager.getInstance().getIdleFrames("Death"));

    public PauseMenu(Skin skin, GameScreen gameScreen, Texture pausedBg) {

        table = new Table(skin);
        this.gameScreen = gameScreen;
        this.pausedBg = pausedBg;
        Gdx.input.setCursorCatched(false);
        this.resumeButton = new TextButton("Resume", skin);
        this.settingsButton = new TextButton("Settings", skin);
        this.giveUp = new TextButton("Give Up", skin);
    }

    @Override
    public void show() {


        Gdx.input.setInputProcessor(uiStage);

        blurShader = new ShaderProgram(
            Gdx.files.internal("blur.vert"),
            Gdx.files.internal("blur.frag")
        );

        if (!blurShader.isCompiled()) {
            System.err.println("Shader compile error: " + blurShader.getLog());
        }

        pause = new TextureRegion(pausedBg);
        pause.flip(false, true);

        table.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        table.add(resumeButton);
        table.row().pad(20);
        table.add(settingsButton);
        table.row().pad(20);
        table.add(giveUp);
        uiStage.addActor(table);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (resumeButton.isChecked()) {
            Main.getMain().setScreen(gameScreen);
        }
        if (settingsButton.isChecked()) {
            Main.getMain().setScreen(new SettingMenu(GameAssetsManager.getInstance().getSkin(), true, gameScreen));
        }
        if (!gaveUp && giveUp.isChecked()) {
            gaveUp = true;
        }
        if (gaveUp) {
            stateTime += delta;
        }
        if (gaveUp) {
            texture = (TextureRegion) deathAnimation.getKeyFrame(stateTime);
            update(delta);
        }

        batch.setShader(blurShader);
        batch.begin();
        if (!gaveUp) {
            batch.draw(pause, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else {
            if (remove) {
                Main.getMain().setScreen(new GameOverMenu(pause.getTexture(), gameScreen.countdownTime, gameScreen.kills, gameScreen.playAsGuest, gameScreen.heart.getHealth(), gaveUp));
            }
            batch.setShader(null);
            batch.draw(pause, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(texture, Gdx.graphics.getWidth() / 2f - texture.getRegionWidth() * 3 / 4f + 20, Gdx.graphics.getHeight() / 2f - texture.getRegionHeight() * 3 /4f + 20);
        }



        batch.end();

        if (!gaveUp) {
            uiStage.act(delta);
            uiStage.draw();
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

    public void update(float delta) {
        stateTime += delta;
        if (deathAnimation.isAnimationFinished(stateTime)) {
            remove = true;
        }
    }
}
