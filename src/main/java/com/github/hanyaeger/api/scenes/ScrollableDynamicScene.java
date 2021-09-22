package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Inject;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class ScrollableDynamicScene extends DynamicScene {

    private StackPane stackPane;
    private ScrollPane scrollPane;

    /**
     * Set the {@link Size} (e.g. the width and height) of the scrollable area of the {@link YaegerScene}. By default,
     * the with and height are set to the width and height of the {@link com.github.hanyaeger.api.YaegerGame}.
     * <p>
     * TODO test and document
     *
     * @param size
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

    @Override
    public void postActivate() {
        super.postActivate();
        if (config.showDebug()) {
            debugger.setGameDimensions(new Size(pane.getPrefWidth(), pane.getPrefHeight()));
        }
    }

    @Override
    Pane getPaneForDebugger() {
        return stackPane;
    }

    @Override
    @Inject
    public void setPaneFactory(final PaneFactory paneFactory) {
        this.pane = paneFactory.createPane();
        this.stackPane = paneFactory.createStackPane();
        this.scrollPane = paneFactory.createScrollPane();

        stackPane.getChildren().add(scrollPane);
        stackPane.setAlignment(Pos.TOP_LEFT);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        scrollPane.setViewOrder(ViewOrders.VIEW_ORDER_SCROLLPANE);
    }

    @Override
    void createJavaFXScene(final SceneFactory sceneFactory) {
        scene = sceneFactory.create(stackPane);
    }

    @Override
    public Scene getScene() {
        return scrollPane.getScene();
    }
}
