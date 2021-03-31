package com.github.hanyaeger.api.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YaegerConfigTest {

    @Test
    void showSplashDefaultToTrue() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        var showSplash = sut.isShowSplash();

        // Assert
        assertTrue(showSplash);
    }

    @Test
    void showSplashSetFalseGivesFalse() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        sut.setShowSplash(false);
        var actual = sut.isShowSplash();

        // Assert
        assertFalse(actual);
    }

    @Test
    void showBoundingBoxDefaultToFalse() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        var actual = sut.isShowBoundingBox();

        // Assert
        assertFalse(actual);
    }

    @Test
    void showBoundingBoxSetTrueGivesTrue() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        sut.setShowBoundingBox(true);
        var actual = sut.isShowBoundingBox();

        // Assert
        assertTrue(actual);
    }

    @Test
    void showDebugDefaultToFalse() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        var actual = sut.isShowDebug();

        // Assert
        assertFalse(actual);
    }

    @Test
    void showDebugSetTrueGivesTrue() {
        // Arrange
        var sut = new YaegerConfig();

        // Act
        sut.setShowDebug(true);
        var actual = sut.isShowDebug();

        // Assert
        assertTrue(actual);
    }

    @Test
    void yaegerConfigIsNotEqualToNull() {
        // Arrange
        var sut = new YaegerConfig();

        // Act & Assert
        assertNotEquals(null, sut);
    }

    @Test
    void yaegerConfigIsNotEqualToConfigWithDifferentContent() {
        // Arrange
        var sut = new YaegerConfig();
        var sutOther = new YaegerConfig();
        sut.setShowSplash(false);

        // Act & Assert
        assertNotEquals(sutOther, sut);
    }

    @Test
    void yaegerConfigIsNotEqualToInstanceOfDifferentClass() {
        // Arrange
        var sut = new YaegerConfig();
        var sutOther = new String();
        sut.setShowSplash(false);

        // Act & Assert
        assertNotEquals(sutOther, sut);
    }

    @Test
    void yaegerConfigIsEqualToSelf() {
        // Arrange
        var sut = new YaegerConfig();

        // Act & Assert
        assertEquals(sut, sut);
    }

    @Test
    void yaegerConfigIsEqualToConfigWithSameContent() {
        // Arrange
        var sut = new YaegerConfig();
        var sutOther = new YaegerConfig();

        // Act & Assert
        assertEquals(sutOther, sut);
    }

    @Test
    void hashCodeIsEqualToHashCodeFromDifferentConfigWithSameContent() {
        // Arrange
        var sut = new YaegerConfig();
        var sutOther = new YaegerConfig();

        // Act & Assert
        assertEquals(sut.hashCode(), sutOther.hashCode());
    }

    @Test
    void hashCodeIsNotEqualToHashCodeFromDifferentConfigWithDifferentContent() {
        // Arrange
        var sut = new YaegerConfig();
        var sutOther = new YaegerConfig();
        sutOther.setShowSplash(false);

        // Act & Assert
        assertNotEquals(sut.hashCode(), sutOther.hashCode());
    }
}
