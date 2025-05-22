package me.hamed.untildawn.model;

public enum Abilities {
    SPEEDY("Speedy"),
    VITALITY("Vitality"),
    DAMAGER("Damager"),
    PROCREASE("Procrease"),
    AMOCREASE("Amocrease"),
    FIRERATE("Firecrease"),
    RELOAD("Reload");

    private String name;
    Abilities(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
