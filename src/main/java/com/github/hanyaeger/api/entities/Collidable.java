package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.Bounded;
import com.github.hanyaeger.api.scenes.YaegerScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A {@link Collidable} is a {@link YaegerEntity} that can be collided with by a
 * {@link Collidable}. In such a case, the {@link Collidable} is the {@link YaegerEntity} that
 * gets notified of the collision.
 *
 * <p>There are different types of collision detection. In the case of an {@link Collidable} we perform a so called
 * <b>Axis-Aligned Bounding Box</b> collision detection, which is based on the bounding box of the {@link YaegerEntity}.
 * </p>
 *
 * <p>Each Game World Update a {@link Collidable} is checked against all instances of{@link Collidable}. If many instances
 * of {@link Collidable} are part of the {@link YaegerScene}, which could lead to many calculations, which could slow
 * down the game and framerate. So make sure only those instances of {@link YaegerEntity} that really need to be part
 * of the collision detection to implement the {@link Collidable} or {@link Collidable} interfaces.
 * </p>
 */
public interface Collidable extends Bounded {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObjects a {@link List} of all instances of {@link Collidable} this {@link Collidable} has collided
     *                         with, during the last Game World Update.
     */
    void onCollision(final List<Collidable> collidingObjects);

    /**
     * Perform collision detection with a {@link Set} of {@link Collidable} instances. Only the first collision
     * is detected.
     * <p>
     * Note that all of this takes place during the same Game World update. The re-rendering takes place after this update
     * completes, meaning the undoing will cause no jitter effect.
     * <p>
     * Note: This method is part of the internal API, and should not be used when implementing a Yaeger game.
     *
     * @param colliders a {@link Set} of colliders that should be checked for collisions
     */
    default void checkForCollisions(final List<Collidable> colliders) {
        if (colliders == null) {
            return;
        }

        if (colliders.isEmpty()) {
            return;
        }

        var colliderList = new ArrayList<Collidable>();

        for (var collider : colliders) {
            if (collider.equals(this)) {
                continue;
            }

            if (hasCollidedWith(collider)) {
                colliderList.add(collider);
            }
        }

        if (!colliderList.isEmpty()) {
            onCollision(colliderList);
        }
    }

    private boolean hasCollidedWith(final Collidable collider) {
        return !this.equals(collider) && getBoundingBox().intersects(collider.getBoundingBox());
    }
}
