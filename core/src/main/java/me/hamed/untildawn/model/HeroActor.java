package me.hamed.untildawn.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class HeroActor extends Actor {
    private Texture circle1, circle2, circle3;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private float stateTime;
    private boolean isHovered = false;
    private boolean isSelected = false;
    private int groupId;
    private Texture selectedHeroTexture;
    private String name;
    private TextureRegion[] frames;
    private int health;

    private static HeroActor selectedHero = null;
    private static HeroActor selectedWeapon = null;

    private float frameWidth;
    private float frameHeight;

    public HeroActor(TextureRegion[] idleFrames, TextureRegion[] runFrames, float frameDuration, int scale, int groupId, Texture selectedHeroTexture, String name, int health) {
        this.circle1 = new Texture("Images/Sprite/T_SummonPulseFX_0.png");
        this.circle2 = new Texture("Images/Sprite/T_SummonPulseFX_1.png");
        this.circle3 = new Texture("Images/Sprite/T_SummonPulseFX_2.png");
        this.name = name;
        this.idleAnimation = new Animation<>(frameDuration, idleFrames);
        this.runAnimation = new Animation<>(frameDuration, runFrames);
        this.stateTime = 0f;
        this.groupId = groupId;
        this.frames = idleFrames;
        this.selectedHeroTexture = selectedHeroTexture;
        this.frameWidth = idleFrames[0].getRegionWidth();
        this.frameHeight = idleFrames[0].getRegionHeight();
        this.health = health;

        setSize(frameWidth * scale, frameHeight * scale);
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isHovered = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isHovered = false;
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (groupId == 1) {
                    if (selectedHero != null) selectedHero.isSelected = false;
                    selectedHero = HeroActor.this;
                } else if (groupId == 2) {
                    if (selectedWeapon != null) selectedWeapon.isSelected = false;
                    selectedWeapon = HeroActor.this;
                }
                isSelected = true;
                return true;
            }
        });
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
    public Texture getSelectedHeroTexture() {
        return selectedHeroTexture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        currentFrame = (isHovered || isSelected) ? runAnimation.getKeyFrame(stateTime, true)
            : idleAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float baseX = getX();
        float baseY = getY();
        float width = 100;
        float height = 100;
        TextureRegion frame = isSelected || isHovered
            ? runAnimation.getKeyFrame(stateTime, true)
            : idleAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
        // Layer sizes
        float[] padding = {20, 15, 10}; // outer to inner
        Texture[] circles = {circle1, circle2, circle3}; // from outer to inner

        // Set color based on state
        if (isSelected) {
            batch.setColor(Color.RED);
        } else if (isHovered) {
            batch.setColor(Color.YELLOW);
        } else {
            batch.setColor(Color.LIGHT_GRAY);
        }

        // Draw each circle
        for (int i = 0; i < circles.length; i++) {
            float pad = padding[i];
            batch.draw(circles[i], baseX - pad - 30, baseY - pad - 25, width + pad * 2, height + pad * 2);
        }

        batch.setColor(Color.WHITE);

        batch.draw(currentFrame, baseX, baseY, getWidth(), getHeight());
    }

    public static HeroActor getSelectedHero() {
        return selectedHero;
    }

    public static HeroActor getSelectedWeapon() {
        return selectedWeapon;
    }

    public String getName() {
        return name;
    }

    public TextureRegion[] getFrames() {
        return frames;
    }

    public int getHealth() {
        return health;
    }
}
