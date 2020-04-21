package nl.han.waterworld.entities.game;

import nl.han.waterworld.scenes.levels.Level;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.Location;

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
