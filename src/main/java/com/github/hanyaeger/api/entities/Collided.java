package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.Bounded;
import com.github.hanyaeger.api.scenes.YaegerScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A {@link Collided} is a {@link YaegerEntity} that can be collided with by a
 * {@link Collider}. In such a case, the {@link Collided} is the {@link YaegerEntity} that
 * gets notified of the collision.
 *
 * <p>There are different types of collision detection. In the case of an {@link Collided} we perform a so called
 * <b>Axis-Aligned Bounding Box</b> collision detection, which is based on the bounding box of the {@link YaegerEntity}.
 * </p>
 *
 * <p>Each Game World Update a {@link Collided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link YaegerScene}, which could lead to many calculations, which could slow
 * down the game and framerate. So make sure only those instances of {@link YaegerEntity} that really need to be part
 * of the collision detection to implement the {@link Collided} or {@link Collider} interfaces.
 * </p>
 */
public interface Collided extends Bounded {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObjects a {@link List} of all instances of {@link Collider} this {@link Collided} has collided
     *                         with, during the last Game World Update.
     */
    void onCollision(final List<Collider> collidingObjects);

    /**
     * Perform collision detection with a {@link Set} of {@link Collider} instances. Only the first collision
     * is detected.
     * <p>
     * Note that all of this takes place during the same Game World update. The re-rendering takes place after this update
     * completes, meaning the undoing will cause no jitter effect.
     *
     * @param colliders a {@link Set} of colliders that should be checked for collisions
     */
    default void checkForCollisions(final List<Collider> colliders) {
        if (colliders == null || colliders.isEmpty()) {
            return;
        }

        var colliderList = new ArrayList<Collider>();

        for (var collider : colliders) {
            if (hasCollidedWith(collider)) {
                colliderList.add(collider);
            }
        }

        if (!colliderList.isEmpty()) {
            onCollision(colliderList);
        }
    }

    private boolean hasCollidedWith(final Collider collider) {
        return !this.equals(collider) && getBoundingBox().intersects(collider.getBoundingBox());
    }
}
