package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.EntityProcessor;
import com.github.hanyaeger.api.engine.entities.entity.motion.Direction;
import com.google.inject.Injector;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YaegerEntityTest {

    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    private static final double SCENE_WIDTH = 37d;
    private static final double SCENE_HEIGHT = 42d;
    private static final double ENTITY_WIDTH = 200d;
    private static final double ENTITY_HEIGHT = 100d;
    private static final BoundingBox BOUNDING_BOX = new BoundingBox(0, 0, 10, 10);

    private YaegerEntityImpl sut;
    private Node node;
    private Injector injector;
    private BoundingBox boundingBox;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new YaegerEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(Optional.of(node));
        scene = mock(Scene.class);
        boundingBox = mock(BoundingBox.class);
        when(node.getBoundsInLocal()).thenReturn(boundingBox);
        when(boundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(boundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
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
        assertEquals(false, actual);
    }

    @Test
    void getVisibleReturnsValueFromNode() {
        // Arrange
        sut.init(injector);
        when(node.isVisible()).thenReturn(false);

        // Act
        var actual = sut.isVisible();

        // Assert
        assertEquals(false, actual);
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
    void setAnchorPointBedoreNodeIsSetStoresAnchorPoint() {
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
    void attachEventListenerDelegatesToNode() {
        // Arrange
        var eventType = mock(EventType.class);
        var eventHandler = mock(EventHandler.class);

        // Act
        sut.attachEventListener(eventType, eventHandler);

        // Assert
        verify(node).addEventHandler(eventType, eventHandler);
    }

    @Test
    void getSceneWidthReturnsSceneWidthFromNode() {
        // Arrange
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        // Act
        double actual = sut.getSceneWidth();

        // Assert
        assertEquals(SCENE_WIDTH, actual);
    }

    @Test
    void getSceneHeightReturnsSceneHeightFromNode() {
        // Arrange
        when(node.getScene()).thenReturn(scene);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

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
    void distanceToNullCoordinate2DThrowsNullpointerException() {
        // Arrange
        Coordinate2D other = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sut.distanceTo(other));
    }

    @Test
    void distanceToNullEntityThrowsNullpointerException() {
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
        assertTrue(Double.compare(actual, expected) == 0);
    }

    @Test
    void distanceToOtherOnSameLocationReturns0() {
        // Arrange
        var other = new YaegerEntityImpl(LOCATION);
        var expected = 0d;

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertTrue(Double.compare(actual, expected) == 0);
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
        assertTrue(Double.compare(actual, expected) == 0);
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
        assertTrue(Double.compare(actual, expected) == 0);
    }

    @Test
    void verticalDistanceToEntityIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX(), LOCATION.getY() + expected));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
        when(otherBoundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(otherBoundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(otherNode.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        other.init(injector);
        other.transferCoordinatesToNode();

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertTrue(Double.compare(actual, expected) == 0);
    }

    @Test
    void horizontalDistanceToEntityIsCorrect() {
        // Arrange
        var expected = 9d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + expected, LOCATION.getY()));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
        when(otherBoundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(otherBoundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(otherNode.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        other.init(injector);
        other.transferCoordinatesToNode();

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.distanceTo(other);

        // Assert
        assertTrue(Double.compare(actual, expected) == 0);
    }

    @Test
    void angleToSelfIs0() {
        // Arrange
        var expected = 0d;

        // Act
        var actual = sut.angleTo(sut);

        // Assert
        assertTrue(Double.compare(actual, expected) == 0);
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
        assertTrue(Double.compare(actual, expected) == 0);
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

    @Test
    void angleToYaegerEntityVerticallyAboveIs180Degrees() {
        // Arrange
        var expected = Direction.UP.getValue();
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 10));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityVerticallyBelowIs0Degrees() {
        // Arrange
        var expected = Direction.DOWN.getValue();
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 10));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityHorizontallyLeftIs270Degrees() {
        // Arrange
        var expected = Direction.LEFT.getValue();
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY()));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityHorizontallyRightIs90Degrees() {
        // Arrange
        var expected = Direction.RIGHT.getValue();
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY()));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityLeftAboveIs225Degrees() {
        // Arrange
        var expected = 225d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() - 10));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityRightAboveIs135Degrees() {
        // Arrange
        var expected = 135d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() - 10));

        other.init(injector);
        other.transferCoordinatesToNode();
        other.applyTranslationsForAnchorPoint();

        sut.transferCoordinatesToNode();
        sut.applyTranslationsForAnchorPoint();

        // Act
        var actual = sut.angleTo(other);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityLeftBelowIs315Degrees() {
        // Arrange
        var expected = 315d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() + 10));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToYaegerEntityRightBelowIs45Degrees() {
        // Arrange
        var expected = 45d;
        var other = new YaegerEntityImpl(new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() + 10));
        var otherNode = mock(Node.class, withSettings().withoutAnnotations());
        other.setNode(Optional.of(otherNode));
        var otherBoundingBox = mock(BoundingBox.class);
        when(otherNode.getBoundsInLocal()).thenReturn(otherBoundingBox);
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
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DVerticallyAboveIs180Degrees() {
        // Arrange
        var expected = Direction.UP.getValue();
        var above = new Coordinate2D(LOCATION.getX(), LOCATION.getY() - 10);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(above);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DVerticallyBelowIs0Degrees() {
        // Arrange
        var expected = Direction.DOWN.getValue();
        var below = new Coordinate2D(LOCATION.getX(), LOCATION.getY() + 10);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(below);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DHorizontallyLeftIs270Degrees() {
        // Arrange
        var expected = Direction.LEFT.getValue();
        var left = new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY());

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(left);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DHorizontallyRightIs90Degrees() {
        // Arrange
        var expected = Direction.RIGHT.getValue();
        var right = new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY());

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(right);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DLeftAboveIs225Degrees() {
        // Arrange
        var expected = 225;
        var leftAbove = new Coordinate2D(LOCATION.getX() - 100, LOCATION.getY() - 100);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(leftAbove);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DRightAboveIs135Degrees() {
        // Arrange
        var expected = 135d;
        var rightAbove = new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() - 10);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(rightAbove);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DLeftBelowIs315Degrees() {
        // Arrange
        var expected = 315d;
        var leftBelow = new Coordinate2D(LOCATION.getX() - 10, LOCATION.getY() + 10);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(leftBelow);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void angleToCoordinate2DRightBelowIs45Degrees() {
        // Arrange
        var expected = 45d;
        var rightBelow = new Coordinate2D(LOCATION.getX() + 10, LOCATION.getY() + 10);

        sut.init(injector);
        sut.transferCoordinatesToNode();

        // Act
        var actual = sut.angleTo(rightBelow);

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

    private class YaegerEntityImpl extends YaegerEntity {

        private Optional<Node> node = Optional.empty();
        private double testX;
        private double testY;

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

        public double getOriginX() {
            return testX;
        }

        public double getOriginY() {
            return testY;
        }
    }
}
