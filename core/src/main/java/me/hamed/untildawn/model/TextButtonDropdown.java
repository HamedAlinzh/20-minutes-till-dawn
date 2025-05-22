package me.hamed.untildawn.model;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;

public class TextButtonDropdown<T> extends Group {
    private TextButton button;
    private List<T> list;
    private ScrollPane scrollPane;
    private Window popup;
    private Array<T> items;
    private Skin skin;
    private Stage stage;
    private String title;
    private float width = 200f;
    private float itemHeight = 40f;
    private float popupWidth = 750;
    private float popupHeight = 160f; // Enough for ~4 items

    public interface SelectionListener<T> {
        void onSelected(T item);
    }

    public TextButtonDropdown(String title, Skin skin, Stage stage, SelectionListener<T> listener) {
        this.skin = skin;
        this.title = title;
        this.stage = stage;
        this.items = new Array<>();

        button = new TextButton(title, skin);
        addActor(button);
        setSize(button.getWidth(), button.getHeight());

        list = new List<>(skin);
        list.setAlignment(Align.center);
        scrollPane = new ScrollPane(list, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        popup = new Window("", skin);
        popup.setMovable(false);
        popup.setResizable(false);
        popup.setKeepWithinStage(true);
        popup.setVisible(false);
        popup.add(scrollPane).width(width).height(itemHeight * 4);
        popup.pack();

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (popup.isVisible()) {
                    popup.remove(); // Hide the popup
                } else {
                    list.setItems(items); // Update item list

                    // Ensure ScrollPane and List size is correct
                    scrollPane.setSize(popupWidth, popupHeight);
                    list.setSize(popupWidth, popupHeight); // Optional but helpful

                    // Rebuild popup content
                    popup.clearChildren();
                    popup.add(scrollPane).width(popupWidth).height(popupHeight);
                    popup.pack(); // Applies layout changes

                    // Add popup to stage (must be after sizing)
                    stage.addActor(popup);

                    // Get correct position relative to stage
                    Vector2 stageCoords = button.localToStageCoordinates(new Vector2(0, 0));

                    // Align horizontally centered below the button
                    popup.setPosition(
                        stageCoords.x + (button.getWidth() - popup.getWidth()) / 2f,
                        stageCoords.y - popup.getHeight()
                    );

                    popup.setVisible(true);
                }
            }
        });


        list.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                T selected = list.getSelected();
                if (listener != null) {
                    listener.onSelected(selected);
                }
                popup.remove();
                popup.setVisible(false);
            }
        });
    }

    public void setItems(Array<T> items) {
        this.items = items;
    }

    public void setWidth(float width) {
        this.width = width;
        button.setWidth(width);
    }

    public TextButton getButton() {
        return button;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        button.setSize(width, height); // Resize internal TextButton
    }


}
