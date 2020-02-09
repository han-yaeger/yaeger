package nl.meron.showcase.buttons;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Location;

public class Back extends Button {

    public static final String BACK = "Back";

    public Back(YaegerShowCase showCase) {
        super(new Location(20, 650), BACK, showCase, YaegerShowCase.SCENE_SELECTION);
        setFill(Color.SNOW);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }
}
