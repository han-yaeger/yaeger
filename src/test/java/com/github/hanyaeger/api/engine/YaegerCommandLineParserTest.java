package com.github.hanyaeger.api.engine;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class YaegerCommandLineParserTest {

    @Test
    void emptyArgumentsReturnsDefaultConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var expected = new YaegerConfig();

        // Act
        var actual = sut.parseToConfig(new ArrayList<>());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void noSplashReturnsCorrectConfig() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var noSplashArgs = Arrays.asList("--noSplash");

        // Act
        var actual = sut.parseToConfig(noSplashArgs);

        // Assert
        assertFalse(actual.isShowSplash());
    }

    @Test
    void helpPrintsHelpScreen() {
        // Arrange
        var sut = new YaegerCommandLineParser();
        var helpArgs = Arrays.asList("--help");

        var ba = new ByteArrayOutputStream();
        System.setOut(new PrintStream(ba));

        // Act
        var actual = sut.parseToConfig(helpArgs);

        // Assert
        String output = new String(ba.toByteArray());
        assertTrue(output.contains("--noSplash"));
        assertTrue(output.contains("--help"));
    }
}
