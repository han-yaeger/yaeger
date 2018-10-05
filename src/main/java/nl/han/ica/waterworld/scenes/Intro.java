package nl.han.ica.waterworld.scenes;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.buttons.QuitButton;
import nl.han.ica.waterworld.entities.buttons.StartButton;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.text.TextEntity;
import nl.han.ica.yaeger.engine.scene.SceneType;
import nl.han.ica.yaeger.engine.scene.impl.StaticScene;

import java.util.Set;

public class Intro extends StaticScene {

    private static final String BACKGROUND_AUDIO = "audio/ocean.mp3";
    private static final String WATERWORLD_NAME = "Waterworld";
    private static final String WATERWORLD_VERSION = "2";
    private static final String BACKGROUND_IMAGE = "underwater2.jpg";

    private Waterworld waterworld;

    public Intro(Waterworld waterworld) {
        this.waterworld = waterworld;
    }


    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.ENTER)) {
            waterworld.nextScene(SceneType.LEVEL_ONE);
        }
        if (input.contains(KeyCode.Q)) {
            waterworld.quitGame();
        }
    }

    @Override
    public void initializeScene() {
        setBackgroundImage(BACKGROUND_IMAGE);
        setBackgroundAudio(BACKGROUND_AUDIO);
    }

    @Override
    public void setupScene() {
        super.setupScene();

        createWaterworldText();
        createButtons();
    }

    private void createButtons() {
        var playGameText = new StartButton(waterworld);
        addEntity(playGameText);

        var quitGameText = new QuitButton(waterworld);
        addEntity(quitGameText);
    }

    private void createWaterworldText() {

        var twoText = new TextEntity(new Position(540, 370), WATERWORLD_VERSION);
        twoText.setFill(Color.ORANGE);
        twoText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 240));
        addEntity(twoText);

        var waterworldText = new TextEntity(new Position(380, 320), WATERWORLD_NAME);
        waterworldText.setFill(Color.DARKBLUE);
        waterworldText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 80));
        addEntity(waterworldText);
    }
}
