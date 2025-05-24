package me.hamed.untildawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameAssetsManager {
    private static GameAssetsManager instance;
    private Skin skin;
    private Skin otherSkin;

    // Maps hero name -> idle frames array
    private Map<String, TextureRegion[]> idleFramesMap = new HashMap<>();
    private Map<String, TextureRegion[]> runFramesMap = new HashMap<>();
    private Texture[] heros = new Texture[5];
    private ArrayList<Sound> xpCollect = new ArrayList<>();
    private Map<String, Sound> sounds = new HashMap<>();
    private Texture pauseBg;
    private Music menuMusic;
    private Music gameMusic;
    private Music otherMusic;
    private Music tlou;

    private Animation<TextureRegion> shubNiggurath;
    private Animation<TextureRegion> shubNiggurathDash;

    private GameAssetsManager() {
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        otherSkin = new Skin(Gdx.files.internal("neon/neon-ui.json"));
        loadIdleFrames();
        loadRunFrames();
        loadHeros();
        loadXpSounds();
        loadSounds();
        loadtlou();
        loadMenuMusic();
        loadMusic();
        loadOtherMusic();
        this.shubNiggurath = new Animation(0.2f, idleFramesMap.get("ShubNiggurath"), Animation.PlayMode.LOOP);
        this.shubNiggurathDash = new Animation(0.2f, idleFramesMap.get("ShubNiggurath Dash"), Animation.PlayMode.LOOP);
    }

    public void loadXpSounds() {
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_01.wav")));
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_02.wav")));
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_03.wav")));
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_04.wav")));
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_05.wav")));
        xpCollect.add(Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Obtain_Points_06.wav")));
    }

    public void loadSounds() {
        sounds.put("Shoot", Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/single_shot.wav")));
        sounds.put("Damage", Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/sfx_sounds_impact1.wav")));
        sounds.put("Reload", Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Weapon_Shotgun_Reload.wav")));
        sounds.put("Spawn", Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Monster_2_RecieveAttack_HighIntensity_01.wav")));
        sounds.put("Dash", Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Buff_BeastsPower.wav")));
    }

    public void loadHeros() {
        heros[0] = new Texture(Gdx.files.internal("Images/Sprite/T_Shana_Portrait.png"));
        heros[1] = new Texture(Gdx.files.internal("Images/Sprite/T_Diamond_Portrait.png"));
        heros[2] = new Texture(Gdx.files.internal("Images/Sprite/T_Scarlett_Portrait.png"));
        heros[3] = new Texture(Gdx.files.internal("Images/Sprite/T_Lilith_Portrait.png"));
        heros[4] = new Texture(Gdx.files.internal("Images/Sprite/T_Dasher_Portrait.png"));
    }

    public void loadRunFrames() {

        runFramesMap.put("Shana", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Run_0 #8762.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_1 #8778.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_2 #8286.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_3 #8349.png"))
        });
        runFramesMap.put("Diamond", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Run_0 #8760.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_1 #8776.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_2 #8284.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_3 #8347.png"))
        });
        runFramesMap.put("Scarlett", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Run_0 #8759.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_1 #8775.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_2 #8283.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_3 #8346.png"))
        });
        runFramesMap.put("Lilith", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Run_0 #8765.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_1 #8781.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_2 #8289.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_3 #8352.png"))
        });
        runFramesMap.put("Dasher", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Run_0 #8757.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_1 #8773.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_2 #8281.png")),
            new TextureRegion(new Texture("Images/Sprite/Run_3 #8344.png"))
        });
        runFramesMap.put("Shotgun", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_1.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_3.png")),
        });
        runFramesMap.put("Revolver", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverReload_0.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverReload_1.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverReload_2.png")),
            new TextureRegion(new Texture("Images/Sprite/RevolverReload_3.png"))
        });
        runFramesMap.put("Dual SMGs", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png")),
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png")),
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png")),
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png")),
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png")),
            new TextureRegion(new Texture("Images/Sprite/SMGReload_0.png")),
            new TextureRegion(new Texture("Images/Sprite/SMGReload_1.png")),
            new TextureRegion(new Texture("Images/Sprite/SMGReload_2.png")),
            new TextureRegion(new Texture("Images/Sprite/SMGReload_3.png")),
        });
        runFramesMap.put("Azina", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall1.png")),
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall2.png")),
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall3.png")),
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall4.png")),
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall5.png")),
            new TextureRegion(new Texture("Images/Texture2D/T_ElectricWall6.png"))
        });
    }

    private void loadIdleFrames() {
        idleFramesMap.put("Shana", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Idle_0 #8330.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_1 #8360.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_2 #8819.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_3 #8457.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_4 #8318.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_5 #8307.png"))
        });
        idleFramesMap.put("Diamond", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Idle_0 #8328.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_1 #8358.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_2 #8817.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_3 #8455.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_4 #8316.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_5 #8305.png"))
        });
        idleFramesMap.put("Scarlett", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Idle_0 #8327.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_1 #8357.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_2 #8816.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_3 #8454.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_4 #8315.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_5 #8304.png"))
        });
        idleFramesMap.put("Lilith", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Idle_0 #8333.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_1 #8363.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_2 #8822.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_3 #8460.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_4 #8321.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_5 #8310.png"))
        });
        idleFramesMap.put("Dasher", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/Idle_0 #8325.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_1 #8355.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_2 #8814.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_3 #8452.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_4 #8313.png")),
            new TextureRegion(new Texture("Images/Sprite/Idle_5 #8302.png"))
        });

        idleFramesMap.put("Damage", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_2.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_3.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_4.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_5.png"))
        });


        idleFramesMap.put("Shotgun", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_Shotgun_SS_0.png"))
        });
        idleFramesMap.put("Revolver", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/RevolverStill.png"))
        });
        idleFramesMap.put("Dual SMGs", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_DualSMGs_Icon.png"))
        });

        idleFramesMap.put("Brain Monster", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/BrainMonster_0.png")),
            new TextureRegion(new Texture("Images/Sprite/BrainMonster_1.png")),
            new TextureRegion(new Texture("Images/Sprite/BrainMonster_2.png")),
            new TextureRegion(new Texture("Images/Sprite/BrainMonster_3.png")),
        });

        idleFramesMap.put("Eye Bat", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_EyeBat_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_EyeBat_1.png")),
            new TextureRegion(new Texture("Images/Sprite/T_EyeBat_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_EyeBat_3.png")),
        });

        idleFramesMap.put("Tree", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_1.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_TreeMonster_1.png")),
        });

        idleFramesMap.put("Heart", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/HeartAnimation_0.png")),
            new TextureRegion(new Texture("Images/Sprite/HeartAnimation_1.png")),
            new TextureRegion(new Texture("Images/Sprite/HeartAnimation_2.png")),
        });

        idleFramesMap.put("Monster Death", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/DeathFX_0.png")),
            new TextureRegion(new Texture("Images/Sprite/DeathFX_1.png")),
            new TextureRegion(new Texture("Images/Sprite/DeathFX_2.png")),
            new TextureRegion(new Texture("Images/Sprite/DeathFX_3.png"))
        });

        idleFramesMap.put("ShubNiggurath", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_0.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_1.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_2.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_3.png"))
        });

        idleFramesMap.put("ShubNiggurath Dash", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_4.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_5.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_6.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_7.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_8.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_9.png")),
            new TextureRegion(new Texture("Images/Sprite/T_ShubNiggurath_10.png"))
        });
        idleFramesMap.put("Death", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_0.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_1.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_2.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_3.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_4.png")),
            new TextureRegion(new Texture("Images/Sprite/ExplosionFX_5.png")),
        });
        idleFramesMap.put("Elder Brain", new TextureRegion[] {
            new TextureRegion(new Texture("Images/Sprite/ElderBrain.png"))
        });
    }

    public static GameAssetsManager getInstance() {
        if (instance == null) {
            instance = new GameAssetsManager();
        }
        return instance;
    }

    public Skin getSkin() {
        return skin;
    }
    public Skin getOtherSkin() {
        return otherSkin;
    }

    public TextureRegion[] getIdleFrames(String heroName) {
        return idleFramesMap.get(heroName);
    }
    public TextureRegion[] getRunFrames(String heroName) {
        return runFramesMap.get(heroName);
    }
    public Texture[] getHeros() {
        return heros;
    }

    public void dispose() {
        for (TextureRegion[] frames : idleFramesMap.values()) {
            for (TextureRegion frame : frames) {
                frame.getTexture().dispose();
            }
        }
        skin.dispose();
    }

    public Texture getBulletTexture() {
        return new Texture("Images/Sprite/T_ChargeUp_0.png");
    }

    public Texture getEyeBatBulletTexture() {
        return new Texture("Images/Sprite/EyeMonsterProjecitle.png");
    }

    public Texture getXpTexture() {
        return new Texture("Images/Sprite/T_SmallCircle.png");
    }

    public Texture getTileTexture() {
        return new Texture("Images/Sprite/T_TileGrass.png");
    }

    public Texture emptyHeart() {
        return new Texture("Images/Sprite/HeartAnimation_3.png");
    }

    public Texture cursor() {
        return new Texture("Images/Sprite/T_CursorSprite.png");
    }


    private void loadMusic() {
        this.gameMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/AudioClip/Wasteland Combat Loop.wav"));
    }
    public Music gameMusic() {
        return gameMusic;
    }

    private void loadMenuMusic() {
        this.menuMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/AudioClip/Pretty Dungeon LOOP.wav"));
    }
    private void loadtlou() {
        this.tlou = Gdx.audio.newMusic(Gdx.files.internal("tlou.mp3"));
    }
    public Music tlouMusic() {
        return tlou;
    }

    public Music menuMusic() {
        return menuMusic;
    }

    public Music otherMusic() {
        return otherMusic;
    }

    private void loadOtherMusic() {
        this.otherMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX/Crystal Castles - Empathy.mp3"));
    }
    public Sound getSounds(String sound) {
        return sounds.get(sound);
    }
    public ArrayList<Sound> getXpSounds() {
        return xpCollect;
    }

    public void setPauseBg(Texture pauseBg) {
        this.pauseBg = pauseBg;
    }

    public Texture getPauseBg() {
        return pauseBg;
    }

    public Animation<TextureRegion> getShubNiggurath() {
        return shubNiggurath;
    }



    public Animation<TextureRegion> getShubNiggurathDash() {
        return shubNiggurathDash;
    }
}
