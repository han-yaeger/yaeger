package nl.han.ica.waterworld.entities.game;

import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.engine.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.interfaces.Collider;

public class Poison extends Bubble {

    public Poison(final double speed, Level level) {
        super("images/poison.png", speed, level);
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
