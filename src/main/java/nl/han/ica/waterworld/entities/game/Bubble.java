package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.BoundingBox;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Movement;
import nl.han.ica.yaeger.engine.scene.SceneBorder;
import nl.han.ica.yaeger.engine.collisions.Collided;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.engine.resourceconsumer.audio.Sound;

public abstract class Bubble extends UpdatableSpriteEntity implements Collided {

    private static final String AUDIO_POP_MP3 = "audio/pop.mp3";
    private final Level level;

    Bubble(final Position position, final String resource, final double speed, final Level game) {
        super(resource, position, new BoundingBox(20, 20), 0, new Movement(Movement.Direction.UP, speed), 0);
        this.level = game;
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }

    void handleCollision() {
        level.increaseBubblesPopped();

        Sound popSound = new Sound(AUDIO_POP_MP3);
        popSound.play();
        remove();
    }

    protected Level getLevel() {
        return level;
    }
}
