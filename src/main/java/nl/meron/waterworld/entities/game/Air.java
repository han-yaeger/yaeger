package nl.meron.waterworld.entities.game;

import nl.meron.waterworld.scenes.levels.Level;
import nl.meron.yaeger.engine.entities.entity.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Location;

public class Air extends Bubble {

    private static final String IMAGES_BUBBLE_PNG = "waterworld/images/bubble.png";

    public Air(final Location location, final double speed, final Level waterworld) {
        super(location, IMAGES_BUBBLE_PNG, speed, waterworld);
    }

    @Override
    public void onCollision(final Collider collidingObject) {
        super.onCollision(collidingObject);

        if (collidingObject instanceof Player) {
            handlePlayerCollision();
        }
    }
}
