package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.entities.events.EventTypes;
import com.github.hanyaeger.core.entities.motion.MotionApplier;
import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoundingBoxVisualizerTest {

    private YaegerEntity yaegerEntity;
    private BoundingBox bounds = new BoundingBox(50, 50, 0, 25, 25, 0);
    private static final double VIEW_ORDER = 42;

    private BoundingBoxVisualizer sut;

    @BeforeEach
    void setup() {
        yaegerEntity = mock(YaegerEntity.class);
        when(yaegerEntity.getBoundingBox()).thenReturn(bounds);
        when(yaegerEntity.getViewOrder()).thenReturn(VIEW_ORDER);

        sut = new BoundingBoxVisualizer(yaegerEntity);
    }

    @Test
    void boundingBoxVisualizerUsesTheInSceneBBOfTheYeagerEntity() {
        // Arrange

        // Act

        // Arrange
        assertEquals(bounds.getMinX(), sut.getAnchorLocation().getX());
        assertEquals(bounds.getMinY(), sut.getAnchorLocation().getY());
    }

    @Test
    void removeEventListenerGetsAttachedToTheYeagerEntity() {
        // Arrange

        // Act

        // Arrange
        verify(yaegerEntity).attachEventListener(eq(EventTypes.REMOVE), any());
    }

    @Test
    void widthFromYaegerEntityIsUsed() {
        // Arrange
        var expectedWidth = 37D;
        when(yaegerEntity.getWidth()).thenReturn(expectedWidth);

        // Act
        sut.explicitUpdate(0L);

        // Arrange
        assertEquals(expectedWidth, sut.getWidth());
    }

    @Test
    void viewOrderOfYaegerEntityIsUsed() {
        // Arrange

        // Act

        // Arrange
        assertEquals(VIEW_ORDER - 1, sut.getViewOrder());
    }

    @Test
    void heightFromYaegerEntityIsUsed() {
        // Arrange
        var expectedHeight = 42D;
        when(yaegerEntity.getWidth()).thenReturn(expectedHeight);

        // Act
        sut.explicitUpdate(0L);

        // Arrange
        assertEquals(expectedHeight, sut.getWidth());
    }

    @Test
    void addToEntityCollectionCallsAddBoundingBoxVisualizer() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);

        // Act
        sut.addToEntityCollection(entityCollection);

        // Arrange
        verify(entityCollection).addBoundingBoxVisualizer(sut);
    }

    @Test
    void initSetsTransparentToMouse() {
        // Arrange
        var injectorMock = mock(Injector.class);
        var rectangleMock = mock(Rectangle.class, withSettings().withoutAnnotations());
        sut.setShape(rectangleMock);

        var motionApplierMock = mock(MotionApplier.class);
        sut.setMotionApplier(motionApplierMock);

        // Act
        sut.init(injectorMock);

        // Assert
        verify(rectangleMock).setMouseTransparent(true);
    }
}
