package nl.han.ica.yaeger.javafx.components.controls;

import javafx.scene.control.Label;

/**
 * Extends the JavaFX {@link Label} and sets the relevant properties.
 */
public abstract class YaegerLabel extends Label {

    protected static final String DEBUGGER_FONT = "palatino";

    /**
     * Instantiate a new {@code YaegerLabel} for the given {@link String}.
     *
     * @param string the text that should be shown
     */
    protected YaegerLabel(String string) {
        super(string);
    }
}
