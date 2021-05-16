package com.github.hanyaeger.core.styles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HanFontTest {
    private static final int SIZE = 37;
    private static final HanFontStyle STYLE = HanFontStyle.ULTRA_LIGHT;

    @Test
    void createDefaultFontCreatesFontOfCorrectSize() {
        // Arrange

        // Act
        var font = HanFont.createDefaultFont(SIZE);

        // Assert
        assertEquals(SIZE, font.getSize());
    }

    @Test
    void createDefaultFontCreatesFontOfCorrectStyle() {
        // Arrange

        // Act
        var font = HanFont.createDefaultFont(SIZE);

        // Assert
        assertTrue(font.getName().toLowerCase().contains(HanFontStyle.REGULAR.getStyle().toLowerCase()));
    }

    @Test
    void createDefaultFontWithStyleCreatesFontOfCorrectSize() {
        // Arrange

        // Act
        var font = HanFont.createDefaultFont(STYLE, SIZE);

        // Assert
        assertEquals(SIZE, font.getSize());
    }


    @Test
    void createDefaultFontWithStyleCreatesFontOfExpectedStyle() {
        // Arrange

        // Act
        var font = HanFont.createDefaultFont(STYLE, SIZE);

        // Assert
        assertTrue(font.getName().toLowerCase().contains(STYLE.getStyle().toLowerCase()));

    }


    @Test
    void createDefaultCondensedFontCreatesFontOfCorrectSize() {
        // Arrange

        // Act
        var font = HanFont.createDefaultCondensedFont(SIZE);

        // Assert
        assertEquals(SIZE, font.getSize());
    }

    @Test
    void createDefaultCondensedFontCreatesFontOfCorrectStyle() {
        // Arrange

        // Act
        var font = HanFont.createDefaultCondensedFont(SIZE);

        // Assert
        assertTrue(font.getName().toLowerCase().contains(HanFontStyle.REGULAR.getStyle().toLowerCase()));

    }

    @Test
    void createDefaultCondensedFontWithStyleCreatesFontOfCorrectSize() {
        // Arrange

        // Act
        var font = HanFont.createDefaultCondensedFont(STYLE, SIZE);

        // Assert
        assertEquals(SIZE, font.getSize());
    }

    @Test
    void createDefaultCondensedFontWithStyleCreatesFontOfExpectedStyle() {
        // Arrange

        // Act
        var font = HanFont.createDefaultCondensedFont(STYLE, SIZE);

        // Assert
        var name = font.getName().toLowerCase().replace(" ", "");
        var style = STYLE.getStyle().toLowerCase();
        assertTrue(name.contains(style));
    }
}
