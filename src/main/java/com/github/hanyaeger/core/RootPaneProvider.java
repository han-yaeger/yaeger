package com.github.hanyaeger.core;

import javafx.scene.layout.Pane;

/**
 * A {@code RootPaneProvider} is capable of providing the {@link javafx.scene.layout.Pane} that
 * should be used as the root pane for all other children of {@link javafx.scene.Node} that are
 * part of the {@link com.github.hanyaeger.api.scenes.YaegerScene}.
 */
public interface RootPaneProvider {

    /**
     * Return the root pane.
     *
     * @return an instance of {@link Pane}
     */
    Pane getRootPane();
}
