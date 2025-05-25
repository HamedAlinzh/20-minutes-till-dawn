package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChooseAbilityScreen implements Screen {

    TextButton ability1;
    TextButton ability2;
    TextButton ability3;
    GameScreen gameScreen;
    ArrayList<String> abilityNames = new ArrayList<>();
    Stage uiStage = new Stage(new ScreenViewport());
    Texture pausedBg;
    SpriteBatch batch = new SpriteBatch();
    TextureRegion pause;
    ShaderProgram blurShader;

    public ChooseAbilityScreen(Skin skin, GameScreen gameScreen, Texture pausedBg) {

        this.gameScreen = gameScreen;

        List<Abilities> allAbilities = new ArrayList<>(Arrays.asList(Abilities.values()));
        Collections.shuffle(allAbilities); // Randomize the list

        // Pick the first 3 unique abilities
        for (int i = 0; i < 3; i++) {
            abilityNames.add(allAbilities.get(i).getName());
        }

        // Create buttons with selected abilities
        ability1 = new TextButton(abilityNames.get(0), skin);
        ability2 = new TextButton(abilityNames.get(1), skin);
        ability3 = new TextButton(abilityNames.get(2), skin);
        System.out.println("Ability 1: " + abilityNames.get(0));
        System.out.println("Ability 2: " + abilityNames.get(1));
        System.out.println("Ability 3: " + abilityNames.get(2));
        this.pausedBg = pausedBg;
        Gdx.input.setCursorCatched(false);
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
        Table levelUpTable = new Table();

        pause = new TextureRegion(pausedBg);
        pause.flip(false, true);
        levelUpTable.setFillParent(true);
        levelUpTable.center();

        levelUpTable.add(ability1).pad(10);
        levelUpTable.row();
        levelUpTable.add(ability2).pad(10);
        levelUpTable.row();
        levelUpTable.add(ability3).pad(10);

        uiStage.addActor(levelUpTable);



    }

    public void closeLevelUpMenu() {

        uiStage.clear();
        Main.getMain().setScreen(gameScreen);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setShader(blurShader);
        batch.begin();
        batch.draw(pause, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        batch.setShader(null);


        if (ability1.isChecked()) {
            ability(gameScreen, ability1.getText().toString());

            gameScreen.showLevelUpUI = false;
            closeLevelUpMenu();
        }
        if (ability2.isChecked()) {
            ability(gameScreen, ability2.getText().toString());
            gameScreen.showLevelUpUI = false;
            closeLevelUpMenu();
        }
        if (ability3.isChecked()) {
            ability(gameScreen, ability3.getText().toString());
            gameScreen.showLevelUpUI = false;
            closeLevelUpMenu();
        }

        uiStage.act(delta);
        uiStage.draw();
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

    private void ability(GameScreen gameScreen, String ability) {
        switch (ability) {
            case "Vitality": {
                gameScreen.heart.setMaxHealth(gameScreen.heart.getMaxHealth() + 1);
                gameScreen.heart.setHealth(gameScreen.heart.getHealth() + 1);
                gameScreen.vitality++;
                break;
            }
            case "Speedy": {
                if (!gameScreen.speedAbility) {
                    gameScreen.speed = gameScreen.speed * 2;
                    gameScreen.speedAbility = true;
                    gameScreen.speedy++;
                }
                break;
            }
            case "Amocrease": {
                gameScreen.weapon.setMaxAmmo(gameScreen.weapon.getMaxAmmo() + 2);
                gameScreen.amocrease++;
                break;
            }
            case "Procrease": {
                gameScreen.weapon.setProjectile(gameScreen.weapon.getProjectile() + 1);
                gameScreen.procrease++;
                break;
            }
            case "Damager": {
                gameScreen.weapon.setDamage(gameScreen.weapon.getDamage() * 1.4f);
                gameScreen.damager++;
                break;
            }
            case "Firecrease": {
                gameScreen.SHOOT_WAIT_TIME = gameScreen.SHOOT_WAIT_TIME * 2 / 3f;
                gameScreen.firecrease++;
                break;
            }
            case "Reload": {
                gameScreen.weapon.setReloadTime(gameScreen.weapon.getReloadTime() * 0.75f);
                gameScreen.reload++;
                break;
            }
        }
    }
}
