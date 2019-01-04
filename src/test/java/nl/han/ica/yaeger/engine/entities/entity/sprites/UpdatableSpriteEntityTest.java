package nl.han.ica.yaeger.engine.entities.entity.sprites;

import com.google.inject.Injector;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SpriteAnimationDelegate;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.engine.scenes.SceneBorder;
import nl.han.ica.yaeger.javafx.factories.ImageViewFactory;
import nl.han.ica.yaeger.module.factories.SpriteAnimationDelegateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UpdatableSpriteEntityTest {

    private final static String DEFAULT_RESOURCE = "images/bubble.png";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Position DEFAULT_POSITION = new Position(X_POSITION, Y_POSITION);
    private final static int WIDTH = 39;
    private final static int HEIGHT = 41;
    private final static Size DEFAULT_SIZE = new Size(WIDTH, HEIGHT);

    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageViewFactory imageViewFactory;
    private SpriteAnimationDelegate spriteAnimationDelegate;
    private ImageRepository imageRepository;
    private Injector injector;

    @BeforeEach
    void setup() {
        imageViewFactory = mock(ImageViewFactory.class);
        spriteAnimationDelegate = mock(SpriteAnimationDelegate.class);
        spriteAnimationDelegateFactory = mock(SpriteAnimationDelegateFactory.class);
        when(spriteAnimationDelegateFactory.create(any(ImageView.class), anyInt())).thenReturn(spriteAnimationDelegate);

        imageRepository = mock(ImageRepository.class);
        injector = mock(Injector.class);
    }

    @Test
    void instantiatingAnUpdatableSpriteEntityWithOneFrameGivesNoSideEffects() {
        // Setup

        // Test
        var updatableSpriteEntity = new TestUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POSITION, DEFAULT_SIZE);

        // Verify
        Assertions.assertNotNull(updatableSpriteEntity);
    }

    @Test
    void setAutocycleDoesNotBreakWhithOnlyOneFrame() {
        // Setup
        var autocycleValue = 37;
        var updatableSpriteEntity = new TestUpdatableSpriteEntity(DEFAULT_RESOURCE, DEFAULT_POSITION, DEFAULT_SIZE);
        updatableSpriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);
        updatableSpriteEntity.setImageRepository(imageRepository);
        updatableSpriteEntity.setImageViewFactory(imageViewFactory);
        updatableSpriteEntity.setSpriteAnimationDelegateFactory(spriteAnimationDelegateFactory);

        updatableSpriteEntity.setAutoCycle(autocycleValue);

        // Test
        updatableSpriteEntity.init(injector);

        // Verify
    }

    private class TestUpdatableSpriteEntity extends UpdatableSpriteEntity {

        public TestUpdatableSpriteEntity(String resource, Position position, Size size) {
            super(resource, position, size);
        }

        @Override
        public void notifyBoundaryCrossing(SceneBorder border) {

        }
    }

}