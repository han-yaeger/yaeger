package nl.han.showcase.buttons;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.showcase.YaegerShowCase;
import nl.han.waterworld.Waterworld;
import nl.han.yaeger.engine.entities.entity.AnchorPoint;
import nl.han.yaeger.engine.entities.entity.Location;

public class Back extends Button {

    public static final String BACK = "Back";

    public Back(YaegerShowCase showCase, Location location) {
        super(location, BACK, showCase, YaegerShowCase.SCENE_SELECTION);
        setAnchorPoint(AnchorPoint.BOTTOM_LEFT);
        setFill(Color.SNOW);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }
}
