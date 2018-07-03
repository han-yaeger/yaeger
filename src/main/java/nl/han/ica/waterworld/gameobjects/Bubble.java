package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.interfaces.Collided;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public class Bubble extends UpdatableSpriteObject implements Collided {

    public Bubble(double x, double y) {
        super("images/bubble.png", x, y, 0, 2, 2, 0);
        scaleToWidth(20);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }

    @Override
    public void hasCollidedWith(Collider collidingObject, CollisionSide collisionSide) {
        Sound popSound = new Sound("audio/pop.mp3");
        popSound.play();
        remove();

        System.out.println("collided with side: " + collisionSide);
    }
}
