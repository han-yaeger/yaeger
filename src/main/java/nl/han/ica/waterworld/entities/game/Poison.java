package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.entities.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class Poison extends Bubble {

    private static final String IMAGES_POISON_PNG = "images/poison.png";

    public Poison(final Position position, final double speed, final Level level) {
        super(position, IMAGES_POISON_PNG, speed, level);
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        super.onCollision(collidingObject, collisionSide);

        if (collidingObject instanceof Player) {
            handlePlayerCollision();

            Player player = (Player) collidingObject;
            player.doDamage();
        }
    }
}
