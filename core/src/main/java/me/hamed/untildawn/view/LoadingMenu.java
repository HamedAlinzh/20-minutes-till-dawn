package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;

public class LoadingMenu implements Screen {

    private Texture[] blinkTextures;
    private TextureRegion[] blinkFrames;
    private Animation<TextureRegion> blinkAnimation;
    private boolean forceBlinkAndSwitch = false;
    private boolean switching = false;
    private boolean isForcedBlinking = false;
    private boolean isSlidingUp = false;
    private float imageY = Gdx.graphics.getHeight();
    private final float SLIDE_SPEED = 1500f; // pixels per second
    private Texture background;
    private Texture grass = new Texture(Gdx.files.internal("Images/Sprite/T_TitleLeaves.png"));
    private Texture logo = new Texture(Gdx.files.internal("Images/Sprite/T_20Logo.png"));
    private TextureRegion grassFlipped = new TextureRegion(grass);
    private float grassX = -Gdx.graphics.getWidth() / 3f;
    private float flippedGrassX = Gdx.graphics.getWidth();


    private float stateTime;
    private float blinkTimer;
    private boolean isBlinking;

    private final float BLINK_INTERVAL = 3f;  // Blink every 3 seconds
    private final float FRAME_DURATION = 0.1f; // 0.1s per frame

    @Override
    public void show() {
        blinkTextures = new Texture[3];
        blinkTextures[0] = new Texture(Gdx.files.internal("Images/Sprite/T_EyeBlink_0.png")); // open
        blinkTextures[1] = new Texture(Gdx.files.internal("Images/Sprite/T_EyeBlink_1.png")); // half
        blinkTextures[2] = new Texture(Gdx.files.internal("Images/Sprite/T_EyeBlink_2.png")); // closed
        background = new Texture(Gdx.files.internal("ChatGPT Image May 5, 2025, 01_30_18 PM.png"));
        grassFlipped.flip(true, false);

        blinkFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            blinkFrames[i] = new TextureRegion(blinkTextures[i]);
        }

        blinkAnimation = new Animation<>(FRAME_DURATION, blinkFrames);
        blinkAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        stateTime = 0f;
        blinkTimer = 0f;
        isBlinking = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Trigger forced blink on SPACE press
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!isBlinking && !isForcedBlinking && !isSlidingUp) {
                stateTime = 0f;
                isForcedBlinking = true;
            }
        }
        if (grassX < 0) {
            grassX += 1300 * delta;
            if (grassX > 0) grassX = 0; // clamp
        }
        if (flippedGrassX > (2 / 3f) * Gdx.graphics.getWidth()) {
            flippedGrassX -= 1300 * delta;
            if (flippedGrassX < (2 / 3f) * Gdx.graphics.getWidth()) {
                flippedGrassX = (2 / 3f) * Gdx.graphics.getWidth();
            }
        }

        Main.batch.begin();
        Main.batch.draw(grass, grassX , 0, Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight());
        Main.batch.draw(grassFlipped, flippedGrassX, 0, Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight());
        Main.batch.draw(logo, (Gdx.graphics.getWidth() - logo.getWidth() * 2) / 2f, Gdx.graphics.getHeight() - logo.getHeight() * 3, logo.getWidth() * 2, logo.getHeight() * 2);
        if (isForcedBlinking) {
            stateTime += delta;
            TextureRegion frame = blinkAnimation.getKeyFrame(stateTime, false);
            Main.batch.draw(frame, Gdx.graphics.getWidth() * 2 / 7f, Gdx.graphics.getHeight() * 2 / 7f,
                Gdx.graphics.getWidth() * 3 / 7f, Gdx.graphics.getHeight() * 3 / 7f);



            if (blinkAnimation.isAnimationFinished(stateTime)) {
                isForcedBlinking = false;
                isSlidingUp = true; // start sliding after blink
            }

        } else if (isSlidingUp) {
            imageY -= SLIDE_SPEED * delta;

            Main.batch.draw(background, 0, imageY,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



            if (imageY <= 0) {
                isSlidingUp = false;
                switching = true;
            }

        } else if (isBlinking) {
            stateTime += delta;
            TextureRegion frame = blinkAnimation.getKeyFrame(stateTime, false);
            Main.batch.draw(frame, Gdx.graphics.getWidth() * 2 / 7f, Gdx.graphics.getHeight() * 2 / 7f,
                Gdx.graphics.getWidth() * 3 / 7f, Gdx.graphics.getHeight() * 3 / 7f);

            if (blinkAnimation.isAnimationFinished(stateTime)) {
                isBlinking = false;
            }

        } else {
            blinkTimer += delta;

            Main.batch.draw(blinkFrames[0], Gdx.graphics.getWidth() * 2 / 7f, Gdx.graphics.getHeight() * 2 / 7f,
                Gdx.graphics.getWidth() * 3 / 7f, Gdx.graphics.getHeight() * 3 / 7f);

            if (blinkTimer >= BLINK_INTERVAL) {
                isBlinking = true;
                stateTime = 0f;
                blinkTimer = 0f;
            }
        }

        Main.batch.end();

        if (switching) {
            Main.getMain().setScreen(new SignUpMenu(GameAssetsManager.getInstance().getSkin()));
        }
    }



    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        for (Texture texture : blinkTextures) {
            texture.dispose();
        }
    }
}
