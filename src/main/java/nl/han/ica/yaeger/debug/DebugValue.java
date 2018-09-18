package nl.han.ica.yaeger.debug;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DebugValue extends Label {

    public DebugValue() {
        this("");
    }

    public DebugValue(String string) {
        super(string);

        setTextFill(Color.GREEN);
        setFont(Font.font("palatino", FontWeight.NORMAL, 12));

        GridPane.setHalignment(this, HPos.RIGHT);
    }
}
