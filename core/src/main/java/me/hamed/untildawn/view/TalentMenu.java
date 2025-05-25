package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;

public class TalentMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table contentTable;
    private ScrollPane scrollPane;
    private TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("Images/Diamond.png")));
    SpriteBatch batch = new SpriteBatch();
    private boolean fromGame = false;
    private GameScreen gameScreen;
    private Texture pauseBg;

    public TalentMenu(Skin skin, boolean fromGame, GameScreen gameScreen, Texture pauseBg) {
        this.fromGame = fromGame;
        this.skin = skin;
        this.gameScreen = gameScreen;
        this.pauseBg = pauseBg;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        root.columnDefaults(0).width(Value.percentWidth(2f/3f, root));
        root.columnDefaults(1).width(Value.percentWidth(1f/3f, root));

        contentTable = new Table(skin);
        contentTable.top().pad(20);
        contentTable.defaults().pad(10).expandX().left();

        Label heroesTitle = new Label("=== Heroes ===", skin, "default");
        heroesTitle.setColor(Color.CYAN);
        heroesTitle.setFontScale(1.2f);
        contentTable.add(heroesTitle).colspan(2).row();

        Label dasher = new Label("Dasher: Has 2 hearts and every 10 seconds turns into a deer", skin);
        dasher.setColor(Color.CYAN);
        contentTable.add(dasher).colspan(2).row();

        Label diamond = new Label("Diamond:                              Starts with 7 hearts", skin);
        diamond.setColor(Color.CYAN);
        contentTable.add(diamond).colspan(2).row();

        Label shana = new Label("Shana:                       You can re-roll the abilities", skin);
        shana.setColor(Color.CYAN);
        contentTable.add(shana).colspan(2).row();

        Label scarlett = new Label("Scarlett:          Every third shot throw a wave of fire", skin);
        scarlett.setColor(Color.CYAN);
        contentTable.add(scarlett).colspan(2).row();

        Label lilith = new Label("Lilith:           When enemies are killed summon a Spirit", skin);
        lilith.setColor(Color.CYAN);
        contentTable.add(lilith).colspan(2).row();

        contentTable.row();
        contentTable.row();

        Label controlsTitle = new Label("=== Controls ===", skin);
        controlsTitle.setColor(Color.ORANGE);
        controlsTitle.setFontScale(1.2f);
        contentTable.add(controlsTitle).colspan(2).row();

        String[][] bindings = {
            {"UP", "W"},
            {"DOWN", "S"},
            {"LEFT", "A"},
            {"RIGHT", "D"},
            {"SHOOT", "SPACE"},
            {"RELOAD", "R"}
        };
        for (String[] b : bindings) {
            Label keyLabel = new Label(b[0] + ":", skin);
            keyLabel.setColor(Color.ORANGE);
            Label bindingLabel = new Label(b[1], skin);
            bindingLabel.setColor(Color.ORANGE);
            contentTable.add(keyLabel);
            contentTable.add(bindingLabel).row();
        }

        contentTable.row();
        contentTable.row();

        Label abilitiesTitle = new Label("=== Abilities ===", skin);
        abilitiesTitle.setColor(Color.LIME);
        abilitiesTitle.setFontScale(1.2f);
        contentTable.add(abilitiesTitle).colspan(2).row();

        String[] abilities = {
            "FIRECREASE:      Increase the fire rate",
            "",
            "VITALITY:        Increase hearts by 1",
            "",
            "PROCREASE:       Increase projectile by 1",
            "",
            "SPEEDY:          Twice the speed for 10 seconds",
            ""
        };
        for (String ab : abilities) {
            Label abLabel = new Label(ab, skin);
            abLabel.setColor(Color.LIME);
            contentTable.add(abLabel).colspan(2).row();
        }

        contentTable.row();
        contentTable.row();

        Label cheatsTitle = new Label("=== Cheat Codes ===", skin);
        cheatsTitle.setColor(Color.PINK);
        cheatsTitle.setFontScale(1.2f);
        contentTable.add(cheatsTitle).colspan(2).row();

        String[][] cheats = {
            {"T", "Reduce the remaining time"},
            {"P", "Increase the projectile"},
            {"L", "Increase the level"},
            {"B", "Spawns the boss"}
        };
        for (String[] cc : cheats) {
            Label codeLabel = new Label(cc[0] + ":", skin);
            codeLabel.setColor(Color.PINK);
            Label descLabel = new Label(cc[1], skin);
            descLabel.setColor(Color.PINK);
            contentTable.add(codeLabel);
            contentTable.add(descLabel).row();
        }

        scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFillParent(true);
        root.add(scrollPane);
        root.add().expand().fill();

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (!fromGame) {
                Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
            } else {
                Main.getMain().setScreen(new PauseMenu(GameAssetsManager.getInstance().getSkin(), gameScreen, pauseBg));
            }
        }
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
    @Override public void dispose() { stage.dispose(); }
}
