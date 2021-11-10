package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.exceptions.YaegerLifecycleException;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A {@code ScrollableDynamicScene} has exactly the same behaviour as a {@link DynamicScene}, but adds the option
 * to differ between the viewable area and the actual window size. The viewable are can be set with the method
 * {@link #setSize(Size)}, which should be called from the lifecycle method {@link #setupScene()}.
 * <p>
 * The main area of a {@code ScrollableDynamicScene} is scrollable and all instances of {@link YaegerEntity} are added
 * to this scrollable area. However, as additional to the {@link DynamicScene}, a {@code ScrollableDynamicScene} exposes
 * an overloaded {@link #addEntity(YaegerEntity, boolean)}, which accepts a second parameter. By setting this to {@code true},
 * the {@link YaegerEntity} is added to a non-scrollable layer, which is placed above the scrollable layer.
 */
public abstract class ScrollableDynamicScene extends DynamicScene {

    private EntitySupplier viewPortEntitySupplier;

    private StackPane stackPane;
    private ScrollPane scrollPane;
    private Pane stickyPane;

    private double horizontalScrollPosition = 0D;
    private double verticalScrollPosition = 0D;

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

    public void setHorizontalScrollPosition(final double horizontalScrollPosition) {
        if (scrollPane == null) {
            this.horizontalScrollPosition = horizontalScrollPosition;
        } else {
            scrollPane.setHvalue(horizontalScrollPosition);
        }
    }

    public void setVerticalScrollPosition(final double verticalScrollPosition) {
        if (scrollPane == null) {
            this.verticalScrollPosition = verticalScrollPosition;
        } else {
            scrollPane.setVvalue(verticalScrollPosition);
        }
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
    public double getHorizontalScrollPosition() {
        if (scrollPane == null) {
            return horizontalScrollPosition;
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
    public double getVerticalScrollPosition() {
        if (scrollPane == null) {
            return verticalScrollPosition;
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
     * scrollable scene, not only the part that is visible (the so-called viewport).
     *
     * @return the width of the complete scene, as a {@code double}
     */
    @Override
    public double getWidth() {
        return pane.getPrefWidth();
    }

    /**
     * Return the height of the complete scene. Note that this concerns the complete area of the
     * scrollable scene, not only the part that is visible (the so-called viewport).
     *
     * @return the height of the complete scene, as a {@code double}
     */
    @Override
    public double getHeight() {
        return pane.getPrefHeight();
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
