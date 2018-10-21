package nl.han.ica.yaeger.javafx.components.debugger;

import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.yaeger.javafx.components.controls.YaegerLabel;

/**
 * A value shown in the {@link nl.han.ica.yaeger.engine.debug.Debugger}.
 */
public class DebugValue extends YaegerLabel {

    /**
     * Instantiate a new {@code DebugValue} without any text value shown.
     */
    public DebugValue() {
        this("");
    }

    /**
     * Instantiate a new {@code DebugValue} with the given text value shown.
     *
     * @param string the {@link String} to be shown
     */
    public DebugValue(String string) {
        super(string);

        setTextFill(Color.GREEN);
        setFont(Font.font(DEBUGGER_FONT, FontWeight.NORMAL, 12));

        GridPane.setHalignment(this, HPos.RIGHT);
    }
}
