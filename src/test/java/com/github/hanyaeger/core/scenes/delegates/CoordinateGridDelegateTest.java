package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.core.factories.LineFactory;
import com.github.hanyaeger.core.factories.TextFactory;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CoordinateGridDelegateTest {

    private static final double PANE_WIDTH_MINIMAL = 2;
    private static final double PANE_HEIGHT_MINIMAL = 2;
    private static final double PANE_WIDTH_REGULAR = 20;
    private static final double PANE_HEIGHT_REGULAR = 20;
    private CoordinateGridDelegate sut;
    private Pane paneMock;
    private LineFactory lineFactoryMock;
    private TextFactory textFactoryMock;

    @BeforeEach
    void setup() {
        paneMock = mock(Pane.class);
        lineFactoryMock = mock(LineFactory.class);
        textFactoryMock = mock(TextFactory.class);
        ObservableList<Node> childrenMock = mock(ObservableList.class);
        var lineMock = mock(Line.class);
        var textMock = mock(Text.class);

        sut = new CoordinateGridDelegate();
        sut.setLineFactory(lineFactoryMock);
        sut.setTextFactory(textFactoryMock);

        sut.setup(paneMock);

        when(paneMock.getChildren()).thenReturn(childrenMock);
        when(lineFactoryMock.createLine(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), any())).thenReturn(lineMock);
        when(textFactoryMock.createText(anyString(), any(Color.class))).thenReturn(textMock);
    }

    @Test
    void postActivationDrawsOnlyOriginLineOfCoordinateGridIfWidthHeightIsMinimal() {
        // Arrange
        when(paneMock.getWidth()).thenReturn(PANE_WIDTH_MINIMAL);
        when(paneMock.getHeight()).thenReturn(PANE_HEIGHT_MINIMAL);

        // Act
        sut.postActivation();

        // Assert
        verify(lineFactoryMock, times(2)).createLine(eq(0d), eq(0d), anyDouble(), anyDouble(), anyDouble(), any());
    }

    @Test
    void postActivationDrawsOnlyOriginOfCoordinateGridIfWidthHeightIsMinimal() {
        // Arrange
        when(paneMock.getWidth()).thenReturn(PANE_WIDTH_MINIMAL);
        when(paneMock.getHeight()).thenReturn(PANE_HEIGHT_MINIMAL);

        // Act
        sut.postActivation();

        // Assert
        verify(textFactoryMock, times(2)).createText("0", CoordinateGridDelegate.TEXT_COLOR);
    }

    @Test
    void postActivationDrawsOriginLineOfCoordinateGrid() {
        // Arrange
        when(paneMock.getWidth()).thenReturn(PANE_WIDTH_REGULAR);
        when(paneMock.getHeight()).thenReturn(PANE_HEIGHT_REGULAR);

        // Act
        sut.postActivation();

        // Assert
        verify(lineFactoryMock, times(2)).createLine(eq(0d), eq(0d), anyDouble(), anyDouble(), anyDouble(), any());
    }

    @Test
    void postActivationDrawsOriginOfCoordinateGrid() {
        // Arrange
        when(paneMock.getWidth()).thenReturn(PANE_WIDTH_REGULAR);
        when(paneMock.getHeight()).thenReturn(PANE_HEIGHT_REGULAR);

        // Act
        sut.postActivation();

        // Assert
        verify(textFactoryMock, times(2)).createText("0", CoordinateGridDelegate.TEXT_COLOR);
    }

    @Test
    void destroyRemovesGridFromPane() {
        // Arrange
        when(paneMock.getWidth()).thenReturn(PANE_WIDTH_REGULAR);
        when(paneMock.getHeight()).thenReturn(PANE_HEIGHT_REGULAR);
        sut.postActivation();

        // Act
        ObservableList<Node> children = paneMock.getChildren();
        sut.destroy();

        // Assert
        verify(paneMock, atLeastOnce()).getChildren();
    }
}
