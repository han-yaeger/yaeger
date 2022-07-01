package com.github.hanyaeger.core.factories.debug;

import com.github.hanyaeger.core.entities.Debugger;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

/**
 * A {@code DebugGridPaneFactory} should be used for creating instances of {@link GridPane} for use in the
 * {@link Debugger}.
 */
public class DebugGridPaneFactory {

    /**
     * Construct a new {@link GridPane} that can be used by the {@link Debugger}.
     *
     * @return the {@link GridPane} that can be used by this {@link Debugger}
     */
    public GridPane create() {
        var gridPane = new GridPane();
        gridPane.setBackground(Background.EMPTY);
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setHgap(2);
        gridPane.setMinWidth(120);
        gridPane.setMouseTransparent(true);
        return gridPane;
    }
}
