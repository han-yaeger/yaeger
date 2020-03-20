package nl.meron.yaeger.engine.entities.tilemap;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.exceptions.InvalidConstructorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryTest {

    private TileFactory sut;

    private final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static Location DEFAULT_LOCATION = new Location(37, 42);
    private final static Size DEFAULT_SIZE = new Size(39, 45);

    @BeforeEach
    void setup() {
        sut = new TileFactory();
    }

    @Test
    void creatingEntityWithInvalidConstructorThrowsinvalidConstructorException() {
        // Arrange

        // Act
        var invalidConstructorException = assertThrows(InvalidConstructorException.class, () -> sut.create(SpriteEntityInvalidConstructorImpl.class, DEFAULT_LOCATION, DEFAULT_SIZE));

        // Assert
        assertTrue(invalidConstructorException.getCause() instanceof NoSuchMethodException);
    }

    private class SpriteEntityValidConstructorImpl extends SpriteEntity {

        protected SpriteEntityValidConstructorImpl(Location initialLocation, Size size) {
            super(DEFAULT_RESOURCE, initialLocation, size);
        }
    }

    private class SpriteEntityInvalidConstructorImpl extends SpriteEntity {

        protected SpriteEntityInvalidConstructorImpl(String resource, Location initialLocation, Size size) {
            super(resource, initialLocation, size);
        }
    }

}
