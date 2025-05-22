package me.hamed.untildawn.model;

public class HeroSelectionManager {
    private static HeroSelectionManager instance;
    private HeroActor selectedHero;

    public static HeroSelectionManager getInstance() {
        if (instance == null) {
            instance = new HeroSelectionManager();
        }
        return instance;
    }

    public void setSelected(HeroActor hero) {
        if (selectedHero != null) {
            selectedHero.setSelected(false);
        }
        selectedHero = hero;
        hero.setSelected(true);
    }

    public HeroActor getSelectedHero() {
        return selectedHero;
    }
}
