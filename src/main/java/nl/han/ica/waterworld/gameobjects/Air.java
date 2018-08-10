package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;

public class Air extends Bubble {

    public Air(double x, double y, Waterworld waterworld) {
        super("images/bubble.png", x, y, waterworld);
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        if (collidingObject instanceof Player) {
            handleCollision();
        }
    }
}
