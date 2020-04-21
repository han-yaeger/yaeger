package nl.han.showcase.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.showcase.YaegerShowCase;
import nl.han.waterworld.Waterworld;
import nl.han.yaeger.engine.entities.entity.Location;

public class Quit extends Button {

    private static final String QUIT = "Quit";

    public Quit(YaegerShowCase showCase) {
        super(new Location(20, 650), QUIT, showCase, -1);
        setFill(Color.SNOW);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onMouseButtonPressed(MouseButton button, double x, double y) {
        showCase.quitGame();
    }
}
