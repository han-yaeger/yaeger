package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.google.inject.Injector;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TextEntityTest {

    private static final String YAEGER = "Yaeger";
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    private static final Font FONT = Font.font("palatino", FontWeight.BOLD, 240);
    private static final Color COLOR = Color.DARKBLUE;
    private Text text;
    private Injector injector;
    private TextEntity sut;

    @BeforeEach
    void setup() {
        text = mock(Text.class);
        injector = mock(Injector.class);
        sut = new TextEntity(LOCATION);
    }

    @Test
    void setAnchorLocationSetsAnchorLocationOnNode() {
        // Arrange
        sut.setShape(text);
        var expected = new Coordinate2D(1.1, 2.2);

        // Act
        sut.setAnchorLocation(expected);

        // Assert
        verify(text).setX(expected.getX());
        verify(text).setY(expected.getY());
    }

    @Test
    void settingDelegateSetsTextOnDelegate() {
        // Setup

        // Test
        sut.setText(YAEGER);
        sut.setShape(text);
        sut.init(injector);

        // Assert
        verify(text).setTextOrigin(VPos.TOP);
        verify(text).setText(YAEGER);
    }

    @Test
    void settingDelegateSetsFillOnDelegate() {
        // Setup

        // Test
        sut.setFill(COLOR);
        sut.setShape(text);
        sut.init(injector);

        // Assert
        verify(text).setFill(COLOR);
    }

    @Test
    void settingDelegateSetsFontOnDelegate() {
        // Setup

        // Test
        sut.setFont(FONT);
        sut.setShape(text);
        sut.init(injector);

        // Assert
        verify(text).setFont(FONT);
    }

    @Test
    void settingDelegateWithContentDelegatesContent() {
        // Setup
        var sut = new TextEntity(LOCATION, YAEGER);

        // Test
        sut.setShape(text);
        sut.init(injector);

        // Assert
        verify(text).setText(YAEGER);
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Setup
        sut.setShape(text);
        sut.init(injector);

        // Test
        sut.setText(YAEGER);
        sut.setVisible(false);
        sut.setFont(FONT);
        sut.setFill(COLOR);

        // Assert
        verify(text).setVisible(false);
        verify(text).setFill(COLOR);
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
    }
}
