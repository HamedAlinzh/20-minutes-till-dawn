package me.hamed.untildawn.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.model.User;
import me.hamed.untildawn.view.MainMenu;
import me.hamed.untildawn.view.PreGameMenu;
import me.hamed.untildawn.view.SignUpMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginMenuController {
    private static final String USER_FILE = "/home/hamed/Documents/20-Minutes-Till-Dawn/assets/jsonFiles/users.json";
    public static void login(TextField passwordF, TextField usernameF, Label errorLabel) throws IOException {
        String username = usernameF.getText().trim();
        String password = passwordF.getText().trim();

        String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
        JSONArray users = new JSONArray(content);
        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            String savedUsername = userObj.optString("username");
            String savedPassword = userObj.optString("password");

            if (username.equals(savedUsername)) {
                if (password.equals(savedPassword)) {
                    int avatarId = userObj.getInt("avatar");
                    int score;
                    try {
                        score = userObj.getInt("score");
                    } catch (Exception e) {
                        score = 0;
                    }
                    Texture avatarTexture = new Texture(avatarId + ".png");
                    Image image = new Image(avatarTexture);
                    Main.getMain().getGame().setLoggedInUser(new User(username, password, image, score, avatarId));
                    Main.getMain().setScreen(new MainMenu(GameAssetsManager.getInstance().getSkin()));
                    return;
                } else {
                    errorLabel.setText("Password is not correct!");
                    errorLabel.setVisible(true);
                    return;
                }
            }
        }

        errorLabel.setText("Invalid username!");
        errorLabel.setVisible(true);
    }

    public static void forgetPassword(Button button, TextField passwordF, TextField usernameF, Label errorLabel, CheckBox checkBox) throws IOException {
        String username = usernameF.getText().trim();
        String password = passwordF.getText().trim();
        String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
        JSONArray users = new JSONArray(content);

        if (!checkBox.isChecked()) {
            errorLabel.setText("Enable the checkbox to reset your password.");
            errorLabel.setVisible(true);
            return;
        }

        if (!button.isChecked()) {
            errorLabel.setText("Click the button to confirm password reset.");
            errorLabel.setVisible(true);
            return;
        }

        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            String savedUsername = userObj.optString("username");

            if (username.equals(savedUsername)) {

                boolean validPass =
                    password.length() >= 8 &&
                        SignUpMenuController.UPPER_CASE_PATTERN.matcher(password).find() &&
                        SignUpMenuController.LOWER_CASE_PATTERN.matcher(password).find() &&
                        SignUpMenuController.SPECIAL_CHAR_PATTERN.matcher(password).find();

                if (!validPass) {
                    errorLabel.setText("Password must be at least 8 characters \nand include upper, lower, and special characters.");
                    errorLabel.setVisible(true);
                    return;
                }

                userObj.put("password", password);
                Files.write(Paths.get(USER_FILE), users.toString(4).getBytes());

                errorLabel.setText("Password changed!");
                errorLabel.setVisible(true);
                return;
            }
        }
    }
}
