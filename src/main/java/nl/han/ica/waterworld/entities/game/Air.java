package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.entities.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class Air extends Bubble {

    private static final String IMAGES_BUBBLE_PNG = "images/bubble.png";

    public Air(final Position position, final double speed, final Level waterworld) {
        super(position, IMAGES_BUBBLE_PNG, speed, waterworld);
    }

    @Override
    public void onCollision(final Collider collidingObject, final CollisionSide collisionSide) {
        super.onCollision(collidingObject, collisionSide);

        if (collidingObject instanceof Player) {
            handlePlayerCollision();
        }
    }
}
