package nl.han.ica.yaeger.gameobjects.interfaces;

import nl.han.ica.yaeger.delegates.CollisionSide;

public interface Collided extends Bounded {

    void hasCollidedWith(Collider collidingObject, CollisionSide collisionSide);
}
