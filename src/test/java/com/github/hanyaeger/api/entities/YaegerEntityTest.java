package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.core.ViewOrders;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.EntityProcessor;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YaegerEntityTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    private static final double SCENE_WIDTH = 37d;
    private static final double SCENE_HEIGHT = 42d;
    private static final double ENTITY_WIDTH = 200d;
    private static final double ENTITY_HEIGHT = 100d;

    private YaegerEntityImpl sut;
    private Node node;
    private Node otherNode;
    private Injector injector;
    private Scene scene;
    private Pane pane;

    @BeforeEach
    void setup() {
        sut = new YaegerEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        otherNode = mock(Node.class, withSettings().withoutAnnotations());

        sut.setNode(Optional.of(node));
        scene = mock(Scene.class);
        pane = mock(Pane.class);

        var boundingBox = mock(BoundingBox.class);

        when(node.getBoundsInLocal()).thenReturn(boundingBox);
        when(node.localToScene(boundingBox, true)).thenReturn(boundingBox);
        when(boundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(boundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(boundingBox.getMinX()).thenReturn(LOCATION.getX());
        when(boundingBox.getMinY()).thenReturn(LOCATION.getY());
        when(node.getScene()).thenReturn(scene);
        when(pane.getWidth()).thenReturn(SCENE_WIDTH);
        when(pane.getHeight()).thenReturn(SCENE_HEIGHT);
        sut.setRootPane(pane);
    }

    @Test
    void getAnchorLocationReturnsAnchorLocation() {
        // Arrange

        // Act
        var actual = sut.getAnchorLocation();

        // Assert
        assertEquals(LOCATION, actual);
    }

    @Test
    void setAnchorLocationSetsAnchorLocation() {
        // Arrange
        var expected = new Coordinate2D(3.7, 4.2);

        sut.setAnchorLocation(expected);
        // Act
        var actual = sut.getAnchorLocation();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void setAnchorLocationXSetsAnchorLocation() {
        // Arrange
        var expected = 28;

        sut.setAnchorLocationX(expected);
        // Act
        var actual = sut.getAnchorLocation();

        // Assert
        assertEquals(expected, actual.getX());
    }

    @Test
    void setAnchorLocationYSetsAnchorLocation() {
        // Arrange
        var expected = 82;

        // Act
        sut.setAnchorLocationY(expected);
        var actual = sut.getAnchorLocation();

        // Assert
        assertEquals(expected, actual.getY());
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Arrange

        // Act
        List<Timer> timers = sut.getTimers();

        // Assert
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    void initCallsSetViewOrderWithDefaultValueIfNoViewOrderHasBeenSet() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(node).setViewOrder(ViewOrders.VIEW_ORDER_ENTITY_DEFAULT);
    }

    @Test
    void initCallsSetViewOrderWithSetValue() {
        // Arrange
        var expected = 42D;
        sut.setViewOrder(expected);

        // Act
        sut.init(injector);

        // Assert
        verify(node).setViewOrder(expected);
    }

    @Test
    void getViewOrderReturnsSetViewOrderBeforeNodeIsSet() {
        // Arrange
        sut.setNode(Optional.empty());

        var expected = 42D;
        sut.setViewOrder(expected);

        // Act
        var actual = sut.getViewOrder();

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void initCallsSetCursorIfCursorIsSetBeforeInit() {
        // Arrange
        var expected = Cursor.CROSSHAIR;
        sut.setCursor(expected);

        // Act
        sut.init(injector);

        // Assert
        verify(scene).setCursor(expected);
    }

    @Test
    void setCursorDelegatedToSceneIfInitAlreadyCalled() {
        // Arrange
        var expected = Cursor.CROSSHAIR;
        sut.init(injector);

        // Act
        sut.setCursor(expected);

        // Assert
        verify(scene).setCursor(expected);
    }

    @Test
    void getCursorReturnsNullIfInitNotYetCalled() {
        // Arrange

        // Act
        var actual = sut.getCursor();

        // Assert
        assertNull(actual);
    }

    @Test
    void getCursorDelegatedToSceneIfInitAlreadyCalled() {
        // Arrange
        var expected = Cursor.CROSSHAIR;
        sut.init(injector);
        when(scene.getCursor()).thenReturn(expected);

        // Act
        var actual = sut.getCursor();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getOpacityReturnsInitialOpacityIfNodeIsNotSet() {
        // Arrange
        sut = new YaegerEntityImpl(LOCATION);

        // Act
        var actual = sut.getOpacity();

        // Assert
        assertEquals(YaegerEntity.DEFAULT_OPACITY, actual);
    }

    @Test
    void getOpacityReturnsOpacityIfNodeIsNotSet() {
        // Arrange
        var expected = 0.37;

        sut.setNode(Optional.empty());
        sut.setOpacity(expected);

        // Act
        var actual = sut.getOpacity();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getOpacityReturnsOpacityFromNodeIfNodeIsSet() {
        // Arrange
        var expected = 0.37;
        when(node.getOpacity()).thenReturn(expected);
        sut.init(injector);

        // Act
        var actual = sut.getOpacity();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void initCallsSetOpacity() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(node).setOpacity(1);
    }

    @Test
    void initCallsSetVisible() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(node).setVisible(true);
    }

    @Test
    void getVisibleReturnsDefaultIfNodeAndVisibilityNotSet() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        var actual = sut.isVisible();

        // Assert
        assertEquals(YaegerEntity.DEFAULT_VISIBILITY, actual);
    }

    @Test
    void getVisibleReturnsSetValueIfNodeNotSet() {
        // Arrange
        sut.setVisible(false);

        // Act
        var actual = sut.isVisible();

        // Assert
        assertFalse(actual);
    }

    @Test
    void getVisibleReturnsValueFromNode() {
        // Arrange
        sut.init(injector);
        when(node.isVisible()).thenReturn(false);

        // Act
        var actual = sut.isVisible();

        // Assert
        assertFalse(actual);
    }

    @Test
    void transferCoordinatesToNodeCallsSetXWithInitialLocation() {
        // Arrange
        sut.init(injector);

        // Act
        sut.transferCoordinatesToNode();

        // Assert
        assertEquals(LOCATION.getX(), sut.getAnchorLocation().getX());
    }

    @Test
    void transferCoordinatesToNodeCallsSetYWithInitialLocation() {
        // Arrange
        sut.init(injector);

        // Act
        sut.transferCoordinatesToNode();

        // Assert
        assertEquals(LOCATION.getY(), sut.getAnchorLocation().getY());
    }

    @Test
    void setVisibleDelegatesToNode() {
        // Arrange

        // Act
        sut.setVisible(false);

        // Assert
        verify(node).setVisible(false);
    }

    @Test
    void removeCallsSetVisibleFalseOnGameNode() {
        // Arrange

        // Act
        sut.remove();

        // Assert
        verify(node).setVisible(false);
    }

    @Test
    void setAnchorPointBeforeNodeIsSetStoresAnchorPoint() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        // Assert
        assertEquals(AnchorPoint.CENTER_RIGHT, sut.getAnchorPoint());
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        // Assert
        verify(node).setTranslateX(0);
        verify(node).setTranslateY(0);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForLEFT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_LEFT);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForCENTER_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForRIGHT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void attachEventListenerDelegatesToNodeAfterInit() {
        // Arrange
        var eventType = mock(EventType.class);
        var eventHandler = mock(EventHandler.class);

        // Act
        sut.attachEventListener(eventType, eventHandler);

        // Assert
        verify(node).addEventHandler(eventType, eventHandler);
    }

    @Test
    void attachEventListenerDelegatesIsBufferedBeforeInit() {
        // Arrange
        var eventType = mock(EventType.class);
        var eventHandler = mock(EventHandler.class);

        // Act
        sut.attachEventListener(eventType, eventHandler);
        sut.init(injector);

        // Assert
        verify(node).addEventHandler(eventType, eventHandler);
    }

    @Test
    void getSceneWidthReturnsSceneWidthFromNode() {
        // Arrange

        // Act
        double actual = sut.getSceneWidth();

        // Assert
        assertEquals(SCENE_WIDTH, actual);
    }

    @Test
    void getSceneHeightReturnsSceneHeightFromNode() {
        // Arrange

        // Act
        double actual = sut.getSceneHeight();

        // Assert
        assertEquals(SCENE_HEIGHT, actual);
    }

    @Test
    void addToEntityCollectionCallsAddStaticEntity() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);

        // Act
        sut.addToEntityCollection(entityCollection);

        // Assert
        verify(entityCollection).addStaticEntity(sut);
    }

    @Test
    void distanceToNullCoordinate2DThrowsNullPointerException() {
        // Arrange
        Coordinate2D other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sut.distanceTo(other));
    }

    @Test
    void distanceToNullEntityThrowsNullPointerException() {
        // Arrange
        YaegerEntity other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sut.distanceTo(other));
    }

    @Test
    void distanceToSelfReturns0() {
        // Arrange
        var expected = 0d;

        // Act
        var actual = sut.distanceTo(sut);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void verticalDistanceToCoordinate2DIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new Coordinate2D(LOCATION.getX(), LOCATION.getY() + expected);
        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void horizontalDistanceToCoordinate2DIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new Coordinate2D(LOCATION.getX() + expected, LOCATION.getY());

        sut.transferCoordinatesToNode();
        sut.applyTranslationsForAnchorPoint();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void verticalDistanceToEntityIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + expected, LOCATION.getY() + expected));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.localToScene(otherBoundingBox, true)).thenReturn(otherBoundingBox);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
        when(otherBoundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(otherBoundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(otherBoundingBox.getMinX()).thenReturn(LOCATION.getX());
        when(otherBoundingBox.getMinY()).thenReturn(LOCATION.getY() + expected);
        when(otherNode.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        other.init(injector);
        other.transferCoordinatesToNode();

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void horizontalDistanceToEntityIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + expected, LOCATION.getY()));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.localToScene(otherBoundingBox, true)).thenReturn(otherBoundingBox);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
        when(otherBoundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(otherBoundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(otherBoundingBox.getMinX()).thenReturn(LOCATION.getX() + expected);
        when(otherBoundingBox.getMinY()).thenReturn(LOCATION.getY());
        when(otherNode.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        other.init(injector);
        other.transferCoordinatesToNode();

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToSelfIs0() {
        // Arrange
        var expected = 0d;

        // Act
        var actual = sut.angleTo(sut);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void angleToOwnCoordinatesIs0() {
        // Arrange
        var expected = 0d;
        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(LOCATION);

        // Assert
        assertEquals(actual, expected);
    }

    @Test
    void angleToNullCoordinate2DThrowsNullPointerException() {
        // Arrange
        Coordinate2D other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sut.angleTo(other));
    }

    @Test
    void angleToNullYaegerEntityThrowsNullPointerException() {
        // Arrange
        YaegerEntity other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sut.angleTo(other));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAngleTo")
    void testAngleToOtherEntity(Coordinate2D otherLocation, double expectedAngle) {
        // Arrange
        var other = new YaegerEntityImpl(otherLocation);
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.localToScene(otherBoundingBox, true)).thenReturn(otherBoundingBox);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
        when(otherBoundingBox.getMinX()).thenReturn(otherLocation.getX());
        when(otherBoundingBox.getMinY()).thenReturn(otherLocation.getY());
        when(otherBoundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(otherBoundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(otherNode.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        other.init(injector);
        other.transferCoordinatesToNode();

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(other);

        // Assert
        assertEquals(expectedAngle, actual);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAngleTo")
    void testAngleToOtherLocation(final Coordinate2D otherLocation, final double expectedAngle) {
        // Arrange
        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(otherLocation);

        // Assert
        assertEquals(expectedAngle, actual);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForGetLocationInScene")
    void testGetLocationInScene(AnchorPoint anchorPoint, double expectedX, double expectedY) {
        // Arrange
        var boundingBox = new BoundingBox(LOCATION.getX(), LOCATION.getY(), ENTITY_WIDTH, ENTITY_HEIGHT);
        when(node.localToScene(boundingBox, true)).thenReturn(boundingBox);
        when(node.getBoundsInLocal()).thenReturn(boundingBox);
        sut.setAnchorPoint(anchorPoint);
        var expected = new Coordinate2D(expectedX, expectedY);

        // Act
        var actual = sut.getLocationInScene();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void applyEntityProcessorCallsProcessOnProcessor() {
        // Arrange
        var entityProcessor = new EntityProcessor() {
            private YaegerEntity processedEntity;

            @Override
            public void process(YaegerEntity yaegerEntity) {
                processedEntity = yaegerEntity;
            }
        };

        // Act
        sut.applyEntityProcessor(entityProcessor);

        // Assert
        Assertions.assertEquals(sut, entityProcessor.processedEntity);
    }

    @Test
    void addToParentCallsProcessOnProcessor() {
        // Arrange
        var entityProcessor = new EntityProcessor() {
            private YaegerEntity processedEntity;

            @Override
            public void process(YaegerEntity yaegerEntity) {
                processedEntity = yaegerEntity;
            }
        };

        // Act
        sut.addToParent(entityProcessor);

        // Assert
        Assertions.assertEquals(sut, entityProcessor.processedEntity);
    }

    private static Stream<Arguments> provideArgumentsForAngleTo() {
        return Stream.of(
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 10), Direction.UP.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 10), Direction.DOWN.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY()), Direction.LEFT.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY()), Direction.RIGHT.getValue()),
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() - 10), 225d), // left above
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() - 10), 135d), // right above
                Arguments.of(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() + 10), 315d), // left below
                Arguments.of(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() + 10), 45d) // right below

        );
    }

    private static Stream<Arguments> provideArgumentsForGetLocationInScene() {
        return Stream.of(
                Arguments.of(AnchorPoint.TOP_LEFT, LOCATION.getX(), LOCATION.getY()),
                Arguments.of(AnchorPoint.TOP_CENTER, LOCATION.getX() + ENTITY_WIDTH / 2, LOCATION.getY()),
                Arguments.of(AnchorPoint.TOP_RIGHT, LOCATION.getX() + ENTITY_WIDTH, LOCATION.getY()),
                Arguments.of(AnchorPoint.CENTER_LEFT, LOCATION.getX(), LOCATION.getY() + ENTITY_HEIGHT / 2),
                Arguments.of(AnchorPoint.CENTER_CENTER, LOCATION.getX() + ENTITY_WIDTH / 2, LOCATION.getY() + ENTITY_HEIGHT / 2),
                Arguments.of(AnchorPoint.CENTER_RIGHT, LOCATION.getX() + ENTITY_WIDTH, LOCATION.getY() + ENTITY_HEIGHT / 2),
                Arguments.of(AnchorPoint.BOTTOM_LEFT, LOCATION.getX(), LOCATION.getY() + ENTITY_HEIGHT),
                Arguments.of(AnchorPoint.BOTTOM_CENTER, LOCATION.getX() + ENTITY_WIDTH / 2, LOCATION.getY() + ENTITY_HEIGHT),
                Arguments.of(AnchorPoint.BOTTOM_RIGHT, LOCATION.getX() + ENTITY_WIDTH, LOCATION.getY() + ENTITY_HEIGHT)
        );
    }

    private static class YaegerEntityImpl extends YaegerEntity {

        private Optional<Node> node = Optional.empty();

        public YaegerEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }

        @Override
        public Optional<? extends Node> getNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }
    }
}
