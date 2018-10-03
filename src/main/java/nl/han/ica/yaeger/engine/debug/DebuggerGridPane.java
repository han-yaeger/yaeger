package nl.han.ica.yaeger.engine.debug;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

/**
 * Extends the JavaFX {@link GridPane} and sets the relevant properties.
 */
class DebuggerGridPane extends GridPane {

    DebuggerGridPane() {
        setBackground(Background.EMPTY);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        setPadding(new Insets(10, 10, 10, 10));
        setHgap(5);
        setHgap(2);
        setMinWidth(120);
        setVisible(false);
    }
}
