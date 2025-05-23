package me.hamed.untildawn.controller;

import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.view.*;

public class MainMenuController {

    public static void logout() {
        Main.getMain().getGame().setLoggedInUser(null);
        Main.getMain().setScreen(new SignUpMenu(GameAssetsManager.getInstance().getSkin()));
    }

    public static void goToSetting() {
        Main.getMain().setScreen(new SettingMenu(GameAssetsManager.getInstance().getSkin(), false, null));
    }

    public static void scoreboard() {
        Main.getMain().setScreen(new Scoreboard(GameAssetsManager.getInstance().getSkin()));
    }

    public static void goToProfileMenu() {
        Main.getMain().setScreen(new ProfileMenu(GameAssetsManager.getInstance().getSkin()));
    }

    public static void talenMenu() {
        Main.getMain().setScreen(new HintMenu(GameAssetsManager.getInstance().getSkin()));
    }

    public static void pregameMenu() {
        Main.getMain().setScreen(new PreGameMenu(GameAssetsManager.getInstance().getSkin(), false));
    }
}
