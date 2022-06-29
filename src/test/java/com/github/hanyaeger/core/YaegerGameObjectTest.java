package com.github.hanyaeger.core;

import javafx.scene.effect.ColorAdjust;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YaegerGameObjectTest {

    private static final double BRIGHTNESS = 0.37D;
    private static final double CONTRAST = 0.314159D;
    private static final double HUE = 0.42D;
    private static final double SATURATION = 0.27D;

    private YaegerGameObjectImpl sut;

    @BeforeEach
    void setup() {
        sut = new YaegerGameObjectImpl();
    }

    @Test
    void setBrightnessDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);

        // Act
        sut.setBrightness(BRIGHTNESS);

        // Verify
        verify(colorAdjust).setBrightness(BRIGHTNESS);
    }

    @Test
    void getBrightnessDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);
        when(colorAdjust.getBrightness()).thenReturn(BRIGHTNESS);

        // Act
        double actual = sut.getBrightness();

        // Verify
        assertEquals(BRIGHTNESS, actual);
    }

    @Test
    void setContrastDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);

        // Act
        sut.setContrast(CONTRAST);

        // Verify
        verify(colorAdjust).setContrast(CONTRAST);
    }

    @Test
    void getConstrastDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);
        when(colorAdjust.getContrast()).thenReturn(CONTRAST);

        // Act
        double actual = sut.getContrast();

        // Verify
        assertEquals(CONTRAST, actual);
    }

    @Test
    void setHueDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);

        // Act
        sut.setHue(HUE);

        // Verify
        verify(colorAdjust).setHue(HUE);
    }

    @Test
    void getHueDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);
        when(colorAdjust.getHue()).thenReturn(HUE);

        // Act
        double actual = sut.getHue();

        // Verify
        assertEquals(HUE, actual);
    }

    @Test
    void setSaturationDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);

        // Act
        sut.setSaturation(SATURATION);

        // Verify
        verify(colorAdjust).setSaturation(SATURATION);
    }

    @Test
    void getSaturationDelegatesToTheColorAdjust() {
        // Arrange
        var colorAdjust = mock(ColorAdjust.class);
        sut.setColorAdjust(colorAdjust);
        when(colorAdjust.getSaturation()).thenReturn(SATURATION);

        // Act
        double actual = sut.getSaturation();

        // Verify
        assertEquals(SATURATION, actual);
    }

    static private class YaegerGameObjectImpl extends YaegerGameObject {

        void setColorAdjust(final ColorAdjust colorAdjust) {
            this.colorAdjust = colorAdjust;
        }
    }
}
