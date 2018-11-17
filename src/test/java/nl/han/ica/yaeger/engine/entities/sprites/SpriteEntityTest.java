package nl.han.ica.yaeger.engine.entities.sprites;

import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Size;
import nl.han.ica.yaeger.engine.entities.entity.sprites.SpriteEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpriteEntityTest {

    @Test
    void instantiatingASpriteEntityWithOneFrameGivesNoSideEffects() {
        // Setup
        var resource = "";
        var position = new Position(37, 37);
        var size = new Size(40, 40);

        // Test
        var spriteEntity = new TestSpriteEntityWithDefaultFrames(resource, position, size);

        // Verify
        Assertions.assertNotNull(spriteEntity);
    }

    @Test
    void instantiatingASpriteEntityWithTwoFrameGivesNoSideEffects() {
        // Setup
        var resource = "";
        var position = new Position(37, 37);
        var size = new Size(40, 40);

        // Test
        var spriteEntity = new TestSpriteEntityWithTwoFrames(resource, position, size, 2);

        // Verify
        Assertions.assertNotNull(spriteEntity);
    }

    private class TestSpriteEntityWithDefaultFrames extends SpriteEntity {

        TestSpriteEntityWithDefaultFrames(String resource, Position position, Size size) {
            super(resource, position, size);
        }
    }

    private class TestSpriteEntityWithTwoFrames extends SpriteEntity {

        TestSpriteEntityWithTwoFrames(String resource, Position position, Size size, int frames) {
            super(resource, position, size, frames);
        }
    }
}
