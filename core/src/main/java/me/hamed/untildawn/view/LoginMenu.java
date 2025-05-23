package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.controller.LoginMenuController;
import me.hamed.untildawn.controller.SignUpMenuController;
import me.hamed.untildawn.model.GameAssetsManager;

import java.io.IOException;

public class LoginMenu implements Screen {
    private Stage stage;
    private static Table table;
    private static TextField field;
    private static TextField passField;
    private static Label label;
    private static TextButton button;
    private static TextButton forgetPassButton;
    private BitmapFont font;
    private final Texture background;
    private final Texture abby;
    private int buttonCheck;
    private TextButton backButton;
    private Label errorLabel;
    private CheckBox checkBox;

    public LoginMenu(Skin skin) {
        table = new Table();
        field = new TextField("username", skin);
        label = new Label("Login Up Menu", skin);
        button = new TextButton("Enter Pregame Menu", skin);
        forgetPassButton = new TextButton("Forget Password", skin);
        field = new TextField("username", skin);
        passField = new TextField("password", skin);
        font = new BitmapFont(Gdx.files.internal("Fonts/Font/score.fnt"));
        background = new Texture("ChatGPT Image May 5, 2025, 01_30_18 PM.png");
        abby = new Texture("Images/Sprite/T_Abby_Portrait.png");
        errorLabel = new Label("", skin);
        checkBox = new CheckBox("", skin);
        backButton = new TextButton("Back", skin);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.left().padLeft(Gdx.graphics.getWidth() / 8);
        table.padTop(Gdx.graphics.getHeight() / 8);
        table.add(label);
        table.row().pad(40);
        table.add(field).width(300);
        table.row().pad(40);
        table.add(passField).width(300);
        table.row().pad(40);
        table.add(button);
        table.row().pad(40);
        table.add(errorLabel).padLeft(40);
        table.row().pad(40);
        table.add(forgetPassButton);
        table.add(checkBox);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(false);

        field.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    field.setText("");
                    cleared = true;
                }
            }
        });

        passField.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    passField.setText("");
                    cleared = true;
                }
            }
        });

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    LoginMenuController.login(passField, field, errorLabel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        forgetPassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    LoginMenuController.forgetPassword(forgetPassButton, passField, field, errorLabel, checkBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        backButton.setPosition(40, Gdx.graphics.getHeight() - 1.2f * backButton.getHeight());
        stage.addActor(backButton);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Main.batch.begin();
        Main.batch.draw(background, 0, 0, width, height);
        Main.batch.draw(abby, width / 1.75f, height / 8f, width / 3f, height / 1.5f);
        Main.batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Main.getMain().setScreen(new SignUpMenu(GameAssetsManager.getInstance().getSkin()));
        }
        if (backButton.isChecked()) {
            Main.getMain().setScreen(new SignUpMenu(GameAssetsManager.getInstance().getSkin()));
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
