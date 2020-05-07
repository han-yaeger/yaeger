package nl.han.yaeger.engine.styles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HanFontStyleTest {

    private static final String BOLD = "Bold";
    private static final String BOLD_ITALIC = "BoldItalic";
    private static final String DEMI_BOLD = "DemiBold";
    private static final String DEMI_BOLD_ITALIC = "DemiBoldItalic";
    private static final String HEAVY = "Heavy";
    private static final String HEAVY_ITALIC = "HeavyItalic";
    private static final String ITALIC = "Italic";
    private static final String MEDIUM = "Medium";
    private static final String MEDIUM_ITALIC = "MediumItalic";
    private static final String REGULAR = "Regular";
    private static final String ULTRA_LIGHT = "UltraLight";
    private static final String ULTRA_LIGHT_ITALIC = "UltraLightItalic";

    @Test
    void boldSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.BOLD;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(BOLD, style);
    }

    @Test
    void boldItalicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.BOLD_ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(BOLD_ITALIC, style);
    }

    @Test
    void demiBoldSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.DEMI_BOLD;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(DEMI_BOLD, style);
    }

    @Test
    void demiBoldItalicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.DEMI_BOLD_ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(DEMI_BOLD_ITALIC, style);
    }

    @Test
    void heavySetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.HEAVY;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(HEAVY, style);
    }

    @Test
    void heavyItalicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.HEAVY_ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(HEAVY_ITALIC, style);
    }

    @Test
    void italicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(ITALIC, style);
    }

    @Test
    void mediumSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.MEDIUM;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(MEDIUM, style);
    }

    @Test
    void mediumItalicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.MEDIUM_ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(MEDIUM_ITALIC, style);
    }

    @Test
    void regularSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.REGULAR;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(REGULAR, style);
    }

    @Test
    void ultraLightSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.ULTRA_LIGHT;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(ULTRA_LIGHT, style);
    }

    @Test
    void ultraLightItalicSetsBoldStyle() {
        // Arrange
        var sut = HanFontStyle.ULTRA_LIGHT_ITALIC;

        // Act
        var style = sut.getStyle();

        // Assert
        assertEquals(ULTRA_LIGHT_ITALIC, style);
    }
}
