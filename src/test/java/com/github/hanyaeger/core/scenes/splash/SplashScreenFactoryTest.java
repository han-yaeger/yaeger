package com.github.hanyaeger.core.scenes.splash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SplashScreenFactoryTest {

    @Test
    void callingFactoryCreatesAnInstanceOfSplashScene() {
        // Arrange
        var sut = new SplashScreenFactory();
        var runnable = mock(Runnable.class);

        // Act
        var yaegerScene = sut.create(runnable);

        // Assert
        assertTrue(yaegerScene instanceof SplashScene);
    }
}
