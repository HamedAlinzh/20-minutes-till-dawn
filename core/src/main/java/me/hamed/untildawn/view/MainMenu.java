package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.controller.MainMenuController;
import me.hamed.untildawn.controller.PreGameMenuController;
import me.hamed.untildawn.model.Game;
import me.hamed.untildawn.model.GameAssetsManager;

public class MainMenu implements Screen {
    private Stage stage;
    private static Label label;
    private static Label score;
    private static Label username;
    private static TextButton pregame;
    private static TextButton setting;
    private static TextButton scoreboard;
    private static TextButton profile;
    private static TextButton talent;
    private static TextButton logout;
    private static TextButton back;
    private static TextButton save;
    private final Texture luna;
    private final Texture background;
    Image avatar = Main.getMain().getGame().getLoggedInUser().getAvatar();

    public MainMenu(Skin skin) {
        label = new Label((Main.getMain().getLanguage().equals("english")) ? Texts.SCORE_LABEL.getEnglish() : Texts.SCORE_LABEL.getFrench(), skin);
        score = new Label((Main.getMain().getLanguage().equals("english")) ? Texts.SCORE_LABEL.getEnglish() : Texts.SCORE_LABEL.getFrench(), skin);
        username = new Label((Main.getMain().getLanguage().equals("english")) ? Texts.USERNAME.getEnglish() : Texts.USERNAME.getFrench(), skin);
        username = new Label(Main.getMain().getGame().getLoggedInUser().getUsername(), skin);
        username.setWidth(100);
        username.setHeight(100);
        score.setText(Main.getMain().getGame().getLoggedInUser().getScore());
        pregame = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.PREGAME.getEnglish() : Texts.PREGAME.getFrench(), skin);
        setting = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.SETTING.getEnglish() : Texts.SETTING.getFrench(), skin);
        scoreboard = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.SCOREBOARD.getEnglish() : Texts.SCOREBOARD.getFrench(), skin);
        profile = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.PROFILE.getEnglish() : Texts.PROFILE.getFrench(), skin);
        talent = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.TALENT.getEnglish() : Texts.TALENT.getFrench(), skin);
        logout = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.LOGOUT.getEnglish() : Texts.LOGOUT.getFrench(), skin);
        back = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.BACK.getEnglish() : Texts.BACK.getFrench(), skin);
        save = new TextButton((Main.getMain().getLanguage().equals("english")) ? Texts.SAVE.getEnglish() : Texts.SAVE.getFrench(), skin);
        luna = new Texture("Images/Sprite/T_Luna_Portrait.png");
        background = new Texture("ChatGPT Image May 5, 2025, 01_30_18 PM.png");
    }
    @Override
    public void show() {
        Table table1 = new Table();
        Table table2 = new Table();
        Table userinfo = new Table();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table1.setFillParent(true);
        table2.setFillParent(true);
        userinfo.setSize(400, 300);
        userinfo.setPosition(Gdx.graphics.getWidth() * 5 / 6f, Gdx.graphics.getHeight() / 3.5f);
        userinfo.right().padBottom(Gdx.graphics.getWidth() / 3.5f).padRight(Gdx.graphics.getWidth() / 10f);
        userinfo.add(avatar).size(100, 100).pad(30);
        username.setColor(Color.RED);
        userinfo.add(username).pad(10);
        userinfo.row();
        label.setColor(Color.RED);
        userinfo.add(label).pad(10);
        userinfo.add(score).pad(10);
        userinfo.setTransform(true);
        userinfo.setOrigin(Align.center);
        userinfo.setScale(1.5f);


        table1.right().padRight(Gdx.graphics.getWidth() / 4f).padTop(Gdx.graphics.getHeight() / 4f);
        table2.right().padRight(Gdx.graphics.getWidth() / 20f).padTop(Gdx.graphics.getHeight() / 4f);
        table1.add(pregame).size(300, 120);
        table1.row().pad(10);
        table1.add(save).size(300, 120);
        table1.row().pad(10);
        table2.add(setting).size(300, 120);
        table2.row().pad(10);
        table1.add(scoreboard).size(300, 120);
        table1.row().pad(10);
        table2.add(profile).size(300, 120);
        table2.row().pad(10);
        table2.add(talent).size(300, 120);
        table2.row().pad(10);

        logout.setPosition(Gdx.graphics.getWidth() * 4 / 7f, Gdx.graphics.getHeight() * 5 / 7f);
        logout.setSize(250 , 100);
        back.setPosition(Gdx.graphics.getWidth() * 4 / 7f, Gdx.graphics.getHeight() * 5 / 7f + logout.getHeight() * 1.1f);
        back.setSize(250 , 100);
        stage.addActor(back);
        stage.addActor(logout);
        stage.addActor(table1);
        stage.addActor(table2);
        stage.addActor(userinfo);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.batch.begin();
        stage.draw();
        Main.batch.end();

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Main.batch.begin();
        Main.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.batch.draw(luna, width / 8f, height / 8f, width / 3f, height / 1.5f);
        Main.batch.end();

        if (logout.isChecked()) {
            MainMenuController.logout();
        }
        if (setting.isChecked()) {
            MainMenuController.goToSetting();
        }
        if (scoreboard.isChecked()) {
            MainMenuController.scoreboard();
        }
        if (profile.isChecked()) {
            MainMenuController.goToProfileMenu();
        }
        if (talent.isChecked()) {
            MainMenuController.talenMenu();
        }
        if (pregame.isChecked()) {
            MainMenuController.pregameMenu();
        }
        if (save.isChecked()) {
            new PreGameMenu(GameAssetsManager.getInstance().getSkin(), false);
            Main.getMain().setScreen(new GameScreen(true, false));
        }
        if (back.isChecked()) {
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

    public static Label getLabel() {
        return label;
    }

    public Stage getStage() {
        return stage;
    }

}
