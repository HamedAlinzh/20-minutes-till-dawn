package me.hamed.untildawn.controller;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import me.hamed.untildawn.Main;
import me.hamed.untildawn.model.GameAssetsManager;
import me.hamed.untildawn.view.LoginMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

public class SignUpMenuController {
    private static final String USER_FILE = "/home/hamed/Documents/20-Minutes-Till-Dawn/assets/jsonFiles/users.json";
    // At least one of the following: @ _ ( ) * & % $ # And lower case and upper case
    public static final Pattern SPECIAL_CHAR_PATTERN =
        Pattern.compile(".*[!_()*&%$#@].*");
    public static final Pattern UPPER_CASE_PATTERN =
        Pattern.compile(".*[A-Z].*");
    public static final Pattern LOWER_CASE_PATTERN =
        Pattern.compile(".*[a-z].*");

    public static void signUp(Button button, TextField passwordF, TextField usernameF, BitmapFont font, Label errorLabel, int selectedAvatar) throws IOException {
        String username = usernameF.getText();
        String password = passwordF.getText();
        float textX;
        float textY = button.getY() - 20; // 10 pixels below the button
        String content = new String(Files.readAllBytes(Paths.get(USER_FILE)));
        JSONArray users = new JSONArray(content);

        if (button.isChecked()) {
            for (int i = 0; i < users.length(); i++) {
                JSONObject userObj = users.getJSONObject(i);
                if (username.equals(userObj.optString("username"))) {
                    errorLabel.setText("Username is already in use!");
                    errorLabel.setVisible(true);
                    return;
                }
            }

            if (password.length() < 8) {
                errorLabel.setText("Password is too short!");
                errorLabel.setVisible(true);
                return;
            }

            if (!SPECIAL_CHAR_PATTERN.matcher(password).find() || !UPPER_CASE_PATTERN.matcher(password).find() || !LOWER_CASE_PATTERN.matcher(password).find()) {
                errorLabel.setText("Password is not strong enough!");
                errorLabel.setVisible(true);
                return;
            }

            if (selectedAvatar == -1) {
                errorLabel.setText("Select an avatar!");
                errorLabel.setVisible(true);
                return;
            }

            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("avatar", selectedAvatar);
            newUser.put("score", 0);
            newUser.put("kills", 0);
            newUser.put("timeAlive", "00:00");
            users.put(newUser);

            Files.write(
                Paths.get(USER_FILE),
                users.toString(2).getBytes(),
                StandardOpenOption.TRUNCATE_EXISTING
            );
            Main.getMain().setScreen(new LoginMenu(GameAssetsManager.getInstance().getSkin()));
        }
    }
}
