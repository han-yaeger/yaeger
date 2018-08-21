package nl.han.ica.waterworld.entities.spel;

import nl.han.ica.waterworld.scenes.GameScene;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.entities.interfaces.Collider;

public class Poison extends Bubble {

    public Poison(double x, double y, final double speed, GameScene waterworld) {
        super("images/poison.png", x, y, speed, waterworld);
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
