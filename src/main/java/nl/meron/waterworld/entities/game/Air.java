package nl.meron.waterworld.entities.game;

import nl.meron.waterworld.scenes.levels.Level;
import nl.meron.yaeger.engine.entities.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Position;

public class Air extends Bubble {

    private static final String IMAGES_BUBBLE_PNG = "waterworld/images/bubble.png";

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
