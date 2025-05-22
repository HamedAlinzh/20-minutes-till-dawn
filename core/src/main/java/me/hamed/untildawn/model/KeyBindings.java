package me.hamed.untildawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class KeyBindings {
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String SHOOT = "shoot";
    public static final String RELOAD = "reload";

    private static final Map<String, Integer> bindings = new HashMap<>();
    private static final Map<String, Boolean> isMouseBinding = new HashMap<>();

    static {
        // Default bindings
        bindings.put(UP, Input.Keys.W);
        bindings.put(DOWN, Input.Keys.S);
        bindings.put(LEFT, Input.Keys.A);
        bindings.put(RIGHT, Input.Keys.D);
        bindings.put(SHOOT, Input.Keys.SPACE);
        bindings.put(RELOAD, Input.Keys.R);
        isMouseBinding.put(SHOOT, false);
    }

    public static int get(String action) {
        return bindings.get(action);
    }

    public static void set(String action, int keycode, boolean mouse) {
        bindings.put(action, keycode);
        isMouseBinding.put(action, mouse);
    }

    public static boolean isMouse(String action) {
        return isMouseBinding.getOrDefault(action, false);
    }

    public static Map<String, Integer> getBindings() {
        return bindings;
    }

    public static void saveBindings() {
        Preferences prefs = Gdx.app.getPreferences("keyBindings");
        for (Map.Entry<String, Integer> entry : bindings.entrySet()) {
            prefs.putInteger(entry.getKey(), entry.getValue());
            prefs.putBoolean(entry.getKey() + "_isMouse", isMouseBinding.getOrDefault(entry.getKey(), false));
        }
        prefs.flush();
    }

    public static void loadBindings() {
        Preferences prefs = Gdx.app.getPreferences("keyBindings");
        for (String action : bindings.keySet()) {
            int key = prefs.getInteger(action, bindings.get(action));
            boolean isMouse = prefs.getBoolean(action + "_isMouse", false);
            bindings.put(action, key);
            isMouseBinding.put(action, isMouse);
        }
    }
}
