package nl.han.ica.waterworld.entities.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.entities.text.TextEntity;

public class QuitButton extends TextEntity {

    private Waterworld waterworld;

    public QuitButton(Waterworld waterworld) {
        super(680, 400, "Exit game");
        this.waterworld = waterworld;
        setFill(Color.YELLOWGREEN);
        setFont(Font.font("palatino", FontWeight.BOLD, 30));
    }

    @Override
    protected void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.quitGame();
        }
    }
}
