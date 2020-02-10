package nl.meron.yaeger.javafx.debug;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.yaeger.engine.debug.Debugger;

/**
 * A value shown in the {@link Debugger}.
 */
public class DebugLabelFactory {

    private static final String DEBUGGER_FONT_FAMILY = "palatino";
    private static final int DEBUGGER_FONT_SIZE = 12;

    /**
     * Create a {@link} that can be used to display a {@code value on the {@link Debugger }
     */
    public Label createValue() {
        return createValue("");
    }


    /**
     * Create a {@link} that can be used to display a {@code value on the {@link Debugger }
     *
     * @param string the {@link String} to be shown
     */
    public Label createValue(final String string) {
        var label = new Label(string);

        label.setTextFill(Color.GREEN);
        label.setFont(Font.font(DEBUGGER_FONT_FAMILY, FontWeight.NORMAL, DEBUGGER_FONT_SIZE));

        GridPane.setHalignment(label, HPos.RIGHT);
        return label;
    }

    /**
     * Instantiate a new {@code DebugLabelFactory} without any text value shown.
     */
    public Label createLabel(final String string) {
        var label = new Label(string);

        label.setTextFill(Color.GREEN);
        label.setFont(Font.font(DEBUGGER_FONT_FAMILY, FontWeight.BOLD, DEBUGGER_FONT_SIZE));

        return label;
    }
}
