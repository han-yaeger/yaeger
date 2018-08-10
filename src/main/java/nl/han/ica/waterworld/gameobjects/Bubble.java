package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.interfaces.Collided;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public abstract class Bubble extends UpdatableSpriteObject implements Collided {

    private final Waterworld waterworld;

    public Bubble(final String resource, final double x, final double y, final double speed, final Waterworld waterworld) {
        super(resource, x, y, 0, 2, speed, 0);
        this.waterworld = waterworld;
        scaleToWidth(20);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }

    protected void handleCollision() {
        getWaterworld().increaseBubblesPopped();

        Sound popSound = new Sound("audio/pop.mp3");
        popSound.play();
        remove();
    }

    protected Waterworld getWaterworld() {
        return waterworld;
    }
}
