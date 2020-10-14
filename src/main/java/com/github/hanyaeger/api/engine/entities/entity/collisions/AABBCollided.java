package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.motion.Rotatable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.Bounded;
import com.github.hanyaeger.api.engine.entities.entity.motion.Moveable;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

import java.util.List;
import java.util.Set;

/**
 * A {@link AABBCollided} represents an non-rotatable {@link YaegerEntity} that can be collided with by a
 * {@link AABBCollider}. In such a case, the {@link AABBCollided} is the {@link YaegerEntity} that
 * gets notified of the collision.
 *
 * <p>There are different types of collision detection. In the case of an {@link AABBCollided} we perform so called
 * <b>Axis-Aligned Bounding Box</b> collision detection, which required the instances of {@link YaegerEntity}
 * to not be {@link Rotatable}. </p>
 *
 * <p>Each Game world Update a {@link AABBCollided} is checked against all instances of{@link AABBCollider}. If many instances
 * of {@link AABBCollider} are part of the {@link YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link YaegerEntity} that really need to be part of the collision detection implement
 * the {@link AABBCollided} or {@link AABBCollider} interfaces.
 * </p>
 */
public interface AABBCollided extends Bounded, Moveable {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     */
    void onCollision(AABBCollider collidingObject);

    /**
     * Perform collision detection with a {@link Set} of {@link AABBCollider} instances. Only the first collision
     * is detected.
     * <p>
     * In case of a collision, the event handler {@link #onCollision(AABBCollider)} and {@link #undoUpdate()} are
     * called. From the {@link #onCollision(AABBCollider)} event handler, it is possible that the speed of the Entity
     * is set to 0. Because the Entity has already been relocated, this relocation should be undone to keep the Entity
     * at the same location. Hence {@link #undoUpdate()} is called, which is responsible for undoing the last relocation.
     * <p>
     * Note that all of this takes place during the same Game World update. The re-rendering takes place after this update
     * completes, meaning the undoing will cause no jitter effect.
     *
     * @param AABBColliders A {@link Set} of colliders that should be checked for collisions.
     */
    default void checkForCollisions(List<AABBCollider> AABBColliders) {
        if (AABBColliders == null || AABBColliders.isEmpty()) {
            return;
        }

        for (AABBCollider AABBCollider : AABBColliders) {
            if (collisionHasOccured(AABBCollider)) {
                onCollision(AABBCollider);
                undoUpdate();
                break;
            }
        }
    }

    private boolean collisionHasOccured(AABBCollider AABBCollider) {
        return !this.equals(AABBCollider) && getTransformedBounds().intersects(AABBCollider.getTransformedBounds());
    }
}
