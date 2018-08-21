package nl.han.ica.waterworld.scenes;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.waterworld.entities.spel.Player;
import nl.han.ica.waterworld.entities.spel.Swordfish;
import nl.han.ica.waterworld.entities.spel.spawners.BubbleSpawner;
import nl.han.ica.yaeger.entities.text.TextEntity;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;
import nl.han.ica.yaeger.scene.SceneType;
import nl.han.ica.yaeger.scene.DynamicScene;

public class GameScene extends DynamicScene {

    private int bubblesPopped = 0;
    private TextEntity bubblesPoppedText;
    private TextEntity healthText;
    private Waterworld waterworld;
    private Sound backgroundAudio;

    public GameScene(final Waterworld waterworld) {
        this.waterworld = waterworld;
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
     * Verhoog de waarde van het aantal ontplofte bubbles.
     */
    public void increaseBubblesPopped() {
        bubblesPopped++;
        updateBubblesPoppedText();
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

    @Override
    public void setupScene() {

        super.setupScene();

        setBackgroundImage("underwater2.jpg");
        setBackgroundAudio();
    }

    @Override
    public void tearDownScene() {
        super.tearDownScene();
        backgroundAudio.stop();
    }

    @Override
    protected void setupSpawners() {
        var spawner = new BubbleSpawner(waterworld.getGameWidth(), waterworld.getGameHeight(), this);
        registerSpawner(spawner);
    }

    @Override
    public void setupDynamicEntities() {
        setupDashboard();

        var swordFish = new Swordfish(200, 200);
        addEntity(swordFish);

        var player = new Player(100, 100, this);
        addEntity(player);
    }

    private void setBackgroundAudio() {
        backgroundAudio = new Sound("audio/waterworld.mp3", Sound.INDEFINITE);
        backgroundAudio.play();
    }

    private void setupDashboard() {
        bubblesPoppedText = new TextEntity(10, 40);
        bubblesPoppedText.setFont(Font.font("palatino", 40));
        bubblesPoppedText.setFill(Color.VIOLET);
        addEntity(bubblesPoppedText);
        updateBubblesPoppedText();

        healthText = new TextEntity(960, 40);
        healthText.setFont(Font.font("palatino", 40));
        healthText.setFill(Color.DARKBLUE);
        addEntity(healthText);
        setHealthText(10);
    }
}
