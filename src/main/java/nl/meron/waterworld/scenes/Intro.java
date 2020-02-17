package nl.meron.waterworld.scenes;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.waterworld.Waterworld;
import nl.meron.waterworld.entities.buttons.QuitPressed;
import nl.meron.waterworld.entities.buttons.StartPressed;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.StaticScene;

import java.util.Set;

public class Intro extends StaticScene {

    private static final String BACKGROUND_AUDIO = "waterworld/audio/ocean.mp3";
    private static final String WATERWORLD_NAME = "Waterworld";
    private static final String WATERWORLD_VERSION = "2";
    private static final String BACKGROUND_IMAGE = "waterworld/underwater2.jpg";

    private Waterworld waterworld;

    public Intro(Waterworld waterworld) {
        this.waterworld = waterworld;
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.ENTER)) {
            waterworld.nextScene(Waterworld.SCENE_LEVEL_ONE);
        }
        if (input.contains(KeyCode.Q)) {
            waterworld.quitGame();
        }
    }

    @Override
    public void setupScene() {
        setBackgroundImage(BACKGROUND_IMAGE);
        setBackgroundAudio(BACKGROUND_AUDIO);
    }

    @Override
    public void setupEntities() {
        createWaterworldText();
        createButtons();
    }

    private void createButtons() {
        var playGameText = new StartPressed(waterworld);
        addEntity(playGameText);

        var quitGameText = new QuitPressed(waterworld);
        addEntity(quitGameText);
    }

    private void createWaterworldText() {

        var twoText = new TextEntity(new Location(540, 220), WATERWORLD_VERSION);
        twoText.setFill(Color.ORANGE);
        twoText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 300));
        addEntity(twoText);

        var waterworldText = new TextEntity(new Location(380, 320), WATERWORLD_NAME);
        waterworldText.setFill(Color.DARKBLUE);
        waterworldText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 80));
        addEntity(waterworldText);

    }
}
