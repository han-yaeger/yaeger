package nl.han.yaeger.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeTest {

    private static final double WIDTH = 37;
    private static final double HEIGHT = 42;

    @Test
    void widthFromConstructorIsSet() {
        // Arrange
        var sut = new Size(WIDTH, HEIGHT);

        // Act
        var actual = sut.getWidth();

        // Assert
        assertEquals(WIDTH, actual);
    }

    @Test
    void heightFromConstructorIsSet() {
        // Arrange
        var sut = new Size(WIDTH, HEIGHT);

        // Act
        var actual = sut.getHeight();

        // Assert
        assertEquals(HEIGHT, actual);
    }

}
