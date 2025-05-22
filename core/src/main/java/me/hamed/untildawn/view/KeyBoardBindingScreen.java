package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.KeyBindings;

public class KeyBoardBindingScreen implements Screen {

    private Stage stage;
    private static TextField up;
    private TextField down;
    private TextField left;
    private TextField right;
    private Label upLabel;
    private Label downLabel;
    private Label leftLabel;
    private Label rightLabel;
    private TextField reload;
    private Label reloadLabel;
    private TextField shoot;
    private Label shootLabel;
    private TextButton back;

    public KeyBoardBindingScreen(Skin skin) {
        up = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.UP)), skin);
        down = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.DOWN)), skin);
        left = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.LEFT)), skin);
        right = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.RIGHT)), skin);
        reload = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.RELOAD)), skin);
        shoot = new TextField(Input.Keys.toString(KeyBindings.get(KeyBindings.SHOOT)), skin);
        upLabel = new Label("Up", skin);
        downLabel = new Label("Down", skin);
        leftLabel = new Label("Left", skin);
        rightLabel = new Label("Right", skin);
        reloadLabel = new Label("Reload", skin);
        shootLabel = new Label("Shoot", skin);
        back = new TextButton("Back", skin);
        TextField.TextFieldFilter oneCharFilter = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                // Only allow if no character exists yet
                return textField.getText().length() == 0;
            }
        };
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.add(upLabel);
        table.add(up);
        table.row().pad(30);
        table.add(downLabel);
        table.add(down);
        table.row().pad(30);
        table.add(leftLabel);
        table.add(left);
        table.row().pad(30);
        table.add(rightLabel);
        table.add(right);
        table.row().pad(30);
        table.add(reloadLabel);
        table.add(reload);
        table.row().pad(30);
        table.add(shootLabel);
        table.add(shoot);
        table.row().pad(30);
        table.add(back);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        if (back.isChecked()) {
            try {
                String text = shoot.getText().toUpperCase();
                if (text.equals("MOUSE") || text.equals("LEFT")) {
                    KeyBindings.set(KeyBindings.SHOOT, Input.Buttons.LEFT, true);
                } else {
                    KeyBindings.set(KeyBindings.SHOOT, Input.Keys.valueOf(text), false);
                }
                KeyBindings.set(KeyBindings.UP, Input.Keys.valueOf(up.getText().toUpperCase()), false);
                KeyBindings.set(KeyBindings.DOWN, Input.Keys.valueOf(down.getText().toUpperCase()), false);
                KeyBindings.set(KeyBindings.LEFT, Input.Keys.valueOf(left.getText().toUpperCase()), false);
                KeyBindings.set(KeyBindings.RIGHT, Input.Keys.valueOf(right.getText().toUpperCase()), false);
                KeyBindings.set(KeyBindings.RELOAD, Input.Keys.valueOf(reload.getText().toUpperCase()), false);

                KeyBindings.saveBindings();
                Main.getMain().setScreen(new SettingMenu(GameAssetsManager.getInstance().getSkin()));
            } catch (IllegalArgumentException e) {
                // Show an error or fallback
                System.out.println("Invalid key entered.");
            }
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
