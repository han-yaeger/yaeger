package nl.han.yaeger.javafx.debug;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.yaeger.engine.debug.Debugger;

/**
 * A value shown in the {@link Debugger}.
 */
public class DebugLabelFactory {

    private static final String DEBUGGER_FONT_FAMILY = "palatino";
    private static final int DEBUGGER_FONT_SIZE = 12;

    /**
     * Create a {@link Label} that can be used to display a value on the {@link Debugger}.
     *
     * @return The {@link Label} with an empty value.
     */
    public Label createValue() {
        return createValue("");
    }

    /**
     * Create a {@link Label} that can be used to display a value on the {@link Debugger}.
     *
     * @param string The {@link String} to be shown.
     * @return The {@link Label} with the supplied value.
     */
    public Label createValue(final String string) {
        var label = new Label(string);

        label.setTextFill(Color.GREEN);
        label.setFont(Font.font(DEBUGGER_FONT_FAMILY, FontWeight.NORMAL, DEBUGGER_FONT_SIZE));

        GridPane.setHalignment(label, HPos.RIGHT);
        return label;
    }

    /**
     * Instantiate a new {@link DebugLabelFactory} without any text value shown.
     *
     * @param string The {@link String} to be shown.
     * @return The {@link Label} with the supplied value.
     */
    public Label createLabel(final String string) {
        var label = new Label(string);

        label.setTextFill(Color.GREEN);
        label.setFont(Font.font(DEBUGGER_FONT_FAMILY, FontWeight.BOLD, DEBUGGER_FONT_SIZE));

        return label;
    }
}
