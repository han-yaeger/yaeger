package com.github.hanyaeger.core;

import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * All constants that are related to the view order of any {@link javafx.scene.Node} that is to be part of a
 * {@link com.github.hanyaeger.api.YaegerGame}.
 */
public class ViewOrders {

    /**
     * A private constructor to prevent instantiation.
     */
    private ViewOrders() {
    }

    /**
     * Since the debugger should be visible in all cases, it should have an exceptionally low view order.
     */
    public static final int VIEW_ORDER_DEBUGGER = 1;


    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity}.
     */
    public static final double VIEW_ORDER_ENTITY_STICKY = 10;

    /**
     * In case of a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene}, the {@link javafx.scene.control.ScrollPane}
     * should be placed behind the {@link javafx.scene.layout.Pane} that is to be used for sticky instances of {@link javafx.scene.Node}.
     */
    public static final int VIEW_ORDER_STICKYPANE = 1;

    /**
     * In case of a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene}, the {@link javafx.scene.control.ScrollPane}
     * should be placed behind the {@link javafx.scene.layout.Pane} that is to be used for sticky instances of {@link javafx.scene.Node}.
     */
    public static final int VIEW_ORDER_SCROLLPANE = 37;

    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity} that are
     * part of a {@link com.github.hanyaeger.api.scenes.TileMap}. These will, by default be behind the
     * {@link YaegerEntity} that are not part of a {@link com.github.hanyaeger.api.scenes.TileMap}.
     */
    public static final double VIEW_ORDER_ENTITY_DEFAULT_BEHIND = 100;

    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity}.
     */
    public static final double VIEW_ORDER_ENTITY_DEFAULT = 37;
}
