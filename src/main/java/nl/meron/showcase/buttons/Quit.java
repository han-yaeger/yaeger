package nl.meron.showcase.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Location;

public class Quit extends Button {

    private static final String QUIT = "Quit";

    public Quit(YaegerShowCase showCase) {
        super(new Location(20, 650), QUIT, showCase, -1);
        setFill(Color.SNOW);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onButtonPressed(MouseButton button) {
        showCase.quitGame();
    }
}
