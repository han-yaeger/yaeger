package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.YaegerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CollisionDelegate} handles all behavior related to Object collisions.
 */
public class CollisionDelegate {

    private final List<Collided> collideds;
    private final List<Collider> colliders;

    /**
     * Create a new {@link CollisionDelegate}.
     */
    public CollisionDelegate() {
        collideds = new ArrayList<>();
        colliders = new ArrayList<>();
    }

    /**
     * Register an {@link YaegerEntity} to be evaluated for collision detection. The {@link YaegerEntity} will only be added
     * if is an {@link Collider} or {@link Collided}.
     *
     * @param entity the {@link YaegerEntity} that should be registered
     * @return a {@code boolean} stated whether this {@link YaegerEntity} is either a {@link Collider} or a {@link Collided}
     */
    public boolean register(final YaegerEntity entity) {
        var registered = false;

        if (entity instanceof Collider collider) {
            register(collider);
            registered = true;
        }
        if (entity instanceof Collided collided) {
            register(collided);
            registered = true;
        }

        return registered;
    }

    /**
     * Register a {@link Collider} to be evaluated for collision detection.
     *
     * @param collider the {@link Collider} that should be registered
     */
    public void register(final Collider collider) {
        colliders.add(collider);
    }

    /**
     * Register a {@link Collided} to be evaluated for collision detection.
     *
     * @param collided the {@link Collided} that should be registered
     */
    public void register(final Collided collided) {
        collideds.add(collided);
    }

    /**
     * Remove the {@link Removable} from the list of Objects that are taken into account
     *
     * @param removable The {@link Removable} that should be removed.
     */
    public void remove(final Removable removable) {
        if (removable instanceof Collider collider) {
            removeCollider(collider);
        }
        if (removable instanceof Collided collided) {
            removeCollided(collided);
        }
    }

    /**
     * Check for collisions. Each {@link Collided} is asked to check for collisions.
     */
    public void checkCollisions() {
        collideds.forEach(collided -> collided.checkForCollisions(colliders));
    }

    private void removeCollider(final Collider collider) {
        colliders.remove(collider);
    }

    private void removeCollided(final Collided collided) {
        collideds.remove(collided);
    }
}
