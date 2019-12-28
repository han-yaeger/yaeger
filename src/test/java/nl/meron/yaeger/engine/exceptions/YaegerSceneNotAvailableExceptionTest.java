package nl.meron.yaeger.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YaegerSceneNotAvailableExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Setup

        // Test
        var sut = new YaegerSceneNotAvailableException(0);

        // Verify
        var message = sut.getMessage();

        Assertions.assertEquals("Scene 0 is not available. Ensure the scene is added to the game.", message);
    }

    @Test
    void typeIsStoredByTheException() {
        // Setup
        var sceneType = 1;

        // Test
        var sut = new YaegerSceneNotAvailableException(sceneType);

        // Verify
        var returnedType = sut.getType();

        Assertions.assertEquals(sceneType, returnedType);
    }
}
