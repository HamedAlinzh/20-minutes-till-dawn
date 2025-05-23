package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.controller.ProfileMenuController;
import me.hamed.untildawn.controller.SignUpMenuController;
import me.hamed.untildawn.model.GameAssetsManager;

import java.io.IOException;

public class ProfileMenu implements Screen {
    private Stage stage;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label confirmPasswordLabel;
    private Label errorLabel;
    private TextField username;
    private TextField password;
    private TextField confirmPassword;
    private Label avatarLabel;
    private Button confirmUsername;
    private final Texture background;
    private int selectedAvatar = -1;
    private TextButton deleteAccountButton;
    private TextButton back;
    private final Texture raven;

    Texture avatar1Texture = new Texture(Gdx.files.internal("0.png"));
    Texture avatar2Texture = new Texture(Gdx.files.internal("1.png"));
    Texture avatar3Texture = new Texture(Gdx.files.internal("2.png"));
    Texture avatar4Texture = new Texture(Gdx.files.internal("3.png"));

    Image avatar1 = new Image(avatar1Texture);
    Image avatar2 = new Image(avatar2Texture);
    Image avatar3 = new Image(avatar3Texture);
    Image avatar4 = new Image(avatar4Texture);

    public ProfileMenu(Skin skin) {
        passwordLabel = new Label("Change Password", skin);
        usernameLabel = new Label("Change Username", skin);
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        avatarLabel = new Label("Change Avatar", skin);
        confirmPasswordLabel = new Label("Repeat Password", skin);
        username = new TextField("New Username", skin);
        password = new TextField("New Password", skin);
        confirmPassword = new TextField("Repeat New Password", skin);
        confirmUsername = new Button(skin);
        deleteAccountButton = new TextButton("Delete Account", skin);
        back = new TextButton("Back", skin);
        deleteAccountButton.getLabel().setFontScale(0.75f);
        deleteAccountButton.setWidth(350);
        raven = new Texture("Images/Sprite/T_Raven_Portrait.png");
        background = new Texture("ChatGPT Image May 5, 2025, 01_30_18 PM.png");
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.left().padLeft(Gdx.graphics.getWidth() / 5f).padBottom(Gdx.graphics.getHeight() / 5f);
        table.setFillParent(true);
        table.add(usernameLabel);
        table.row().pad(10);
        table.add(username).width(300);
        table.row().pad(10);
        table.add(passwordLabel);
        table.row().pad(10);
        table.add(password).width(300);
        table.row().pad(10);
        table.add(confirmPasswordLabel);
        table.row().pad(10);
        table.add(confirmPassword).width(300);
        table.add(confirmUsername);
        table.row().pad(10);
        table.add(errorLabel);
        back.setX(Gdx.graphics.getWidth() / 40f);
        back.setY(Gdx.graphics.getHeight() * 17 / 20f + 20);
        deleteAccountButton.setX(Gdx.graphics.getWidth() / 30f + back.getWidth());
        deleteAccountButton.setY(Gdx.graphics.getHeight() * 17 / 20f + 20);
        Table avatarTable = new Table();
        avatarTable.left().bottom().padBottom(Gdx.graphics.getHeight() / 9f).padLeft(Gdx.graphics.getWidth() / 6.5f);
        avatarTable.setFillParent(true);

        avatarTable.add(avatarLabel).colspan(4).center();
        avatarTable.row().pad(60);

        avatarTable.add(avatar1).size(100, 100).pad(10);
        avatarTable.add(avatar2).size(100, 100).pad(10);
        avatarTable.add(avatar3).size(100, 100).pad(10);
        avatarTable.add(avatar4).size(100, 100).pad(10);

        avatar1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 0;
                SignUpMenu.highlightSelection(avatar1, avatar2, avatar3, avatar4);
            }
        });

        avatar2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 1;
                SignUpMenu.highlightSelection(avatar2, avatar1, avatar3, avatar4);
            }
        });

        avatar3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 2;
                SignUpMenu.highlightSelection(avatar3, avatar1, avatar2, avatar4);
            }
        });

        avatar4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 3;
                SignUpMenu.highlightSelection(avatar4, avatar2, avatar3, avatar1);
            }
        });
        stage.addActor(deleteAccountButton);
        stage.addActor(back);
        stage.addActor(avatarTable);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Main.batch.begin();
        Main.batch.draw(background, 0, 0, width, height);
        Main.batch.draw(raven, width / 1.75f, height / 8f, width / 3f, height / 1.5f);
        Main.batch.end();

        password.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    password.setText("");
                    cleared = true;
                }
            }
        });

        username.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    username.setText("");
                    cleared = true;
                }
            }
        });

        confirmPassword.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    confirmPassword.setText("");
                    cleared = true;
                }
            }
        });

        confirmUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ProfileMenuController.changeProfile(username.getText(),password.getText(), confirmPassword.getText(), errorLabel, selectedAvatar);
                } catch (IOException e) {
                    errorLabel.setText("Failed to update username!");
                    errorLabel.setVisible(true);
                    e.printStackTrace();
                }
            }
        });

        if (deleteAccountButton.isChecked()) {
            ProfileMenuController.deleteAccount();
        }

        if (back.isChecked()) {
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
