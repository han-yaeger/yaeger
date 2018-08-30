package nl.han.ica.waterworld.scenes;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.entities.text.TextEntity;
import nl.han.ica.yaeger.scene.impl.DynamicScene;
import nl.han.ica.yaeger.scene.SceneType;

public abstract class Level extends DynamicScene {

    protected Waterworld waterworld;
    int bubblesPopped = 0;
    private TextEntity bubblesPoppedText;
    private TextEntity healthText;

    Level(final Waterworld waterworld) {
        this.waterworld = waterworld;
    }

    @Override
    protected void setupEntities() {
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
