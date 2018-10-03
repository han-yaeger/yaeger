package nl.han.ica.yaeger.engine.debug;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Extends the JavaFX {@link Label} and sets the relevant properties.
 */
class DebugLabel extends Label {

    DebugLabel(String text) {
        super(text);
        setTextFill(Color.GREEN);
        setFont(Font.font("palatino", FontWeight.BOLD, 12));
    }
}
