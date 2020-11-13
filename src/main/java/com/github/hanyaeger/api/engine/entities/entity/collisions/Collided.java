package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.motion.Rotatable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.Bounded;
import com.github.hanyaeger.api.engine.entities.entity.motion.Moveable;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

import java.util.List;
import java.util.Set;

/**
 * A {@link Collided} represents an non-rotatable {@link YaegerEntity} that can be collided with by a
 * {@link Collider}. In such a case, the {@link Collided} is the {@link YaegerEntity} that
 * gets notified of the collision.
 *
 * <p>There are different types of collision detection. In the case of an {@link Collided} we perform so called
 * <b>Axis-Aligned Bounding Box</b> collision detection, which required the instances of {@link YaegerEntity}
 * to not be {@link Rotatable}. </p>
 *
 * <p>Each Game world Update a {@link Collided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link YaegerEntity} that really need to be part of the collision detection implement
 * the {@link Collided} or {@link Collider} interfaces.
 * </p>
 */
public interface Collided extends Bounded, Moveable {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject the EntityCollection you are colliding with
     */
    void onCollision(final Collider collidingObject);

    /**
     * Perform collision detection with a {@link Set} of {@link Collider} instances. Only the first collision
     * is detected.
     * <p>
     * In case of a collision, the event handler {@link #onCollision(Collider)} and {@link #undoUpdate()} are
     * called. From the {@link #onCollision(Collider)} event handler, it is possible that the speed of the Entity
     * is set to 0. Because the Entity has already been relocated, this relocation should be undone to keep the Entity
     * at the same location. Hence {@link #undoUpdate()} is called, which is responsible for undoing the last relocation.
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

        for (final Collider collider : colliders) {
            if (hasCollidedWith(collider)) {
                onCollision(collider);
                undoUpdate();
                break;
            }
        }
    }

    private boolean hasCollidedWith(final Collider collider) {
        return !this.equals(collider) && getBoundsInScene().intersects(collider.getBoundsInScene());
    }
}
