package me.hamed.untildawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.*;
import me.hamed.untildawn.model.GameSaves.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
    public float SHOOT_WAIT_TIME = 0.25f;
    private final float RELOAD_BAR_X = 996.5f;
    private final float RELOAD_BAR_Y = 582f;

    float worldX = 0f;
    float worldY = 0f;
    int tileSize = 40;
    private Texture tileTexture = GameAssetsManager.getInstance().getTileTexture();


    private Game game = Main.getMain().getGame();
    private float countdownTime; // in seconds
    private boolean timeUp = false;
    private BitmapFont timerFont;
    private float gameTime;


    public Player player;
    public Heart heart;
    private Rectangle playerRect = new Rectangle();
    private float invincibility = 0f;
    private boolean invincible = false;
    private SpriteBatch batch;
    private boolean facingLeft = false;
    private int level = 1;
    private int currentXp = 0;
    private int xpToNextLevel = 20;
    private int kills = 0;
    boolean showLevelUpUI = false;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    int barX = 20;
    int barY = Gdx.graphics.getHeight() - 60;
    int barWidth = Gdx.graphics.getWidth() - 40;
    int barHeight = 20;



    public Weapon weapon;
    OrthographicCamera camera;


    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> damage;
    private float animationTimer = 0f;

    private boolean playingDamageAnimation = false;
    private float stateTime = 0f;

    public float speed = 200f;
    private float speedAbilityTimer = 0;
    public boolean speedAbility = false;

    private boolean isRunning;

    private float playerDrawX, playerDrawY;
    private boolean reloadingSound = false;


    private float enemySpawnTimer = 0f;
    private float spawnInterval;
    private float eyeBatSpawnTimer = 0f;
    private float eyeBatSpawnInterval = 0f;
    private ArrayList<BrainMonster> brainMonsters = new ArrayList<>();
    private ArrayList<EyeBat> eyeBats = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<XpDrop> xpDrops = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<MonsterDeath> monsterDeaths = new ArrayList<>();



    private ArrayList<Bullet> bullets = new ArrayList<>();
    float weaponTipOffsetX;
    float weaponTipOffsetY;
    float shootTimer = 0;
    int firedBullets = 0;
    float reloadTimer = 0f;
    boolean reloading = false;
    Texture reloadLine = new Texture(Gdx.files.internal("Images/Sprite/T_ReloadBar_0.png"));
    Texture reloadBar = new Texture(Gdx.files.internal("Images/Sprite/T_ReloadBar_1.png"));
    float reloadBarX = RELOAD_BAR_X;
    float reloadBarY = RELOAD_BAR_Y;
    boolean autoAim = false;


    private boolean playAsGuest;
    private InputProcessor gameInputProcessor;


    ShaderProgram grayscaleShader;




    public GameScreen(boolean loadSavedGame, boolean playAsGuest) {
        if (loadSavedGame) {
            loadGame();
        } else {
            startNewGame();
            weapon = player.getCurrentWeapon();
        }
        this.playAsGuest = playAsGuest;
        idleAnimation = new Animation<>(0.1f, GameAssetsManager.getInstance().getIdleFrames(player.getHeroName()));
        runAnimation = new Animation<>(0.1f, GameAssetsManager.getInstance().getRunFrames(player.getHeroName()));
        damage = new Animation<>(0.1f, GameAssetsManager.getInstance().getIdleFrames("Damage"));
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void startNewGame() {
        player = Main.getMain().getGame().getPlayer();
        trees = Tree.generateTrees(450); // Random trees
        heart = new Heart(player.getHealth(), player.getHealth());
        weaponTipOffsetX = player.getWeapon().getWidth();
        weaponTipOffsetY = player.getWeapon().getHeight() / 2f;
        gameTime = game.getTime();
        countdownTime = game.getTime();

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        timerFont = new BitmapFont();
        gameInputProcessor = Gdx.input.getInputProcessor(); // Save it
        Gdx.input.setInputProcessor(gameInputProcessor);


        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("default.vert"),
            Gdx.files.internal("grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            System.err.println("Shader compile error: " + grayscaleShader.getLog());
        }
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput(delta);

        if (autoAim) {
            Gdx.input.setCursorCatched(true);
        } else {
            Gdx.input.setCursorCatched(false);
        }

        Animation<TextureRegion> currentAnimation = isRunning ? runAnimation : idleAnimation;
        TextureRegion currentFrame = currentAnimation.getKeyFrame(animationTimer);

        if (!timeUp) {
            countdownTime -= delta;
            if (countdownTime <= 0) {
                countdownTime = 0;
                timeUp = true;
            }
        }

        playerDrawX = Gdx.graphics.getWidth() / 2f - currentFrame.getRegionWidth() / 2f;
        playerDrawY = Gdx.graphics.getHeight() / 2f - currentFrame.getRegionHeight() / 2f;
        playerRect.x = playerDrawX;
        playerRect.y = playerDrawY;
        playerRect.width = currentFrame.getRegionWidth() * 1.5f;
        playerRect.height = currentFrame.getRegionHeight() * 1.5f;

        animationTimer += delta;
        float playerDx = 0, playerDy = 0;
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.UP))) playerDy += speed * delta;
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.DOWN))) playerDy -= speed * delta;
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.LEFT))) playerDx -= speed * delta;
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.RIGHT))) playerDx += speed * delta;

        // Spawn brain monsters
        enemySpawnTimer += delta;
        spawnInterval = 90 / (gameTime - countdownTime);

        if (enemySpawnTimer >= spawnInterval) {
            BrainMonster.spawn(brainMonsters, monsters);
            enemySpawnTimer = 0f;
        }

        eyeBatSpawnTimer += delta;
        eyeBatSpawnInterval = 300 / (4 * (gameTime - countdownTime) - gameTime + 30);
        if (gameTime - countdownTime > gameTime / 4f) { // TODO tagheer be 4f
            if (eyeBatSpawnTimer >= eyeBatSpawnInterval) {
                EyeBat.spawn(eyeBats, monsters);
                eyeBatSpawnTimer = 0f;
            }
        }
        Monster nearestEnemy = findNearestBrainMonster();

        // Calculate angle to nearest enemy once
        float angleRadians = 0f;

        if (autoAim) {
            if (nearestEnemy != null) {
                float dx = nearestEnemy.getX() - playerDrawX;
                float dy = nearestEnemy.getY() - playerDrawY;
                angleRadians = (float) Math.atan2(dy, dx);
            }
        } else {
            Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(mouse); // convert to world coordinates

            float dx = playerDrawX - mouse.x;
            float dy = playerDrawY - mouse.y;
            angleRadians = (float) Math.atan2(dy, dx);
            angleRadians += MathUtils.PI;
//            System.out.println("Mouse world: " + mouse.x + ", " + mouse.y + "   " + playerDrawX + ", " + playerDrawY);
//            System.out.println("Player: " + playerDrawX + ", " + playerDrawY);
//            System.out.println("Angle (deg): " + Math.toDegrees(angleRadians));


        }



        float angleDegrees = (float) Math.toDegrees(angleRadians);

        ArrayList<Monster> monstersToRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            monster.update(delta, playerDrawX, playerDrawY);
            monster.moveRelativeToPlayer(playerDx, playerDy);

            if (monster instanceof EyeBat) {
                for (EyeBatBullet bullet : ((EyeBat) monster).getBullets()) {
                    bullet.moveRelativeToPlayer(playerDx, playerDy);
                    bullet.update(delta);
                }
            }

            if (monster.isRemove()) {
                monstersToRemove.add(monster);
            }
            for (int i = 0; i < monsters.size(); i++) {
                Monster e1 = monsters.get(i);
                Rectangle r1 = e1.getBounds();

                for (int j = i + 1; j < monsters.size(); j++) {
                    Monster e2 = monsters.get(j);
                    Rectangle r2 = e2.getBounds();

                    if (r1.overlaps(r2)) {
                        resolveCollision(e1, e2);
                    }
                }
            }
        }
        monsters.removeAll(monstersToRemove);

        ArrayList<MonsterDeath> monsterDeathsToRemove = new ArrayList<>();
        for (MonsterDeath monsterDeath : monsterDeaths) {
            if (monsterDeath.remove) {
                monsterDeathsToRemove.add(monsterDeath);
            }
        }
        monsterDeaths.removeAll(monsterDeathsToRemove);



        float orbitRadius = 20f;
        float weaponX = playerDrawX - 10 + currentFrame.getRegionWidth() * 1.5f / 2f + MathUtils.cos(angleRadians) * orbitRadius;
        float weaponY = playerDrawY - 10 + currentFrame.getRegionHeight() * 1.5f / 2f + MathUtils.sin(angleRadians) * orbitRadius;

        float originX = player.getWeapon().getWidth() / 2f;
        float originY = player.getWeapon().getHeight() / 2f;

        float cos = MathUtils.cosDeg(angleDegrees);
        float sin = MathUtils.sinDeg(angleDegrees);
        Vector2 bulletDirection = new Vector2(cos, sin).nor();

        float angleRad = (float) Math.toRadians(angleDegrees);

        float rotatedTipX = (float)(weaponTipOffsetX * Math.cos(angleRad) - weaponTipOffsetY * Math.sin(angleRad));
        float rotatedTipY = (float)(weaponTipOffsetX * Math.sin(angleRad) + weaponTipOffsetY * Math.cos(angleRad));

        float bulletX = weaponX + originX + rotatedTipX;
        float bulletY = weaponY + originY + rotatedTipY;

        shootTimer += delta;
        if (reloadTimer >= weapon.getReloadTime()) {

            reloading = false;
            reloadingSound = false;
            firedBullets = 0;
            reloadTimer = 0f;
            reloadBarX = RELOAD_BAR_X;
            reloadBarY = RELOAD_BAR_Y;
        }

        if (Main.getMain().isAutoReload()) {
            if (firedBullets == weapon.getMaxAmmo()) {
                reloading = true;
            }
        }

        if (reloading && !reloadingSound) {
            GameAssetsManager.getInstance().getSounds("Reload").play(Main.getSoundEffects());
            reloadingSound = true;
        }


        Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseScreen); // get actual position in world space

        Vector2 target = new Vector2(mouseScreen.x, mouseScreen.y);
        Vector2 origin = new Vector2(playerDrawX, playerDrawY);
        Vector2 directionAim = target.sub(origin).nor();


        if (autoAim) {
            if (!KeyBindings.isMouse(KeyBindings.SHOOT)) {
                if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.SHOOT)) &&
                    shootTimer >= SHOOT_WAIT_TIME &&
                    firedBullets < weapon.getMaxAmmo()) {

                    if (!reloading) {
                        shootTimer = 0;
                        float spreadAngle = 7.5f;
                        float baseAngle;
                        if (autoAim) {
                            baseAngle = bulletDirection.angleDeg();
                        } else {
                            baseAngle = directionAim.angleDeg();
                        }

                        for (int i = 0; i < weapon.getProjectile(); i++) {
                            float offset = (i - (weapon.getProjectile() - 1) / 2f) * spreadAngle;
                            float finalAngle = baseAngle + offset;
                            Vector2 newDirection = new Vector2(1, 0).setAngleDeg(finalAngle).nor();
                            Bullet bullet = new Bullet(bulletX, bulletY, newDirection);
                            bullets.add(bullet);
                        }
                        GameAssetsManager.getInstance().getSounds("Shoot").play(Main.getSoundEffects());
                        firedBullets++;
                    }
                }
            } else {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shootTimer >= SHOOT_WAIT_TIME && firedBullets < weapon.getMaxAmmo()) {
                    if (!reloading) {
                        shootTimer = 0;
                        float spreadAngle = 7.5f;
                        float baseAngle;
                        if (autoAim) {
                            baseAngle = bulletDirection.angleDeg();
                        } else {
                            baseAngle = directionAim.angleDeg();
                        }

                        for (int i = 0; i < weapon.getProjectile(); i++) {
                            float offset = (i - (weapon.getProjectile() - 1) / 2f) * spreadAngle;
                            float finalAngle = baseAngle + offset;
                            Vector2 newDirection = new Vector2(1, 0).setAngleDeg(finalAngle).nor();
                            Bullet bullet = new Bullet(bulletX, bulletY, newDirection);
                            bullets.add(bullet);
                        }
                        GameAssetsManager.getInstance().getSounds("Shoot").play(Main.getSoundEffects());
                        firedBullets++;
                    }
                }
            }
        }

        // check for collisions
        for (Bullet bullet : bullets) {
            for (Monster monster : monsters) {
                if (monster.getBounds().overlaps(bullet.getBounds())) {
                    monster.setHb((int) (monster.getHb() - weapon.getDamage()));
                    GameAssetsManager.getInstance().getSounds("Damage").play(Main.getSoundEffects() * 0.5f);
                    Vector2 direction = new Vector2(bullet.getVelocity().x, bullet.getVelocity().y);

                    if (direction.len2() == 0) {
                        direction.set(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)); // prevent zero direction
                    }

                    direction.nor();
                    float pushAmount = 50f;

                    monster.moveBy(direction.x * pushAmount / 2, direction.y * pushAmount / 2);
                    if (monster.getHb() <= 0) {
                        monster.setRemove(true);
                        kills++;
                        MonsterDeath monsterDeath = new MonsterDeath(monster.getX(), monster.getY());
                        monsterDeaths.add(monsterDeath);
                        XpDrop.spawn(monster.getX(), monster.getY(), xpDrops);
                    }
                    bullet.deactivate();
                }
            }
        }

        // Update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (!bullet.isActive()) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);


        ArrayList<XpDrop> xpDropsToRemove = new ArrayList<>();
        for (XpDrop xpDrop : xpDrops) {
            xpDrop.moveRelativeToPlayer(playerDx, playerDy);
            if (playerRect.overlaps(xpDrop.getBounds())) {
                currentXp += 12; // TODO: 3 ta
                Random random = new Random();
                int x = random.nextInt(6);
                GameAssetsManager.getInstance().getXpSounds().get(x).play(Main.getSoundEffects());

                while (currentXp >= xpToNextLevel) {
                    currentXp -= xpToNextLevel;
                    level++;
                    showLevelUpUI = true;
                    xpToNextLevel = level * 20;
                    // Optionally: Trigger level-up effects here
                }
                xpDrop.setRemove(true);
                xpDropsToRemove.add(xpDrop);
            }
        }
        xpDrops.removeAll(xpDropsToRemove);

        for (Tree tree : trees) {
            tree.moveRelativeToPlayer(playerDx, playerDy, delta);
        }

        float fillRatio = (float) currentXp / (level * 20);
        int filledWidth = (int)(barWidth * fillRatio);


        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.RELOAD))) {
            reloading = true;
        }

        if (invincible) {
            invincibility += delta;
        }
        if (invincibility > 1.5f) {
            invincible = false;
            invincibility = 0;
        }
        updateDamage(delta);
        for (Monster monster : monsters) {
            if (monster instanceof EyeBat) {
                for (EyeBatBullet bullet : ((EyeBat) monster).getBullets()) {
                    if (bullet.getBounds().overlaps(playerRect) && !invincible) {
                        heart.setHealth(heart.getHealth() - 1);
                        GameAssetsManager.getInstance().getSounds("Damage").play(Main.getSoundEffects());
                        playingDamageAnimation = true;
                        invincible = true;
                        bullet.deactivate();
                    }
                }
            }
            if (monster.getBounds().overlaps(playerRect) && !invincible) {
                heart.setHealth(heart.getHealth() - 1);
                GameAssetsManager.getInstance().getSounds("Damage").play(Main.getSoundEffects());
                Vector2 direction = new Vector2(playerDrawX - monster.getX(), playerDrawY - monster.getY());

                if (direction.len2() == 0) {
                    direction.set(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)); // prevent zero direction
                }

                direction.nor();
                float pushAmount = 200f;
                monster.moveBy(-direction.x * pushAmount / 2, -direction.y * pushAmount / 2);
                playingDamageAnimation = true;
                invincible = true;
            }
        }

        for (MonsterDeath monsterDeath : monsterDeaths) {
            monsterDeath.update(delta);
        }

        for (Tree tree : trees) {
            if (tree.getBounds().overlaps(playerRect) && !invincible) {
                playingDamageAnimation = true;
                heart.setHealth(heart.getHealth() - 1);
                GameAssetsManager.getInstance().getSounds("Damage").play(Main.getSoundEffects());
                invincible = true;
            }
        }
        if (speedAbility)
            speedAbilityTimer += delta;
        if (speedAbilityTimer > 20) {
            speedAbilityTimer = 0;
            speed /= 2;
            speedAbility = false;
        }


        if (Main.getMain().isBlackAndWhite()) {
            batch.setShader(grayscaleShader);
        }
        batch.begin();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float offsetX = worldX % tileSize;
        float offsetY = worldY % tileSize;

        for (int y = -1; y < screenHeight / tileSize + 2; y++) {
            for (int x = -1; x < screenWidth / tileSize + 2; x++) {
                batch.draw(
                    tileTexture,
                    x * tileSize - offsetX,
                    y * tileSize - offsetY,
                    40,
                    40
                );
            }
        }



        for (Monster monster : monsters) {
            monster.draw(batch); // draw each monster
            if (monster instanceof EyeBat) {
                for (EyeBatBullet bullet : ((EyeBat) monster).getBullets()) {
                    bullet.draw(batch);
                }
            }
        }

        for (XpDrop xpDrop : xpDrops) {
            if (!Main.getMain().isBlackAndWhite()) {
                batch.setColor(50f / 255f, 100f / 255f, 40f / 255f, 1f);
                xpDrop.draw(batch);
                batch.setColor(Color.WHITE);
            } else {
                xpDrop.draw(batch);
            }
        }
        for (Monster monster : monsters) {
            monster.draw(batch);
        }
        if (nearestEnemy != null) {
            batch.draw(GameAssetsManager.getInstance().cursor(), nearestEnemy.getX() + nearestEnemy.getWidth() / 2f - GameAssetsManager.getInstance().cursor().getWidth() / 2,
                nearestEnemy.getY() + nearestEnemy.getHeight() / 2f - GameAssetsManager.getInstance().cursor().getHeight() / 2, 30, 30);
        }
        for (MonsterDeath monsterDeath : monsterDeaths) {
            monsterDeath.draw(batch);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        // Draw player centered
        batch.draw(currentFrame, playerDrawX, playerDrawY, currentFrame.getRegionWidth() * 1.5f, currentFrame.getRegionHeight() * 1.5f);
        batch.draw(
            player.getWeaponRegion(),
            weaponX,
            weaponY,
            originX,
            originY,
            player.getWeapon().getWidth() * 1.5f,
            player.getWeapon().getHeight() * 1.5f,
            1f, 1f,
            angleDegrees
        );

        for (Tree tree : trees) {
            tree.update(delta);
            tree.draw(batch);
        }

        // check for collisions
        for (Bullet bullet : bullets) {
            for (Tree tree : trees) {
                if (tree.getBounds().overlaps(bullet.getBounds())) {
                    if (!tree.isPlayAnimation()) {
                        tree.playAnimation();
                    }
                    bullet.deactivate();
                }
            }
        }

        if (reloading) {
            reloadTimer += delta;

            float reloadProgress = Math.min(reloadTimer / weapon.getReloadTime(), 1f);

            float barStartX = playerDrawX - currentFrame.getRegionWidth();
            float barY = playerDrawY + currentFrame.getRegionHeight() * 2;
            float barWidth = reloadLine.getWidth();
            float barHeight = reloadLine.getHeight();

            // Draw the horizontal reload line
            batch.draw(
                reloadLine,
                barStartX,
                barY
            );

            // Calculate the moving vertical bar position based on progress
            float progressBarX = barStartX + barWidth * reloadProgress;
            float progressBarHeight = reloadBar.getHeight();

            // Center the vertical line vertically on the base line
            float verticalOffset = (barHeight - progressBarHeight) / 2f;

            // Draw the vertical progress marker
            batch.draw(
                reloadBar,
                progressBarX,
                barY + verticalOffset
            );
        }

        drawDamage(batch);
        String timeString = formatTime(countdownTime);
        GlyphLayout layout = new GlyphLayout(timerFont, timeString);
        float x = Gdx.graphics.getWidth() - layout.width * 2;
        float y = Gdx.graphics.getHeight() - 20; // top, with 20px padding

        GlyphLayout levelLayout = new GlyphLayout(timerFont, "Level: " + level);
        GlyphLayout killsLayout = new GlyphLayout(timerFont, "Kills: " + kills);
        String remainingAmoInt = String.valueOf(weapon.getMaxAmmo() - firedBullets);
        GlyphLayout remainingAmo = new GlyphLayout(timerFont, remainingAmoInt);

        float levelX = Gdx.graphics.getWidth() / 2f - layout.width / 2f;
        float levelY = barY + barHeight * 2;
        timerFont.draw(batch, levelLayout, levelX, levelY);
        timerFont.draw(batch, layout, x, y);
        timerFont.draw(batch, killsLayout, 20, y);
        timerFont.draw(batch, remainingAmo, playerDrawX - 20, playerDrawY + 40);

        heart.update(delta);
        heart.drawHearts(batch, 20, Gdx.graphics.getHeight() - 40 - barHeight * 4);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, filledWidth, barHeight);

        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            saveGame();
            Pixmap screenshotPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Texture pausedBg = new Texture(screenshotPixmap);
            screenshotPixmap.dispose();
            Gdx.app.log("Save", "Game saved successfully!");
            GameAssetsManager.getInstance().setPauseBg(pausedBg);
            Main.getMain().setScreen(new PauseMenu(GameAssetsManager.getInstance().getSkin(), this, pausedBg));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            countdownTime -= 60;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            level++;
            showLevelUpUI = true;
            xpToNextLevel = level * 20;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (heart.getHealth() < heart.getMaxHealth()) {
                heart.setHealth(heart.getHealth() + 1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            switchAutoAim();
        }


        if (showLevelUpUI) {
            saveGame();
            Pixmap screenshotPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Texture pausedBg = new Texture(screenshotPixmap);
            screenshotPixmap.dispose();
            Main.getMain().setScreen(new ChooseAbilityScreen(GameAssetsManager.getInstance().getSkin(), this, pausedBg));
        }


        if (heart.getHealth() <= 0 || timeUp || countdownTime > gameTime) {
            Gdx.input.setCursorCatched(true);
            Pixmap screenshotPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Texture pausedBg = new Texture(screenshotPixmap);
            screenshotPixmap.dispose();
            Main.getMain().setScreen(new GameOverMenu(pausedBg, countdownTime, kills, playAsGuest, heart.getHealth()));
        }
    }

    private void handleInput(float delta) {
        isRunning = false;
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.UP))) {
            player.position.y += speed * delta;
            worldY += speed * delta;
            isRunning = true;
        }
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.DOWN))) {
            player.position.y -= speed * delta;
            worldY -= speed * delta;
            isRunning = true;
        }
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.LEFT))) {
            player.position.x -= speed * delta;
            worldX -= speed * delta;
            if (!facingLeft) {
                flipAnimation(runAnimation, true);
                flipAnimation(idleAnimation, true);
                facingLeft = true;
            }
            isRunning = true;
        }
        if (Gdx.input.isKeyPressed(KeyBindings.get(KeyBindings.RIGHT))) {
            player.position.x += speed * delta;
            worldX += speed * delta;
            if (facingLeft) {
                flipAnimation(runAnimation, false);
                flipAnimation(idleAnimation, false);
                facingLeft = false;
            }
            isRunning = true;
        }
    }

    private void flipAnimation(Animation<TextureRegion> animation, boolean flipX) {
        for (TextureRegion frame : animation.getKeyFrames()) {
            if (frame.isFlipX() != flipX) {
                frame.flip(true, false);
            }
        }
    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        for (TextureRegion frame : idleAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : runAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }

    private void resolveCollision(Monster e1, Monster e2) {
        Vector2 direction = new Vector2(e1.getX() - e2.getX(), e1.getY() - e2.getY());

        if (direction.len2() == 0) {
            direction.set(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)); // prevent zero direction
        }

        direction.nor();
        float pushAmount = 1f;

        e1.moveBy(direction.x * pushAmount / 2, direction.y * pushAmount / 2);
        e2.moveBy(-direction.x * pushAmount / 2, -direction.y * pushAmount / 2);
    }

    private Monster findNearestBrainMonster() {
        Monster nearestEnemy = null;
        float nearestDistance = Float.MAX_VALUE;

        for (Monster monster : monsters) {
            float dx = monster.getX() - playerDrawX;
            float dy = monster.getY() - playerDrawY;
            float dist = (dx * dx + dy * dy);
            if (dist < nearestDistance) {
                nearestDistance = dist;
                nearestEnemy = monster;
            }
        }
        return nearestEnemy;
    }

    public static String formatTime(float timeSeconds) {
        int minutes = (int)(timeSeconds / 60);
        int seconds = (int)(timeSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void saveGame() {
        GameSave data = new GameSave();

        data.heroName = player.getHero().getName();
        data.weaponName = player.getCurrentWeapon().toString();
        data.countdownTime = countdownTime;
        data.level = level;
        data.currentXp = currentXp;
        data.xpToNextLevel = xpToNextLevel;
        data.player = new PlayerSave();
        data.player.x = playerDrawX;
        data.player.y = playerDrawY;
        data.player.facingLeft = facingLeft;
        data.enemySpawnTimer = enemySpawnTimer;
        data.eyeBatSpawnTimer = eyeBatSpawnTimer;
        data.player.health = heart.getHealth();
        data.player.maxHealth = heart.getMaxHealth();
        data.spawnInterval = spawnInterval;
        data.eyeBatSpawnInterval = eyeBatSpawnInterval;
        data.gameTime = player.getGameTime();
        data.kills = kills;
        ArrayList<MonsterSave> monsterSaves = new ArrayList<>();
        for (Monster monster : monsters) {
            MonsterSave monsterSave = new MonsterSave();
            monsterSave.x = monster.getX();
            monsterSave.y = monster.getY();
            monsterSave.health = monster.getHb();
            monsterSave.type = monster instanceof BrainMonster ? "BrainMonster" : "EyeBat";

            monsterSaves.add(monsterSave);
        }
        data.monsters = monsterSaves;
        data.reloadTimer = reloadTimer;

        ArrayList<TreeSave> treeSaves = new ArrayList<>();
        for (Tree tree : trees) {
            TreeSave treeSave = new TreeSave();
            treeSave.x = tree.getX();
            treeSave.y = tree.getY();
            treeSaves.add(treeSave);
        }
        data.trees = treeSaves;

        ArrayList<XpDropSave> xpDropSaves = new ArrayList<>();
        for (XpDrop xpDrop : xpDrops) {
            XpDropSave xpDropSave = new XpDropSave();
            xpDropSave.x = xpDrop.getX();
            xpDropSave.y = xpDrop.getY();
            xpDropSaves.add(xpDropSave);
        }
        data.xpDrops = xpDropSaves;
        ArrayList<BulletSave> bulletSaves = new ArrayList<>();
        for (Bullet bullet : bullets) {
            BulletSave bulletSave = new BulletSave();
            bulletSave.x = bullet.getX();
            bulletSave.y = bullet.getY();
            bulletSave.velocityX = bullet.getVelocity().x;
            bulletSave.velocityY = bullet.getVelocity().y;
            bulletSaves.add(bulletSave);
        }
        data.bullets = bulletSaves;
        data.reloading = reloading;
        data.worldX = worldX;
        data.worldY = worldY;
        data.fireRate = SHOOT_WAIT_TIME;
        data.maxAmo = weapon.getMaxAmmo();
        data.reloadRate = weapon.getReloadTime();
        data.projectiles = weapon.getProjectile();

        Json json = new Json();
        FileHandle file = Gdx.files.local("savegame.json");
        file.writeString(json.prettyPrint(data), false);
        System.out.println(file.file().getAbsolutePath());
    }

    public void loadGame() {
        FileHandle file = Gdx.files.local("savegame.json");
        if (file.exists()) {
            Json json = new Json();
            GameSave data = json.fromJson(GameSave.class, file);

            HeroActor actor = null;
            switch (data.heroName) {
                case "Shana" : {
//                    player = new Player(PreGameMenu.shana, null, 0, 0, null);
                    actor = PreGameMenu.shana;
                    break;
                }
                case "Diamond": {
//                    player = new Player(PreGameMenu.diamond, null, 0, 0, null);
                    actor = PreGameMenu.diamond;
                    break;
                }
                case "Scarlett": {
//                    player = new Player(PreGameMenu.scarlett, null, 0, 0, null);
                    actor = PreGameMenu.scarlett;
                    break;
                }
                case "Lilith": {
//                    player = new Player(PreGameMenu.lilith, null, 0, 0, null);
                    actor = PreGameMenu.lilith;
                    break;
                }
                case "Dasher": {
//                    player = new Player(PreGameMenu.dasher, null, 0, 0, null);
                    actor = PreGameMenu.dasher;
                    break;
                }
            }
            switch (data.weaponName) {
                case "Shotgun": {
                    player = new Player(actor, null, 0, 0, "Shotgun", data.gameTime);
                    player.setWeapon(new Shotgun());
                    weapon = new Shotgun();
                    player.setWeaponTexture(GameAssetsManager.getInstance().getIdleFrames("Shotgun")[0]);
                    break;
                }
                case "DualSMG": {
                    player = new Player(actor, null, 0, 0, "Dual SMGs", data.gameTime);
                    player.setWeapon(new DualSMG());
                    weapon = new DualSMG();
                    player.setWeaponTexture(GameAssetsManager.getInstance().getIdleFrames("Dual SMGs")[0]);
                    break;
                }
                case "Revolver": {
                    player = new Player(actor, null, 0, 0, "Revolver", data.gameTime);
                    player.setWeapon(new Revolver());
                    weapon = new Revolver();
                    player.setWeaponTexture(GameAssetsManager.getInstance().getIdleFrames("Revolver")[0]);
                    break;
                }
            }

            player.position.x = data.player.x;
            player.position.y = data.player.y;
            playerDrawX = data.player.x;
            playerDrawY = data.player.y;
            countdownTime = data.countdownTime;
            level = data.level;
            player.setHealth(data.player.maxHealth);
            player.setHeroName(data.heroName);
            enemySpawnTimer = data.enemySpawnTimer;
            eyeBatSpawnTimer = data.eyeBatSpawnTimer;
            currentXp = data.currentXp;
            xpToNextLevel = data.xpToNextLevel;
            facingLeft = data.player.facingLeft;
            reloading = data.reloading;
            heart = new Heart(data.player.health, data.player.maxHealth);
            worldX = data.worldX;
            worldY = data.worldY;
            reloadTimer = data.reloadTimer;
            spawnInterval = data.spawnInterval;
            eyeBatSpawnInterval = data.eyeBatSpawnInterval;
            gameTime = data.gameTime;
            kills = data.kills;
            SHOOT_WAIT_TIME = data.fireRate;
            weapon.setMaxAmmo(data.maxAmo);
            weapon.setReloadTime(data.reloadRate);
            weapon.setProjectile(data.projectiles);

            for (XpDropSave xpDropSave : data.xpDrops) {
                XpDrop xpDrop = new XpDrop(xpDropSave.x, xpDropSave.y);
                xpDrops.add(xpDrop);
            }
            for (BulletSave bulletSave : data.bullets) {
                Vector2 direction = new Vector2(bulletSave.velocityX, bulletSave.velocityY);
                Bullet bullet = new Bullet(bulletSave.x, bulletSave.y, direction);
                bullets.add(bullet);
            }
            for (TreeSave treeSave : data.trees) {
                Tree tree = new Tree(treeSave.x, treeSave.y);
                trees.add(tree);
            }
            for (MonsterSave monsterSave : data.monsters) {
                if (monsterSave.type.equals("BrainMonster")) {
                    BrainMonster bm = new BrainMonster(monsterSave.x, monsterSave.y);
                    monsters.add(bm);
                } else if (monsterSave.type.equals("EyeBat")) {
                    EyeBat monster = new EyeBat(monsterSave.x, monsterSave.y);
                    monsters.add(monster);
                }
            }
        }
    }

    private void switchAutoAim() {
        autoAim = !autoAim;
    }

    public void drawDamage(Batch batch) {
        if (playingDamageAnimation) {
            TextureRegion currentFrame = damage.getKeyFrame(stateTime);
            batch.draw(currentFrame, playerDrawX, playerDrawY, currentFrame.getRegionWidth() / 2f, currentFrame.getRegionHeight() / 2f);
        }
    }

    public void updateDamage(float delta) {
        if (playingDamageAnimation) {
            stateTime += delta;
            if (damage.isAnimationFinished(stateTime)) {
                playingDamageAnimation = false;
                stateTime = 0;
            }
        }
    }
}
