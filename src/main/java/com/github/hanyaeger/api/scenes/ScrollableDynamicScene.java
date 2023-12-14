package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A {@code ScrollableDynamicScene} has exactly the same behaviour as a {@link DynamicScene}, but adds the option
 * to differ between the viewable area and the actual window size. The size of the viewable are can be set with the method
 * {@link #setSize(Size)}, which should be called from the lifecycle method {@link #setupScene()}.
 * <p>
 * The main area of a {@code ScrollableDynamicScene} is scrollable and all instances of {@link YaegerEntity} are added
 * to this scrollable area. However, additional to the {@link DynamicScene}, a {@code ScrollableDynamicScene} exposes
 * an overloaded {@link #addEntity(YaegerEntity, boolean)}, which accepts a second parameter. By setting this to {@code true},
 * the {@link YaegerEntity} is added to a non-scrollable layer, which is placed above the scrollable layer.
 * <p>
 * Since the size of the actual window size is usually much larger than the viewable area is, it is necessary set which
 * part can be seen. For this, the horizontal and vertical scroll position can be set, with both relative values
 * ({@link #setRelativeScrollPosition(double, double)}, or separately through {@link #setVerticalRelativeScrollPosition(double)}
 * and {@link #setHorizontalRelativeScrollPosition}) or absolute values ({@link #setScrollPosition(Coordinate2D)}, or separately
 * through {@link #setHorizontalScrollPosition(double)} and {@link #setVerticalScrollPosition(double)}).
 */
public abstract class ScrollableDynamicScene extends DynamicScene {

    private EntitySupplier viewPortEntitySupplier;

    private StackPane stackPane;
    private ScrollPane scrollPane;
    private Pane stickyPane;

    private Point2D scrollPosition = Point2D.ZERO;

    /**
     * Set the {@link Size} (e.g. the width and height) of the scrollable area of the {@link YaegerScene}. By default,
     * the with and height are set to the width and height of the {@link com.github.hanyaeger.api.YaegerGame}.
     *
     * @param size the {@link Size} of the scrollable area of the {@link YaegerScene}
     */
    protected void setSize(final Size size) {
        if (Double.compare(size.width(), 0) != 0) {
            scrollPane.setFitToWidth(false);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            pane.setPrefWidth(size.width());
        }
        if (Double.compare(size.height(), 0) != 0) {
            scrollPane.setFitToHeight(false);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            pane.setPrefHeight(size.height());
        }
    }

    /**
     * Set the horizontal scroll position of the scene to a relative value. A value of 0 (or smaller) will mean the viewport
     * is positioned on the utmost left of the scene. A value of 1 or greater places the viewport on the utmost right of
     * the scene.
     *
     * @param horizontalRelativeScrollPosition a {@code double} between 0 and 1. A {@code double} below 0 will be handled as 0
     *                                         and a {@code double} greater than 1 will be treated as 1.
     */
    public void setHorizontalRelativeScrollPosition(final double horizontalRelativeScrollPosition) {
        setRelativeScrollPosition(horizontalRelativeScrollPosition, scrollPosition.getY());
    }

    /**
     * Set the vertical scroll position of the scene to a relative value. A value of 0 (or smaller) will mean the viewport is
     * positioned on the top of the scene. A value of 1 or greater places the viewport on the bottom of the scene.
     *
     * @param verticalRelativeScrollPosition a {@code double} between 0 and 1. A {@code double} below 0 will be handled as 0
     *                                       and a {@code double} greater than 1 will be treated as 1.
     */
    public void setVerticalRelativeScrollPosition(final double verticalRelativeScrollPosition) {
        setRelativeScrollPosition(scrollPosition.getX(), verticalRelativeScrollPosition);
    }

    /**
     * Set the scroll position of the scene to the relative x and y-value provided. Both values should be between
     * 0 and 1. An  x-value of 0 (or smaller) will mean the viewport is positioned on the utmost left of
     * the scene. An x-value of 1 or greater places the viewport on the utmost right of the scene.
     * <p>
     * A y-value of 0 (or smaller) will mean the viewport is positioned on the top of the scene. A y-value of 1
     * or greater places the viewport on the bottom of the scene.
     *
     * @param horizontalRelativeScrollPosition a {@code double} between 0 and 1. A {@code double} below 0 will be handled as 0
     *                                         and a {@code double} greater than 1 will be treated as 1.
     * @param verticalRelativeScrollPosition   a {@code double} between 0 and 1. A {@code double} below 0 will be handled as 0
     *                                         and a {@code double} greater than 1 will be treated as 1.
     */
    public void setRelativeScrollPosition(final double horizontalRelativeScrollPosition, final double verticalRelativeScrollPosition) {
        this.scrollPosition = new Point2D(horizontalRelativeScrollPosition, verticalRelativeScrollPosition);

        if (scrollPane != null) {
            scrollPane.setHvalue(scrollPosition.getX());
            scrollPane.setVvalue(scrollPosition.getY());
        }
    }

    /**
     * Set the horizontal scroll position of the scene. A value of 0 (or smaller) will mean the viewport
     * is positioned on the utmost left of the scene. A value equal or greater than the width of the scene places the
     * viewport on the utmost right of the scene.
     *
     * @param horizontalScrollPosition a {@code double} between 0 and the width of the scene. A {@code double} below 0
     *                                 will be handled as  0 and a {@code double} greater than the width of the scene will
     *                                 be treated as the width of the scene.
     */
    public void setHorizontalScrollPosition(final double horizontalScrollPosition) {
        var relativeX = horizontalScrollPosition / pane.getPrefWidth();
        setHorizontalRelativeScrollPosition(relativeX);
    }

    /**
     * Set the vertical scroll position of the scene. A value of 0 (or smaller) will mean the viewport
     * is positioned on the utmost top of the scene. A value equal or greater than the height of the scene places the
     * viewport on the bottom of the scene.
     *
     * @param verticalScrollPosition a {@code double} between 0 and the height of the scene. A {@code double} below 0
     *                               will be handled as  0 and a {@code double} greater than the height of the scene will
     *                               be treated as the height of the scene.
     */
    public void setVerticalScrollPosition(final double verticalScrollPosition) {
        var relativeY = verticalScrollPosition / pane.getPrefHeight();
        setVerticalRelativeScrollPosition(relativeY);
    }

    /**
     * Set the scroll position of the scene centered on the given {@link Coordinate2D}.
     *
     * @param coordinate2D the {@link Coordinate2D} on which the {@code ScrollableDynamicScene} should be centered.
     */
    public void setScrollPosition(final Coordinate2D coordinate2D) {
        var relativeX = coordinate2D.getX() / pane.getPrefWidth();
        var relativeY = coordinate2D.getY() / pane.getPrefHeight();
        setRelativeScrollPosition(relativeX, relativeY);
    }

    /**
     * Return the current horizontal scroll position of the viewport. The {@code ScrollableDynamicScene} will update
     * this value whenever the viewport is scrolled.
     * <p>
     * The value will be a {@code double} between 0 and 1, where a 0 will mean the viewport is positioned
     * at the left of the scrollable area. A value of 1 will mean the viewport will be positioned at the end of the
     * scrollable area.
     *
     * @return a {@code double} between 0 and 1 (inclusive)
     */
    public double getHorizontalRelativeScrollPosition() {
        if (scrollPane == null) {
            return scrollPosition.getX();
        }
        return scrollPane.getHvalue();
    }

    /**
     * Return the current vertical scroll position of the viewport. The {@code ScrollableDynamicScene} will update
     * this value whenever the viewport is scrolled.
     * <p>
     * The value will be a {@code double} between 0 and 1, where a 0 will mean the viewport will be positioned
     * at the top of the scrollable area. A value of 1 will mean the viewport will be positioned at the end of the
     * scrollable area.
     *
     * @return a {@code double} between 0 and 1 (inclusive)
     */
    public double getVerticalRelativeScrollPosition() {
        if (scrollPane == null) {
            return scrollPosition.getY();
        }

        return scrollPane.getVvalue();
    }

    /**
     * Add an {@link YaegerEntity} to this {@link YaegerScene}.
     *
     * @param yaegerEntity     the {@link YaegerEntity} to be added
     * @param stickyOnViewport {@code true} if this {@link YaegerEntity} should be placed on the viewport, {@code false}
     *                         if this {@link YaegerEntity} should be part of the scrollable area.
     */
    protected void addEntity(final YaegerEntity yaegerEntity, final boolean stickyOnViewport) {
        if (!stickyOnViewport) {
            super.addEntity(yaegerEntity);
        } else {
            yaegerEntity.setViewOrder(ViewOrders.VIEW_ORDER_ENTITY_STICKY);
            viewPortEntitySupplier.add(yaegerEntity);
        }
    }

    @Override
    public void postActivate() {
        viewPortEntitySupplier.setPane(stickyPane);
        entityCollection.registerSupplier(viewPortEntitySupplier);

        super.postActivate();

        if (config.showDebug()) {
            debugger.setGameDimensions(new Size(pane.getPrefWidth(), pane.getPrefHeight()));
        }

        if (!config.enableScroll()) {
            scrollPane.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        }
    }

    /**
     * Return the width of the complete scene. Note that this concerns the complete area of the
     * scrollable scene, not only the part that is visible (the viewport).
     * <p>
     * If only the width of the viewport is required, use the method {@link #getViewportWidth()}
     *
     * @return the width of the complete scene, as a {@code double}
     */
    @Override
    public double getWidth() {
        return pane.getPrefWidth();
    }

    /**
     * Return the width of the viewport.
     * <p>
     * If the full width of the scene is required, use the method {@link #getWidth()}.
     *
     * @return the width of the viewport, as a {@code double}
     */
    public double getViewportWidth() {
        return super.getWidth();
    }

    /**
     * Return the height of the complete scene. Note that this concerns the complete area of the
     * scrollable scene, not only the part that is visible (the viewport).
     * <p>
     * If only the height of the viewport is required, use the method {@link #getViewportHeight()}
     *
     * @return the height of the complete scene, as a {@code double}
     */
    @Override
    public double getHeight() {
        return pane.getPrefHeight();
    }

    /**
     * Return the height of the viewport.
     * <p>
     * If the full height of the scene is required, use the method {@link #getHeight()}.
     *
     * @return the height of the viewport, as a {@code double}
     */
    public double getViewportHeight() {
        return super.getHeight();
    }

    @Override
    Pane getPaneForDebugger() {
        return stickyPane;
    }

    @Override
    @Inject
    public void setPaneFactory(final PaneFactory paneFactory) {
        this.pane = paneFactory.createPane();

        this.scrollPane = paneFactory.createScrollPane();
        scrollPane.setViewOrder(ViewOrders.VIEW_ORDER_SCROLLPANE);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);

        this.stickyPane = paneFactory.createPane();
        stickyPane.setViewOrder(ViewOrders.VIEW_ORDER_STICKYPANE);
        stickyPane.setPickOnBounds(false);

        this.stackPane = paneFactory.createStackPane();
        stackPane.getChildren().add(scrollPane);
        stackPane.getChildren().add(stickyPane);
        stackPane.setAlignment(Pos.TOP_LEFT);
    }

    @Override
    void createJavaFXScene(final SceneFactory sceneFactory) {
        scene = sceneFactory.create(stackPane);
    }

    @Override
    public Scene getScene() {
        return scrollPane.getScene();
    }

    /**
     * Set the {@link EntitySupplier} that should be used for the view port layer.
     *
     * @param viewPortEntitySupplier the {@link EntitySupplier} to be used
     */
    @Inject
    public void setViewPortEntitySupplier(final EntitySupplier viewPortEntitySupplier) {
        this.viewPortEntitySupplier = viewPortEntitySupplier;
    }
}
