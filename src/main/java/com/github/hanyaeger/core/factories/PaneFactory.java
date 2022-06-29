package com.github.hanyaeger.core.factories;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A {@code PaneFactory} can be used to create instances of {@link javafx.scene.layout.Pane} or
 * {@link javafx.scene.control.ScrollPane}.
 */
public class PaneFactory {

    /**
     * Return an instance of {@link Pane}.
     *
     * @return an instance of {@link Pane}
     */
    public Pane createPane() {
        return new Pane();
    }

    /**
     * Return an instance of {@link ScrollPane}.
     *
     * @return an instance of {@link ScrollPane}
     */
    public ScrollPane createScrollPane() {
        return new ScrollPane();
    }

    /**
     * Return an instance of {@link StackPane}.
     *
     * @return an instance of {@link StackPane}
     */
    public StackPane createStackPane() {
        return new StackPane();
    }
}
