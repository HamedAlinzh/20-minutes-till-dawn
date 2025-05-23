package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.Player;
import me.hamed.untildawn.model.User;

import java.awt.*;

public class GameOverMenu implements Screen {
    Texture pausedBg;
    TextureRegion pause;
    ShaderProgram blurShader;
    SpriteBatch batch;
    Stage uiStage = new Stage(new ScreenViewport());
    BitmapFont font;
    float countdownTime;
    int kills;
    boolean playAsGuest;
    int heart;
    User player;
    int height = Gdx.graphics.getHeight();
    int width = Gdx.graphics.getWidth();
    boolean drawn = false;

    public GameOverMenu(Texture pausedBg, float countDown, int kills, boolean playAsGuest, int heart) {
        this.pausedBg = pausedBg;
        this.batch = new SpriteBatch();
        this.playAsGuest = playAsGuest;
        this.heart = heart;
        this.countdownTime = countDown;
        this.kills = kills;
        this.font = new BitmapFont(Gdx.files.internal("Fonts/Font/score.fnt"), false);
        Gdx.input.setCursorCatched(false);
        if (!playAsGuest) {
            player = Main.getMain().getGame().getLoggedInUser();
        }
    }

    @Override
    public void show() {

        blurShader = new ShaderProgram(
            Gdx.files.internal("blur.vert"),
            Gdx.files.internal("blur.frag")
        );

        if (!blurShader.isCompiled()) {
            System.err.println("Shader compile error: " + blurShader.getLog());
        }
        Gdx.input.setInputProcessor(uiStage);

        pause = new TextureRegion(pausedBg);
        pause.flip(false, true);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setShader(blurShader);
        batch.begin();
        batch.draw(pause, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setShader(null);

        if (!playAsGuest) {
            switch (player.getAvatarId()) {
                case 1: {
                    batch.draw(SignUpMenu.avatar1Texture, width / 3f - 200, height / 1.5f, 100, 100);
                    break;
                }
                case 2: {
                    batch.draw(SignUpMenu.avatar2Texture, width / 3f - 200, height / 1.5f, 100, 100);
                    break;
                }
                case 3: {
                    batch.draw(SignUpMenu.avatar3Texture, width / 3f - 200, height / 1.5f, 100, 100);
                    break;
                }
                case 4: {
                    batch.draw(SignUpMenu.avatar4Texture, width / 3f - 200, height / 1.5f - 100, 100, 100);
                    break;
                }
            }

            GlyphLayout layout = new GlyphLayout(font, player.getUsername(), Color.RED, 100, Align.left, false);
            font.draw(batch, layout, width / 3f, height * 6 / 10f);
            drawn = true;
        }

        GlyphLayout gameOver = new GlyphLayout(font, "GAME OVER", Color.RED, 300, Align.center, false);
        font.draw(batch, gameOver, width / 2f - gameOver.width / 2f, height * 9f / 10f);

        if (heart > 0) {
            GlyphLayout win = new GlyphLayout(font, "YOU WIN!", Color.GREEN, 300, Align.left, false);
            font.draw(batch, win, width / 2f - win.width / 2f, height * 7.5f / 10f);
        } else {
            GlyphLayout lose = new GlyphLayout(font, "YOU DIED!", Color.RED, 300, Align.left, false);
            font.draw(batch, lose, width / 2f - lose.width / 2f, height * 7.5f / 10f);
        }

        GlyphLayout timeAlive = new GlyphLayout(font, "Time Alive: " + GameScreen.formatTime(Main.getMain().getGame().getTime() - countdownTime), Color.RED, 100, Align.left, false);
        font.draw(batch, timeAlive, width / 3f, height * 5 / 10f);
        GlyphLayout killsLayout = new GlyphLayout(font, "Kills: " + kills, Color.RED, 100, Align.left, false);
        font.draw(batch, killsLayout, width / 3f, height * 4 / 10f);
        GlyphLayout score = new GlyphLayout(font, "Score: " + (int) (Main.getMain().getGame().getTime() - countdownTime) * kills, Color.RED, 100, Align.left, false);
        font.draw(batch, score, width / 3f, height * 3 / 10f);

        batch.end();

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
