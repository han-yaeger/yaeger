package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.Updater;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.core.factories.EntityCollectionFactory;
import com.github.hanyaeger.core.factories.PaneFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.github.hanyaeger.core.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.core.scenes.delegates.KeyListenerDelegate;
import com.google.inject.Injector;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxToolkit.registerPrimaryStage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScrollableDynamicSceneTest extends FxRobot {

    private ScrollableDynamicSceneImpl sut;

    private Debugger debugger;

    private EntitySupplier entitySupplier;
    private EntitySupplier viewPortEntitySupplier;

    private Pane defaultPane;
    private Pane stickyPane;
    private static ScrollPane scrollPane;
    private StackPane stackPane;
    private ObservableList<Node> stackPaneChildren;

    private YaegerConfig config;
    private Scene scene;

    @BeforeAll
    static void beforeAll() throws TimeoutException {

        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();

        scrollPane = mock(ScrollPane.class, withSettings());
    }

    @BeforeEach
    void setup() {
        sut = new ScrollableDynamicSceneImpl();

        defaultPane = mock(Pane.class);
        stickyPane = mock(Pane.class);
        stackPane = mock(StackPane.class);
        reset(scrollPane);

        stackPaneChildren = mock(ObservableList.class);

        var backgroundDelegate = mock(BackgroundDelegate.class);
        var keyListenerDelegate = mock(KeyListenerDelegate.class);
        entitySupplier = mock(EntitySupplier.class);
        viewPortEntitySupplier = mock(EntitySupplier.class);
        var sceneFactory = mock(SceneFactory.class);
        debugger = mock(Debugger.class);
        var animationTimer = mock(AnimationTimer.class);
        var entityCollectionFactory = mock(EntityCollectionFactory.class);
        var animationTimerFactory = mock(AnimationTimerFactory.class);
        var paneFactory = mock(PaneFactory.class);
        var injector = mock(Injector.class);
        var updater = mock(Updater.class);
        config = mock(YaegerConfig.class);
        var stage = mock(Stage.class);
        scene = mock(Scene.class);
        var entityCollection = mock(EntityCollection.class);

        when(sceneFactory.create(defaultPane)).thenReturn(scene);
        when(entityCollectionFactory.create(config)).thenReturn(entityCollection);
        when(animationTimerFactory.create(any(), eq(false))).thenReturn(animationTimer);

        when(paneFactory.createPane()).thenReturn(defaultPane, stickyPane);
        when(paneFactory.createScrollPane()).thenReturn(scrollPane);
        when(paneFactory.createStackPane()).thenReturn(stackPane);

        when(stackPane.getChildren()).thenReturn(stackPaneChildren);

        when(stage.getScene()).thenReturn(scene);

        sut.setDebugger(debugger);
        sut.setSceneFactory(sceneFactory);
        sut.setEntityCollectionFactory(entityCollectionFactory);
        sut.setPaneFactory(paneFactory);
        sut.setBackgroundDelegate(backgroundDelegate);
        sut.setKeyListenerDelegate(keyListenerDelegate);
        sut.setEntitySupplier(entitySupplier);
        sut.setViewPortEntitySupplier(viewPortEntitySupplier);
        sut.setViewPortEntitySupplier(viewPortEntitySupplier);
        sut.setAnimationTimerFactory(animationTimerFactory);
        sut.setUpdater(updater);
        sut.setStage(stage);
        sut.setConfig(config);

        sut.init(injector);
    }

    @Test
    void scrollPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(scrollPane).setViewOrder(ViewOrders.VIEW_ORDER_SCROLLPANE);
        verify(scrollPane).setFitToWidth(true);
        verify(scrollPane).setFitToHeight(true);
        verify(scrollPane).setContent(defaultPane);
    }

    @Test
    void stickyPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(stickyPane).setViewOrder(ViewOrders.VIEW_ORDER_STICKYPANE);
        verify(stickyPane).setPickOnBounds(false);
    }

    @Test
    void stackPaneInitializedCorrectly() {
        // Arrange

        // Act

        // Assert
        verify(stackPane).setAlignment(Pos.TOP_LEFT);
        verify(stackPaneChildren).add(scrollPane);
        verify(stackPaneChildren).add(stickyPane);
    }

    @Test
    void getRootPaneReturnsDefaultPane() {
        // Arrange

        // Act

        // Assert
        assertEquals(defaultPane, sut.getRootPane());
    }

    @Test
    void getPaneForDebuggerReturnsStickyPane() {
        // Arrange

        // Act
        var actual = sut.getPaneForDebugger();

        // Assert
        assertEquals(stickyPane, actual);
    }

    @Test
    void getSceneReturnsSceneFromScrollPane() {
        // Arrange
        when(scrollPane.getScene()).thenReturn(scene);

        // Act
        var actual = sut.getScene();

        // Assert
        assertEquals(scene, actual);
    }

    @Test
    void postActivateSetsPaneOnViewPortEntitySupplier() {
        // Arrange
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(viewPortEntitySupplier).setPane(stickyPane);
    }

    @Test
    void postActivateSetsGameDimensionsOnDebuggerIfDebugIsTrue() {
        // Arrange
        when(config.showDebug()).thenReturn(true);
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(debugger).setGameDimensions(any());
    }

    @Test
    void postActivateDisablesScrollingByDefault() {
        // Arrange
        when(config.enableScroll()).thenReturn(false);
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(scrollPane).addEventFilter(eq(ScrollEvent.SCROLL), any());
    }

    @Test
    void postActivateWithEnableScrollDoesNotDisableScrolling() {
        // Arrange
        when(config.enableScroll()).thenReturn(true);
        sut.activate();

        // Act
        sut.postActivate();

        // Assert
        verify(scrollPane, never()).addEventFilter(eq(ScrollEvent.SCROLL), any());
    }

    @Test
    void getWidthReturnsWidthOfDefaultPane() {
        // Arrange

        // Act
        sut.getWidth();

        // Assert
        verify(defaultPane).getPrefWidth();
    }

    @Test
    void getHeightReturnsHeightOfDefaultPane() {
        // Arrange

        // Act
        sut.getHeight();

        // Assert
        verify(defaultPane).getPrefHeight();
    }

    @Test
    void getViewPortWidthReturnsWidthOfStage() {
        // Arrange

        // Act
        sut.getViewportWidth();

        // Assert
        verify(scene).getWidth();
    }

    @Test
    void getViewPortHeightReturnsHeightOfStage() {
        // Arrange

        // Act
        sut.getViewportHeight();

        // Assert
        verify(scene).getHeight();
    }

    @Test
    void setRelativeScrollPositionDelegatesToPane() {
        // Arrange
        var expected = new Point2D(0.37, 0.42);

        // Act
        sut.setRelativeScrollPosition(expected.getX(), expected.getY());

        // Assert
        verify(scrollPane).setHvalue(expected.getX());
        verify(scrollPane).setVvalue(expected.getY());
    }

    @Test
    void setScrollPositionDelegatesToPane(){
        // Arrange
        var coordinate2D = new Coordinate2D(500, 500);
        var expected = new Point2D(0.5, 0.5);
        when(defaultPane.getPrefHeight()).thenReturn(1000D);
        when(defaultPane.getPrefWidth()).thenReturn(1000D);

        // Act
        sut.setScrollPosition(coordinate2D);

        // Assert
        verify(scrollPane).setHvalue(expected.getX());
        verify(scrollPane).setVvalue(expected.getY());
    }

    @Test
    void setHorizontalRelativeScrollPositionDelegatesToPane() {
        // Arrange
        var expected = 0.37D;

        // Act
        sut.setHorizontalRelativeScrollPosition(expected);

        // Assert
        verify(scrollPane).setHvalue(expected);
    }

    @Test
    void setHorizontalScrollPositionDelegatesToPane() {
        // Arrange
        when(defaultPane.getPrefWidth()).thenReturn(1000D);

        // Act
        sut.setHorizontalScrollPosition(500D);

        // Assert
        verify(scrollPane).setHvalue(0.5);
    }

    @Test
    void getHorizontalScrollPositionDelegatesToPane() {
        // Arrange

        // Act
        sut.getHorizontalRelativeScrollPosition();

        // Assert
        verify(scrollPane).getHvalue();
    }

    @Test
    void setVerticalRelativeScrollPositionDelegatesToPane() {
        // Arrange
        var expected = 0.37D;

        // Act
        sut.setVerticalRelativeScrollPosition(expected);

        // Assert
        verify(scrollPane).setVvalue(expected);
    }

    @Test
    void setVerticalScrollPositionDelegatesToPane() {
        // Arrange
        when(defaultPane.getPrefHeight()).thenReturn(1000D);

        // Act
        sut.setVerticalScrollPosition(500D);

        // Assert
        verify(scrollPane).setVvalue(0.5);
    }

    @Test
    void getVerticalScrollPositionDelegatesToPane() {
        // Arrange

        // Act
        sut.getVerticalRelativeScrollPosition();

        // Assert
        verify(scrollPane).getVvalue();
    }

    @Test
    void setSizeWithZeroValuesDoesNoting() {
        // Arrange
        var size = new Size(0, 0);
        verify(scrollPane).setViewOrder(ViewOrders.VIEW_ORDER_SCROLLPANE);
        verify(scrollPane).setFitToHeight(true);
        verify(scrollPane).setFitToWidth(true);
        verify(scrollPane).setContent(defaultPane);

        // Act
        sut.setSize(size);

        // Assert
        verifyNoMoreInteractions(scrollPane);
    }

    @Test
    void setSizeWithNonZeroWidthSetsCorrectValuesOnScrollPane() {
        // Arrange
        var size = new Size(37, 0);

        // Act
        sut.setSize(size);

        // Assert
        verify(scrollPane).setFitToWidth(false);
        verify(scrollPane).setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Test
    void setSizeWithNonZeroHeightSetsCorrectValuesOnScrollPane() {
        // Arrange
        var size = new Size(0, 37);

        // Act
        sut.setSize(size);

        // Assert
        verify(scrollPane).setFitToHeight(false);
        verify(scrollPane).setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Test
    void setSizeWithNonZeroWidthSetsCorrectValuesOnPane() {
        // Arrange
        var size = new Size(37, 0);

        // Act
        sut.setSize(size);

        // Assert
        verify(defaultPane).setPrefWidth(size.width());
    }

    @Test
    void setSizeWithNonZeroHeightSetsCorrectValuesOnPane() {
        // Arrange
        var size = new Size(0, 37);

        // Act
        sut.setSize(size);

        // Assert
        verify(defaultPane).setPrefHeight(size.height());
    }

    @Test
    void addEntityAddsTheEntitySupplier() {
        // Arrange
        sut.activate();

        var testEntity = mock(YaegerEntity.class);

        // Act
        sut.addEntity(testEntity);

        // Verify
        verify(entitySupplier).add(testEntity);
    }

    @Test
    void addNonStickyEntityAddsTheEntitySupplier() {
        // Arrange
        sut.activate();

        var testEntity = mock(YaegerEntity.class);

        // Act
        sut.addEntity(testEntity, false);

        // Verify
        verify(entitySupplier).add(testEntity);
    }

    @Test
    void addStickyEntitySetsViewOrder() {
        // Arrange
        sut.activate();

        var testEntity = mock(YaegerEntity.class);

        // Act
        sut.addEntity(testEntity, true);

        // Verify
        verify(testEntity).setViewOrder(ViewOrders.VIEW_ORDER_ENTITY_STICKY);
    }

    @Test
    void addStickyEntityAddsTheEntitySupplier() {
        // Arrange
        sut.activate();

        var testEntity = mock(YaegerEntity.class);

        // Act
        sut.addEntity(testEntity, true);

        // Verify
        verify(viewPortEntitySupplier).add(testEntity);
    }

    private static class ScrollableDynamicSceneImpl extends ScrollableDynamicScene {

        @Override
        public void setupScene() {
            // Not required here
        }

        @Override
        public void setupEntities() {
            // Not required here
        }
    }
}
