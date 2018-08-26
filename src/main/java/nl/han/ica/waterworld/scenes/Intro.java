package nl.han.ica.waterworld.scenes;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.buttons.QuitButton;
import nl.han.ica.waterworld.entities.buttons.StartButton;
import nl.han.ica.yaeger.entities.text.TextEntity;
import nl.han.ica.yaeger.scene.SceneType;
import nl.han.ica.yaeger.scene.impl.StaticScene;

import java.util.Set;

public class Intro extends StaticScene {

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
    public void setupScene() {
        super.setupScene();

        setBackgroundImage("underwater2.jpg");
        setBackgroundAudio("audio/ocean.mp3");

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

        var twoText = new TextEntity(540, 370, "2");
        twoText.setFill(Color.ORANGE);
        twoText.setFont(Font.font("palatino", FontWeight.BOLD, 240));
        addEntity(twoText);

        var waterworldText = new TextEntity(380, 320, "Waterworld");
        waterworldText.setFill(Color.DARKBLUE);
        waterworldText.setFont(Font.font("palatino", FontWeight.BOLD, 80));
        addEntity(waterworldText);
    }
}
