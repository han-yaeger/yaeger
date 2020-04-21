package nl.han.yaeger.engine.entities.entity.collisions;

/**
 * A {@link CollisionSide} denotes the side of the {@link AABBCollided}
 * with which the {@link Collider} has collided.
 */
public enum CollisionSide {
    TOP,
    BOTTOM,
    RIGHT,
    LEFT,
    UNKNOWN
}
