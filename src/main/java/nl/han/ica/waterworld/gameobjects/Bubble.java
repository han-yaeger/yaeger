package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.interfaces.Collided;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public class Bubble extends UpdatableSpriteObject implements Collided {

    private final Waterworld waterworld;

    public Bubble(final double x, final double y, final Waterworld waterworld) {
        super("images/bubble.png", x, y, 0, 2, 2, 0);
        this.waterworld = waterworld;
        scaleToWidth(20);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        waterworld.increaseBubblesPopped();

        Sound popSound = new Sound("audio/pop.mp3");
        popSound.play();
        remove();
    }
}
