package me.hamed.untildawn.view;

public enum Texts {
    // Sign up menu
    USERNAME("username", "Nom d'utilisateur"),
    PASSWORD("password", "Mot de passe"),
    QUESTION("What favorite breakfast fish?", "Quel est votre poisson préféré au petit déjeuner ?"),
    SIGN_UP_LABEL("Sign Up Menu", "Menu d'inscription"),
    AVATAR_LABEL("Choose Your Avatar", "Choisissez votre avatar"),
    LOGIN("Login", "Connexion"),
    PLAY_AS_GUEST("Play As A Guest", "Jouer en tant qu'invité"),
    ENTER_MAIN_MENU("Enter Main Menu", "Entrer dans le menu principal"),
    USERNAME_ERROR("Username already exists", "Le nom d'utilisateur existe déjà"),
    PASSWORD_LENGTH_ERROR("Password is too short", "Le mot de passe doit comporter"),
    PASSWORD_ERROR("Invalid password format", "Format de mot de passe invalide"),
    AVATAR_ERROR("Choose an avatar", "Veuillez choisir un avatar"),

    // Login Menu
    LOGIN_MENU("Login Menu", "Menu de connexion"),
    ENTER_PREGAME_MENU("Enter Pre-Game Menu", "Entrer dans le menu de pré-jeu"),
    FORGET_PASSWORD("Forget Password", "Mot de passe oublié"),
    BACK("Back", "Retour"),
    PASSWORD_IS_NOT_CORRECT("Password is not correct!", "Le mot de passe est incorrect !"),
    INVALID_USERNAME("Invalid username!", "Nom d'utilisateur invalide !"),
    // ...

    // Main Menu
    SCORE_LABEL("Score:", "Score :"),
    USERNAME_LABEL("Username:", "Nom d'utilisateur :"),
    PREGAME("Pregame", "Pré-jeu"),
    SETTING("Setting", "Paramètres"),
    SCOREBOARD("Scoreboard", "Tableau de score"),
    PROFILE("Profile", "Profil"),
    TALENT("Talent", "Talent"),
    LOGOUT("Logout", "Déconnexion"),
    SAVE("Save", "Enregistrer"),
    SELECT_GAME_DURATION("Select Game Duration:", "Sélectionnez la durée de jeu :"),

    // Setting Menu
    MUSIC("Music", "Musique"),
    SOUND_EFFECTS("Sound Effects", "Effets sonores"),
    SOUND_EFFECTS_VOLUME("Sound Effects Volume", "Volume des effets sonores"),
    CHANGE_KEYBOARD("Change Keyboard", "Changer le clavier"),
    AUTO_RELOAD("Auto Reload: ", "Rechargement auto: "),
    BLACK_AND_WHITE("Black and White: ", "Noir et blanc: "),
    ON("ON", "ACTIVÉ"),
    OFF("OFF", "DÉSACTIVÉ");

    private final String english;
    private final String french;

    Texts(String english, String french) {
        this.english = english;
        this.french = french;
    }

    public String getEnglish() {
        return english;
    }

    public String getFrench() {
        return french;
    }
}
