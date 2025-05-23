package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.controller.LoginMenuController;
import me.hamed.untildawn.controller.SignUpMenuController;
import me.hamed.untildawn.model.GameAssetsManager;

import java.io.IOException;

public class SignUpMenu implements Screen {
    private int screenWidth, screenHeight;
    private Stage stage;
    private Table table;
    private TextField field;
    private TextField passField;
    private TextField questionField;

    private  Label label;
    private  TextButton button;
    private  TextButton loginButton;
    private  TextButton guestButton;
    private  Label avatarLabel;
    int buttonCheck = 4;
    private BitmapFont font;
    private final Texture background;
    private final Texture scarlett;
    private Label errorLabel;
    private int selectedAvatar = -1; // 0 = avatar1, 1 = avatar2, etc.
    public static Texture avatar1Texture = new Texture(Gdx.files.internal("0.png"));
    public static Texture avatar2Texture = new Texture(Gdx.files.internal("1.png"));
    public static Texture avatar3Texture = new Texture(Gdx.files.internal("2.png"));
    public static Texture avatar4Texture = new Texture(Gdx.files.internal("3.png"));

    public static Image avatar1 = new Image(avatar1Texture);
    public static Image avatar2 = new Image(avatar2Texture);
    public static Image avatar3 = new Image(avatar3Texture);
    public static Image avatar4 = new Image(avatar4Texture);

    public SignUpMenu(Skin skin) {
        table = new Table();
        field = new TextField("username", skin);
        questionField = new TextField("What is favorite breakfast fish?", skin);
        label = new Label("Sign Up Menu", skin);
        avatarLabel = new Label("Choose Your Avatar", skin);
        loginButton = new TextButton("Login", skin);
        guestButton = new TextButton("Play As A Guest", skin);
        button = new TextButton("Enter Main Menu", skin);
        passField = new TextField("password", skin);
        font = new BitmapFont(Gdx.files.internal("Fonts/Font/score.fnt"));
        background = new Texture("ChatGPT Image May 5, 2025, 01_30_18 PM.png");
        scarlett = new Texture("Images/Sprite/T_Scarlett_Portrait.png");
        errorLabel = new Label("", skin);
    }

    @Override
    public void show() {
        if (Main.backgroundMusic == null) {
            Main.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/AudioClip/Pretty Dungeon LOOP.wav"));
            Main.backgroundMusic.setLooping(true);
            Main.backgroundMusic.setVolume(Main.getSoundVolume());
            Main.backgroundMusic.play();
        }
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.right().padRight(Gdx.graphics.getWidth() / 8f).padBottom(Gdx.graphics.getHeight() / 15f);
        table.add(label);
        table.row().pad(20);
        table.add(field).width(300);
        table.row().pad(20);
        table.add(passField).width(300);
        table.row().pad(20);
        questionField.setAlignment(Align.center);
        table.add(questionField).width(500);
        table.row().pad(20);
        table.add(button);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(false);
        table.row().pad(10);
        table.add(errorLabel).colspan(2);
        // 🔹 Create a new table for top-right buttons
        Table topRightTable = new Table();
        topRightTable.top().right().padTop(20).padRight(20); // Align to top-right
        topRightTable.setFillParent(true); // Let it fill the screen
        topRightTable.add(loginButton).pad(20);
        topRightTable.add(guestButton).pad(20);
        Table avatarTable = new Table();
        avatarTable.right().bottom().padBottom(Gdx.graphics.getHeight() / 9f).padRight(260);
        avatarTable.setFillParent(true);

        avatarTable.add(avatarLabel).colspan(4).center();
        avatarTable.row().pad(60);

        avatarTable.add(avatar1).size(100, 100).pad(10);
        avatarTable.add(avatar2).size(100, 100).pad(10);
        avatarTable.add(avatar3).size(100, 100).pad(10);
        avatarTable.add(avatar4).size(100, 100).pad(10);
        stage.addActor(avatarTable);

        avatar1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 0;
                highlightSelection(avatar1, avatar2, avatar3, avatar4);
            }
        });

        avatar2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 1;
                highlightSelection(avatar2, avatar1, avatar3, avatar4);
            }
        });

        avatar3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 2;
                highlightSelection(avatar3, avatar1, avatar2, avatar4);
                            }
        });

        avatar4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatar = 3;
                highlightSelection(avatar4, avatar2, avatar3, avatar1);
            }
        });

        stage.addActor(topRightTable); // 🔹 Add top-right buttons first// Optional: where this appears depends on alignment
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
        Main.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.batch.draw(scarlett, width / 8f, height / 8f, width / 3f, height / 1.5f);
        Main.batch.end();

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

        passField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused && passField.getText().isEmpty()) {
                    passField.setText("password");
                }
            }
        });

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

        field.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused && field.getText().isEmpty()) {
                    field.setText("username");
                }
            }
        });

        questionField.addListener(new ClickListener() {
            boolean cleared = false;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!cleared) {
                    questionField.setText("");
                    cleared = true;
                }
            }
        });

        questionField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused && questionField.getText().isEmpty()) {
                    questionField.setText("What is favorite breakfast fish?");
                }
            }
        });

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    SignUpMenuController.signUp(button, passField, field, font, errorLabel, selectedAvatar);
                } catch (IOException e) {
                    e.printStackTrace(); // or show an error message on screen
                }
            }
        });
        if (loginButton.isChecked()) {
            Main.getMain().setScreen(new LoginMenu(GameAssetsManager.getInstance().getSkin()));
            return;
        }
        if (guestButton.isChecked()) {
            Main.getMain().setScreen(new PreGameMenu(GameAssetsManager.getInstance().getSkin(), true));
            return;
        }

        stage.act(delta); // Recommended for animation/input updates
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
        font.dispose();  // Dispose the font to free resources
    }

    @Override
    public void dispose() {

    }

    public static void highlightSelection(Image selected, Image... others) {
        selected.setColor(Color.YELLOW); // highlight selected
        for (Image other : others) {
            other.setColor(Color.WHITE); // reset others
        }
    }

}
