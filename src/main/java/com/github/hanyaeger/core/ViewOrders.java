package com.github.hanyaeger.core;

import com.github.hanyaeger.api.entities.YaegerEntity;

/**
 * All constants that are related to the view order of any {@link javafx.scene.Node} that is to be part of a
 * {@link com.github.hanyaeger.api.YaegerGame}.
 */
public class ViewOrders {
    /**
     * Since the debugger should be visible in all cases, it should have an exceptionally low view order.
     */
    public static final int VIEW_ORDER_DEBUGGER = 1;


    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity}.
     */
    public static final double VIEW_ORDER_ENTITY_STICKY = 10D;

    /**
     * In case of a {@link com.github.hanyaeger.api.scenes.ScrollableDynamicScene}, the
     * {@link com.github.hanyaeger.core.entities.Debugger} is placed on the same underlying
     * {@link javafx.scene.layout.StackPane}, and should be behind the
     * {@link com.github.hanyaeger.core.entities.Debugger}.
     */
    public static final int VIEW_ORDER_SCROLLPANE = 37;

    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity} that are
     * part of a {@link com.github.hanyaeger.api.scenes.TileMap}. These will, by default be behind the
     * {@link YaegerEntity} that are not part of a {@link com.github.hanyaeger.api.scenes.TileMap}.
     */
    public static final double VIEW_ORDER_ENTITY_DEFAULT_BEHIND = 100D;

    /**
     * The default value for the viewOrder for instances of {@link YaegerEntity}.
     */
    public static final double VIEW_ORDER_ENTITY_DEFAULT = 37D;


    /**
     * The default value for the viewOrder for all instances of {@code BoundingBoxVisualizer}.
     */
    public static final double VIEW_ORDER_ENTITY_BOUNDINGBOX_VISUALIZER = 1D;
}
