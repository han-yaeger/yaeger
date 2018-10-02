package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.engine.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.engine.entities.interfaces.Collided;
import nl.han.ica.yaeger.engine.entities.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.engine.resourceconsumer.audio.Sound;

public abstract class Bubble extends UpdatableSpriteEntity implements Collided {

    private final Level level;

    Bubble(final String resource, final double speed, final Level game) {
        super(resource, 20, 20, 0, 2, speed, 0);
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

        Sound popSound = new Sound("audio/pop.mp3");
        popSound.play();
        remove();
    }

    protected Level getLevel() {
        return level;
    }
}
