package nl.han.ica.waterworld.entities.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.entities.text.TextEntity;
import nl.han.ica.yaeger.scene.SceneType;

public class StartButton extends TextEntity {

    private Waterworld waterworld;

    public StartButton(Waterworld waterworld) {
        super(380, 400, "Play game");
        this.waterworld = waterworld;
        setFill(Color.VIOLET);
        setFont(Font.font("palatino", FontWeight.BOLD, 30));
    }

    @Override
    protected void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.nextScene(SceneType.LEVEL_ONE);
        }
    }
}
