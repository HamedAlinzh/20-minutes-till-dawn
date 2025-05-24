package me.hamed.untildawn.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Scoreboard Screen: Displays top 10 players sorted by selected field.
 * Loads data from a JSON file with fields: username, score, kills, timeAlive (MM:SS), avatar.
 */
public class Scoreboard implements Screen {
    private static final String USER_FILE = "/home/hamed/Documents/20-Minutes-Till-Dawn/assets/jsonFiles/users.json";
    SpriteBatch batch = new SpriteBatch();
    private Stage stage;
    private Skin skin;
    private Table table;
    private ScrollPane scrollPane;
    private SelectBox<String> sortBox;
    private TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("Images/Purple.png")));

    private List<PlayerData> players = new ArrayList<>();
    private TextureRegion[] avatarRegions = new TextureRegion[4];
    private String currentUser;

    public Scoreboard(Skin skin) {
        this.skin = skin;
        User user = Main.getMain().getGame().getLoggedInUser();
        this.currentUser = user != null ? user.getUsername() : "";
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        for (int i = 0; i < 4; i++) {
            Texture tex = new Texture(Gdx.files.internal((i) + ".png"));
            avatarRegions[i] = new TextureRegion(tex);
        }

        loadPlayerData();

        table = new Table(skin);
        table.top().pad(10);
        table.defaults().pad(5).growX();

        // Header row with avatar
        table.add(new Label("Avatar", skin));
        table.add(new Label("Username", skin));
        table.add(new Label("Kills", skin));
        table.add(new Label("Time Alive", skin));
        table.add(new Label("Score", skin));
        table.row();

        sortBox = new SelectBox<>(skin);
        sortBox.setItems("Score", "Kills", "Time Alive");
        sortBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshTable(sortBox.getSelected());
            }
        });

        Table container = new Table(skin);
        container.top().pad(10);
        container.add(new Label("Sort by:", skin));
        container.add(sortBox).padLeft(10);
        container.row();
        container.add(new ScrollPane(table, skin)).expand().fill();

        scrollPane = new ScrollPane(container, skin);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);

        refreshTable(sortBox.getItems().get(0));
    }

    private void loadPlayerData() {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.absolute(USER_FILE));
        players.clear();
        for (JsonValue entry = root.child; entry != null; entry = entry.next) {
            PlayerData pd = new PlayerData();
            pd.username  = entry.getString("username", "");
            pd.kills     = entry.getInt("kills", 0);
            pd.score     = entry.getInt("score", 0);
            pd.timeAlive = entry.getString("timeAlive", "00:00");
            pd.avatar    = entry.getInt("avatar", 0);
            players.add(pd);
        }
    }

    private void refreshTable(String sortField) {
        table.clearChildren();
        table.add(new Label("Avatar", skin)).padLeft(180);
        table.add(new Label("Username", skin));
        table.add(new Label("Kills", skin));
        table.add(new Label("Time Alive", skin));
        table.add(new Label("Score", skin));
        table.row();

        Comparator<PlayerData> comparator;
        switch (sortField) {
            case "Kills":      comparator = Comparator.comparingInt(pd -> pd.kills); break;
            case "Time Alive": comparator = Comparator.comparingInt(pd -> parseTime(pd.timeAlive)); break;
            case "Score":
            default:            comparator = Comparator.comparingInt(pd -> pd.score); break;
        }

        List<PlayerData> sorted = players.stream()
            .sorted(comparator.reversed())
            .limit(10)
            .collect(Collectors.toList());

        for (int i = 0; i < sorted.size(); i++) {
            PlayerData pd = sorted.get(i);
            boolean isCurrent = pd.username.equals(currentUser);
            Color rowColor;
            switch (i) {
                case 0: rowColor = Color.GOLD; break;
                case 1: rowColor = Color.GRAY; break;
                case 2: rowColor = new Color(205/255f, 127/255f, 50/255f, 1); break;
                default: rowColor = isCurrent ? Color.RED : Color.WHITE; break;
            }

            TextureRegion avatar = avatarRegions[MathUtils.clamp(pd.avatar, 0, 3)];
            Image img = new Image(avatar);
            table.add(img)
                .size(120, 120)
                .pad(5);

            Label nameLabel = new Label(pd.username, skin);
            nameLabel.setColor(rowColor);
            table.add(nameLabel);

            Label killsLabel = new Label(String.valueOf(pd.kills), skin);
            killsLabel.setColor(rowColor);
            table.add(killsLabel);

            Label timeLabel = new Label(pd.timeAlive, skin);
            timeLabel.setColor(rowColor);
            table.add(timeLabel);

            Label scoreLabel = new Label(String.valueOf(pd.score), skin);
            scoreLabel.setColor(rowColor);
            table.add(scoreLabel);

            table.row();
        }
    }

    private int parseTime(String mmss) {
        String[] parts = mmss.split(":");
        int m = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
        int s = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        return m * 60 + s;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
        }
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
    @Override public void dispose() { stage.dispose(); }

    private static class PlayerData {
        String username;
        int kills;
        int score;
        String timeAlive;  // MM:SS format
        int avatar;
    }
}
