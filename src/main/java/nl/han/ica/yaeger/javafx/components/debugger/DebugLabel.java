package nl.han.ica.yaeger.javafx.components.debugger;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.yaeger.javafx.components.controls.YaegerLabel;

/**
 * A label shown in the {@link nl.han.ica.yaeger.engine.debug.Debugger}.
 */
public class DebugLabel extends YaegerLabel {

    /**
     * Instantiate a new {@code DebugLabel} without any text value shown.
     */
    public DebugLabel(String text) {
        super(text);

        setTextFill(Color.GREEN);
        setFont(Font.font(DEBUGGER_FONT, FontWeight.BOLD, 12));
    }
}
