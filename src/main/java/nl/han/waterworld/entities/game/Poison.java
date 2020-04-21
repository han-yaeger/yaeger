package nl.han.waterworld.entities.game;

import nl.han.waterworld.scenes.levels.Level;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.Location;

public class Poison extends Bubble {

    private static final String IMAGES_POISON_PNG = "waterworld/images/poison.png";

    public Poison(final Location location, final double speed, final Level level) {
        super(location, IMAGES_POISON_PNG, speed, level);
    }

    @Override
    public void onCollision(Collider collidingObject) {
        super.onCollision(collidingObject);

        if (collidingObject instanceof Player) {
            handlePlayerCollision();

            Player player = (Player) collidingObject;
            player.doDamage();
        }
    }
}
