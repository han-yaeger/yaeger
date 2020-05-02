package nl.han.yaeger.engine.entities.entity.collisions;

import nl.han.yaeger.engine.entities.entity.Bounded;

import java.util.Set;

/**
 * A {@link AABBCollided} represents an non-rotatable {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that can be collided with by a
 * {@link Collider}. In such a case, the {@link AABBCollided} is the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that
 * gets notified of the collision.
 *
 * <p>There are different types of collision detection. In the case of an {@link AABBCollided} we perform so called
 * <b>Axis-Aligned Bounding Box</b> collision detection, which required the instances of {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}
 * to not be {@link nl.han.yaeger.engine.entities.entity.Rotatable}. </p>
 *
 * <p>Each Game world Update a {@link AABBCollided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link nl.han.yaeger.engine.scenes.YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that really need to be part of the collision detection implement
 * the {@link AABBCollided} or {@link Collider} interfaces.
 * </p>
 */
public interface AABBCollided extends Bounded {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     */
    void onCollision(Collider collidingObject);

    /**
     * Perform collision detection with a {@link Set} of {@link Collider} instances. Only the first collision
     * is detected.
     *
     * @param colliders A {@link Set} of colliders that should be checked for collisions.
     */
    default void checkForCollisions(Set<Collider> colliders) {
        if (colliders == null || colliders.isEmpty()) {
            return;
        }

        for (Collider collider : colliders) {
            if (collisionHasOccured(collider)) {
                onCollision(collider);
                break;
            }
        }
    }

    private boolean collisionHasOccured(Collider collider) {
        return !this.equals(collider) && getTransformedBounds().intersects(collider.getTransformedBounds());
    }
}
