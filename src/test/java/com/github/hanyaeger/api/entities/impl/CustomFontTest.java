package com.github.hanyaeger.api.entities.impl;

import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

class CustomFontTest {

    private Font font;
    private static final String CUSTOM_FONT = "fonts/pixelfont.ttf";

    @BeforeEach
    void setup() {
        font = mock(Font.class);
    }

    @Test
    void getFontReturnsFontCreatedByJavaFX() {
        try (MockedStatic<Font> fontMockedStatic = mockStatic(Font.class)) {
            fontMockedStatic.when(() -> Font.loadFont(anyString(), anyDouble())).thenReturn(font);

            // Arrange
            CustomFont sut = new CustomFont(CUSTOM_FONT, 12);

            // Act
            var actual = sut.getFont();

            // Assert
            Assertions.assertEquals(font, actual);
        }
    }

    @Test
    void getFontPassesFontSizeToJavaFX() {
        var fontSize = 12D;
        try (MockedStatic<Font> fontMockedStatic = mockStatic(Font.class)) {
            fontMockedStatic.when(() -> Font.loadFont(anyString(), eq(fontSize))).thenReturn(font);

            // Arrange
            CustomFont sut = new CustomFont(CUSTOM_FONT, fontSize);

            // Act
            var actual = sut.getFont();

            // Assert
            Assertions.assertEquals(font, actual);
        }
    }
}
