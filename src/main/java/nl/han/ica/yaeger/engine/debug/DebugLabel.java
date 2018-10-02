package nl.han.ica.yaeger.engine.debug;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DebugLabel extends Label {

    public DebugLabel(String text) {
        super(text);
        setTextFill(Color.GREEN);
        setFont(Font.font("palatino", FontWeight.BOLD, 12));
    }
}
