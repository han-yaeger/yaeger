package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.levels.Level;
import nl.han.ica.yaeger.engine.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.entity.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class Poison extends Bubble {

    public Poison(final Position position, final double speed, final Level level) {
        super(position, "images/poison.png", speed, level);
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
