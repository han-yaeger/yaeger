package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;

public class Poison extends Bubble {

    public Poison(double x, double y, final double speed, Waterworld waterworld) {
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
