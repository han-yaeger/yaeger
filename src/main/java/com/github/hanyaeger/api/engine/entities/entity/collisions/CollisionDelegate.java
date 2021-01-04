package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.Removeable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

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

        if (entity instanceof Collider) {
            register((Collider) entity);
            registered = true;
        }
        if (entity instanceof Collided) {
            register((Collided) entity);
            registered = true;
        }

        return registered;
    }

    /**
     * Register a {@link Collider} to be evaluated for collision detection.
     *
     * @param Collider the {@link Collider} that should be registered
     */
    public void register(final Collider Collider) {
        colliders.add(Collider);
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
     * Remove the {@link Removeable} from the list of Objects that are taken into account
     *
     * @param removeable The {@link Removeable} that should be removed.
     */
    public void remove(final Removeable removeable) {
        if (removeable instanceof Collider) {
            removeCollider((Collider) removeable);
        }
        if (removeable instanceof Collided) {
            removeCollided((Collided) removeable);
        }
    }

    /**
     * Check for collisions. Each {@link Collided} is asked to check for collisions.
     */
    public void checkCollisions() {
        collideds.forEach(collided -> collided.checkForCollisions(colliders));
    }

    private void removeCollider(final Collider Collider) {
        colliders.remove(Collider);
    }

    private void removeCollided(final Collided collided) {
        collideds.remove(collided);
    }
}
