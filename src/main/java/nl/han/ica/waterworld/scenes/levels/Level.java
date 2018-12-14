package nl.han.ica.waterworld.scenes.levels;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.game.Player;
import nl.han.ica.waterworld.entities.game.spawners.BubbleSpawner;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.text.TextEntity;
import nl.han.ica.yaeger.engine.scenes.impl.DynamicScene;
import nl.han.ica.yaeger.engine.scenes.SceneType;

public abstract class Level extends DynamicScene {

    private static final String BACKGROUND_AUDIO = "audio/waterworld.mp3";

    protected Waterworld waterworld;
    int bubblesPopped = 0;
    private TextEntity bubblesPoppedText;
    private TextEntity healthText;

    Level(final Waterworld waterworld) {
        this.waterworld = waterworld;
    }

    @Override
    public void setupEntities() {
        bubblesPoppedText = new TextEntity(new Position(10, 40));
        bubblesPoppedText.setFont(Font.font(Waterworld.FONT, 40));
        bubblesPoppedText.setFill(Color.VIOLET);
        addEntity(bubblesPoppedText);
        updateBubblesPoppedText();

        healthText = new TextEntity(new Position(960, 40));
        healthText.setFont(Font.font(Waterworld.FONT, 40));
        healthText.setFill(Color.DARKBLUE);
        addEntity(healthText);
        setHealthText(10);

        var player = new Player(new Position(100, 100), this, 10);
        addEntity(player);
    }

    @Override
    public void setupScene() {

//        setBackgroundAudio(BACKGROUND_AUDIO);
    }

    /**
     * Zet de healthwaarde van de speler.
     *
     * @param health De health als integer.
     */
    public void setHealthText(final int health) {
        healthText.setText("Health: " + health);
    }

    /**
     * Verhoog de waarde van het aantal gepopte bubbles.
     */
    public void increaseBubblesPopped() {
        bubblesPopped++;
        updateBubblesPoppedText();
    }

    @Override
    protected void setupSpawners() {
        var spawner = new BubbleSpawner(waterworld.getGameWidth(), waterworld.getGameHeight(), this);
        registerSpawner(spawner);
    }

    /**
     * Deze methode wordt aangeroepen wanneer de speler sterft.
     */
    public void playerDied() {
        waterworld.nextScene(SceneType.GAMEOVER);
    }

    private void updateBubblesPoppedText() {
        bubblesPoppedText.setText("Bubbles popped: " + bubblesPopped);
    }
}
