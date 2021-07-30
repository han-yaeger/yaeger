package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Coordinate2D;
import com.google.inject.Injector;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TextEntityTest {

    private static final String YAEGER = "Yaeger";
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    private static final Font FONT = Font.font("palatino", FontWeight.BOLD, 240);
    private static final CustomFont CUSTOM_FONT = new CustomFont("fonts/pixelfont.ttf", 12);

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
    void getFontWithoutNodeOrBufferedFontReturnsDefault() {
        // Arrange

        // Act
        var actual = sut.getFont();

        // Assert
        assertEquals(TextEntity.DEFAULT_FONT, actual);
    }

    @Test
    void getFontBeforeNodeIsSetUsesBufferedFont() {
        // Arrange
        sut.setFont(FONT);

        // Act
        var actual = sut.getFont();

        // Assert
        assertEquals(FONT, actual);
    }

    @Test
    void getFontAfterNodeIsSetDelegatesTheFont() {
        // Arrange
        sut.setShape(text);
        sut.init(injector);

        when(text.getFont()).thenReturn(FONT);

        // Act
        var actual = sut.getFont();

        // Assert
        assertEquals(FONT, actual);
    }

    @Test
    void getTextBeforeNodeIsSetUsesBufferedText() {
        // Arrange
        sut.setText(YAEGER);

        // Act
        var actual = sut.getText();

        // Assert
        assertEquals(YAEGER, actual);
    }

    @Test
    void getTextAfterNodeIsSetDelegatesTheText() {
        // Arrange
        sut.setShape(text);
        sut.init(injector);

        when(text.getText()).thenReturn(YAEGER);

        // Act
        var actual = sut.getText();

        // Assert
        assertEquals(YAEGER, actual);
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
    void settingDelegateSetsFontOnDelegateForCustomFont() {
        // Setup

        // Test
        sut.setFont(CUSTOM_FONT);
        sut.setShape(text);
        sut.init(injector);

        // Assert
        verify(text).setFont(CUSTOM_FONT.getFont());
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
        sut.setFont(FONT);

        // Assert
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
    }
}
