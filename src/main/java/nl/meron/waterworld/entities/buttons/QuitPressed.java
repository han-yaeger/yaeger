package nl.meron.waterworld.entities.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;

public class QuitPressed extends TextEntity implements MousePressedListener {

    public static final String EXIT_GAME = "Exit game";
    private Waterworld waterworld;

    public QuitPressed(Waterworld waterworld) {
        super(new Point(680, 400), EXIT_GAME);
        this.waterworld = waterworld;
        setFill(Color.YELLOWGREEN);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.quitGame();
        }
    }
}
