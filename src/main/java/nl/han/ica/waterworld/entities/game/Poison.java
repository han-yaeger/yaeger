package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class Poison extends Bubble {

    public static final String IMAGES_POISON_PNG = "images/poison.png";

    public Poison(final Position position, final double speed, final Level level) {
        super(position, IMAGES_POISON_PNG, speed, level);
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        if (collidingObject instanceof Player) {
            handleCollision();

            Player player = (Player) collidingObject;
            player.doDamage();
        }
    }
}
