package nl.meron.yaeger.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeTest {

    private static final int WIDTH = 37;
    private static final int HEIGHT = 42;

    @Test
    void widthFromConstructorIsSet() {
        // Setup
        var sut = new Size(WIDTH, HEIGHT);

        // Test
        int width = sut.getWidth();

        // Verify
        assertEquals(WIDTH, width);
    }

    @Test
    void heightFromConstructorIsSet() {
        // Setup
        var sut = new Size(WIDTH, HEIGHT);

        // Test
        int height = sut.getHeight();

        // Verify
        assertEquals(HEIGHT, height);
    }

}
