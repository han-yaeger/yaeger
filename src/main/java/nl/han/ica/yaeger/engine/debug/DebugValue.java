package nl.han.ica.yaeger.engine.debug;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Extends the JavaFX {@link Label} and sets the relevant properties.
 */
class DebugValue extends Label {

    DebugValue() {
        this("");
    }

    DebugValue(String string) {
        super(string);

        setTextFill(Color.GREEN);
        setFont(Font.font("palatino", FontWeight.NORMAL, 12));

        GridPane.setHalignment(this, HPos.RIGHT);
    }
}
