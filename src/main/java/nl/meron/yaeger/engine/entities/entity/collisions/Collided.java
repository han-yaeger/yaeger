package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.meron.yaeger.engine.entities.entity.Bounded;

import java.util.Set;

/**
 * A {@link Collided} represents an {@link nl.meron.yaeger.engine.entities.entity.Entity} that can be collided with by a
 * {@link Collider}. In such a case, the {@link Collided} is the {@link nl.meron.yaeger.engine.entities.entity.Entity} that
 * gets notified of the collision.
 *
 * <p>Each Game world Update a {@link Collided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link nl.meron.yaeger.engine.scenes.YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link nl.meron.yaeger.engine.entities.entity.Entity} that really need to be part of the collision detection implement
 * the {@link Collided} of {@link Collider} interfaces. If an {@link nl.meron.yaeger.engine.entities.entity.Entity} is both
 * a {@link Collider} and {@link Collided}, implement the interface {@link Collidable}.
 * </p>
 */
public interface Collided extends Bounded {

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

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     */
    void onCollision(Collider collidingObject);

    private boolean collisionHasOccured(Collider collider) {
        return !this.equals(collider) && getTransformedBounds().intersects(collider.getTransformedBounds());
    }
}
