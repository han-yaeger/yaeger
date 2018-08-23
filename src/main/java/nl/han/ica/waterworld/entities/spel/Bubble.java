package nl.han.ica.waterworld.entities.spel;

import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.entities.interfaces.Collided;
import nl.han.ica.yaeger.entities.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public abstract class Bubble extends UpdatableSpriteEntity implements Collided {

    private final Level level;

    Bubble(final String resource, final double x, final double y, final double speed, final Level game) {
        super(resource, x, y, 0, 2, speed, 0);
        this.level = game;
        scaleToWidth(20);
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
