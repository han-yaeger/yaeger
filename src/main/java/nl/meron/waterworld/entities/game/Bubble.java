package nl.meron.waterworld.entities.game;

import nl.meron.waterworld.scenes.levels.Level;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.movement.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.media.audio.SoundClip;
import nl.meron.yaeger.engine.entities.collisions.Collided;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public abstract class Bubble extends UpdatableSpriteEntity implements Collided {

    private static final String AUDIO_POP_MP3 = "waterworld/audio/pop.mp3";
    private final Level level;

    Bubble(final Point point, final String resource, final double speed, final Level game) {
        super(resource, point, new Size(20, 20), 0, new MovementVector(MovementVector.Direction.UP, speed));
        this.level = game;
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        if (collidingObject instanceof AnimatedShark) {
            handleSharkCollision();
        }
    }

    void handleSharkCollision() {
        removeBubble();
    }

    void handlePlayerCollision() {
        level.increaseBubblesPopped();

        removeBubble();
    }

    private void removeBubble() {
        SoundClip popSound = new SoundClip(AUDIO_POP_MP3);
        popSound.play();
        remove();
    }

    protected Level getLevel() {
        return level;
    }
}
