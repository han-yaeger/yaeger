package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.events.EventTypes;
import javafx.geometry.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoundingBoxVisualizerTest {

    private YaegerEntity yaegerEntity;
    private BoundingBox bounds = new BoundingBox(50, 50, 0, 25, 25, 0);

    private BoundingBoxVisualizer sut;

    @BeforeEach
    void setup() {
        yaegerEntity = mock(YaegerEntity.class);
        when(yaegerEntity.getBoundingBox()).thenReturn(bounds);

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
    void removeEventistenerGetsAttachedToTheYeagerEntity() {
        // Arrange

        // Act

        // Arrange
        verify(yaegerEntity).attachEventListener(eq(EventTypes.REMOVE), any());
    }

    @Test
    void widthFromYaegerEntityIsUsed(){
        // Arrange
        var expectedWidth = 37D;
        when(yaegerEntity.getWidth()).thenReturn(expectedWidth);

        // Act
        sut.explicitUpdate(0L);

        // Arrange
        assertEquals(expectedWidth, sut.getWidth());
    }

    @Test
    void heightFromYaegerEntityIsUsed(){
        // Arrange
        var expectedHeight = 42D;
        when(yaegerEntity.getWidth()).thenReturn(expectedHeight);

        // Act
        sut.explicitUpdate(0L);

        // Arrange
        assertEquals(expectedHeight, sut.getWidth());
    }
}