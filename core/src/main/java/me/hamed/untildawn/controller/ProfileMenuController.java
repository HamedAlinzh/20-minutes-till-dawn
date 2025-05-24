package me.hamed.untildawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.User;
import me.hamed.untildawn.view.MainMenu;
import me.hamed.untildawn.view.SignUpMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProfileMenuController {
    private static final String USER_FILE = "/home/hamed/Documents/20-Minutes-Till-Dawn/assets/jsonFiles/users.json";

    public static void changeProfile(String newUsername, String newPass, String repeatedNewPass, Label errorLabel, int selectedAvatar) throws IOException {
        User user = Main.getMain().getGame().getLoggedInUser();

        // Load user data once
        String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
        JSONArray users = new JSONArray(content);
        JSONObject currentUserObj = null;

        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            if (userObj.getString("username").equals(user.getUsername())) {
                currentUserObj = userObj;
                break;
            }
        }

        if (currentUserObj == null) {
            errorLabel.setText("Current user not found!");
            errorLabel.setVisible(true);
            return;
        }

        boolean modified = false;

        // --- Username update ---
        if (newUsername != null && !newUsername.trim().isEmpty() && !newUsername.equals("New Username")) {
            for (int i = 0; i < users.length(); i++) {
                JSONObject userObj = users.getJSONObject(i);
                if (newUsername.equals(userObj.getString("username"))) {
                    errorLabel.setText("Username is already in use!");
                    errorLabel.setVisible(true);
                    return;
                }
            }
            currentUserObj.put("username", newUsername);
            user.setUsername(newUsername);
            modified = true;
        }

        // --- Password update ---
        if (newPass != null && repeatedNewPass != null &&
            !newPass.equals("New Password") ||
            !repeatedNewPass.equals("Repeat New Password")) {

            if (!newPass.equals(repeatedNewPass)) {
                errorLabel.setText("Passwords do not match!");
                errorLabel.setVisible(true);
                return;
            }

            if (newPass.length() < 8) {
                errorLabel.setText("Password length invalid!");
                errorLabel.setVisible(true);
                return;
            }

            if (!SignUpMenuController.SPECIAL_CHAR_PATTERN.matcher(newPass).find() ||
                !SignUpMenuController.UPPER_CASE_PATTERN.matcher(newPass).find() ||
                !SignUpMenuController.LOWER_CASE_PATTERN.matcher(newPass).find()) {
                errorLabel.setText("Password is not strong enough!");
                errorLabel.setVisible(true);
                return;
            }

            currentUserObj.put("password", newPass);
            user.setPassword(newPass); // assuming User has this method
            modified = true;
        }
        // --- Avatar update ---
        if (selectedAvatar != -1) {
            if (selectedAvatar == 0) {
                user.setAvatar(SignUpMenu.avatar1);
            } else if (selectedAvatar == 1) {
                user.setAvatar(SignUpMenu.avatar2);
            } else if (selectedAvatar == 2) {
                user.setAvatar(SignUpMenu.avatar3);
            } else if (selectedAvatar == 3) {
                user.setAvatar(SignUpMenu.avatar4);
            }
            currentUserObj.put("avatar", selectedAvatar);
            modified = true;
        }

        // --- Save if any changes ---
        if (modified) {
            Files.write(Paths.get(USER_FILE), users.toString(4).getBytes());
            Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
        } else {
            Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
            errorLabel.setVisible(true);
        }
    }
    public static void deleteAccount() {
        try {
            User user = Main.getMain().getGame().getLoggedInUser();

            // Load the user file
            String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
            JSONArray users = new JSONArray(content);

            // Find and remove the current user
            for (int i = 0; i < users.length(); i++) {
                JSONObject userObj = users.getJSONObject(i);
                if (userObj.getString("username").equals(user.getUsername())) {
                    users.remove(i); // remove the user
                    break;
                }
            }

            // Save the updated users list back to the file
            Files.write(Paths.get(USER_FILE), users.toString(4).getBytes());

            // Optionally log out and go to main menu or login screen
            MainMenuController.logout();

        } catch (IOException e) {
            e.printStackTrace(); // Handle file read/write issues
        } catch (Exception e) {
            e.printStackTrace(); // Catch other issues (e.g., null pointers)
        }
    }

    public static void saveScore(int score, int kills, String timeAlive) throws IOException {
        User user = Main.getMain().getGame().getLoggedInUser();

        String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
        JSONArray users = new JSONArray(content);
        JSONObject currentUserObj = null;
        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            if (userObj.getString("username").equals(user.getUsername())) {
                currentUserObj = userObj;
                break;
            }
        }

        if (currentUserObj != null) {
            String scoreString = String.valueOf(score);
            currentUserObj.put("score", scoreString);
            currentUserObj.put("kills", kills);
            currentUserObj.put("timeAlive", timeAlive);
        }
        Files.write(Paths.get(USER_FILE), users.toString(4).getBytes());

    }

}
