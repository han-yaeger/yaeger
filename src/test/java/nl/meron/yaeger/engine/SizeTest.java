package nl.meron.yaeger.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeTest {

    private static final int WIDTH = 37;
    private static final int HEIGHT = 42;

    @Test
    void widthFromConstructorIsSet() {
        // Arrange
        var sut = new Size(WIDTH, HEIGHT);

        // Act
        int width = sut.getWidth();

        // Assert
        assertEquals(WIDTH, width);
    }

    @Test
    void heightFromConstructorIsSet() {
        // Arrange
        var sut = new Size(WIDTH, HEIGHT);

        // Act
        int height = sut.getHeight();

        // Assert
        assertEquals(HEIGHT, height);
    }

}
