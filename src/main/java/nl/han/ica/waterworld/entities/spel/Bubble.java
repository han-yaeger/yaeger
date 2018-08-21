package nl.han.ica.waterworld.entities.spel;

import nl.han.ica.waterworld.scenes.GameScene;
import nl.han.ica.yaeger.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.entities.interfaces.Collided;
import nl.han.ica.yaeger.entities.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public abstract class Bubble extends UpdatableSpriteEntity implements Collided {

    private final GameScene game;

    public Bubble(final String resource, final double x, final double y, final double speed, final GameScene game) {
        super(resource, x, y, 0, 2, speed, 0);
        this.game = game;
        scaleToWidth(20);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }

    protected void handleCollision() {
        game.increaseBubblesPopped();

        Sound popSound = new Sound("audio/pop.mp3");
        popSound.play();
        remove();
    }

    protected GameScene getGame() {
        return game;
    }
}
