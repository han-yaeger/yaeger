package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.entities.interfaces.Collider;

public class Air extends Bubble {

    public Air(final double speed, final Level waterworld) {
        super("images/bubble.png", speed, waterworld);
    }

    @Override
    public void onCollision(final Collider collidingObject, final CollisionSide collisionSide) {
        if (collidingObject instanceof Player) {
            handleCollision();
        }
    }
}
