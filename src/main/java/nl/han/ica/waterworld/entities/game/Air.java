package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.entity.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class Air extends Bubble {

    public Air(final Position position, final double speed, final Level waterworld) {
        super(position, "images/bubble.png", speed, waterworld);
    }

    @Override
    public void onCollision(final Collider collidingObject, final CollisionSide collisionSide) {
        if (collidingObject instanceof Player) {
            handleCollision();
        }
    }
}
