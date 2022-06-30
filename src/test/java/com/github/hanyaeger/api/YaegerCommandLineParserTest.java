package com.github.hanyaeger.api;

import com.github.hanyaeger.core.YaegerCommandLineParser;
import com.github.hanyaeger.core.YaegerConfig;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class YaegerCommandLineParserTest {

    @Test
    void emptyArgumentsReturnsDefaultConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var expected = new YaegerConfig(true, false, false, false, false);

        // Act
        var actual = sut.parseToConfig(new ArrayList<>());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void noSplashReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var noSplashArgs = Collections.singletonList("--noSplash");

        // Act
        var actual = sut.parseToConfig(noSplashArgs);

        // Assert
        assertFalse(actual.showSplash());
    }

    @Test
    void showBBReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var showBBArgs = Collections.singletonList("--showBB");

        // Act
        var actual = sut.parseToConfig(showBBArgs);

        // Assert
        assertTrue(actual.showBoundingBox());
    }

    @Test
    void showDebugReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var showDebugArgs = Collections.singletonList("--showDebug");

        // Act
        var actual = sut.parseToConfig(showDebugArgs);

        // Assert
        assertTrue(actual.showDebug());
    }

    @Test
    void enableScrollReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var showDebugArgs = Collections.singletonList("--enableScroll");

        // Act
        var actual = sut.parseToConfig(showDebugArgs);

        // Assert
        assertTrue(actual.enableScroll());
    }

    @Test
    void limitGWUReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var limitGWUArgs = Collections.singletonList("--limitGWU");

        // Act
        var actual = sut.parseToConfig(limitGWUArgs);

        // Assert
        assertTrue(actual.limitGWU());
    }

    @Test
    void helpPrintsHelpScreen() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var helpArgs = Collections.singletonList("--help");

        var ba = new ByteArrayOutputStream();
        System.setOut(new PrintStream(ba));

        // Act
        var actual = sut.parseToConfig(helpArgs);

        // Assert
        String output = ba.toString();
        assertTrue(output.contains("--noSplash"));
        assertTrue(output.contains("--help"));
    }

    @Test
    void invalidArgumentPrintsWarning() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var invalidArgs = Collections.singletonList("--foo");

        var ba = new ByteArrayOutputStream();
        System.setOut(new PrintStream(ba));

        // Act
        var actual = sut.parseToConfig(invalidArgs);

        // Assert
        String output = ba.toString();
        assertTrue(output.contains("--foo"));
    }
}
