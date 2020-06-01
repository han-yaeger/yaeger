package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.Removeable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * A CollisionDelegate handles all behavior related to Object collisions.
 */
public class CollisionDelegate {

    private Set<AABBCollided> collideds;
    private Set<AABBCollider> AABBColliders;

    /**
     * Create a new CollisionDelegate.
     */
    public CollisionDelegate() {
        collideds = new HashSet<>();
        AABBColliders = new HashSet<>();
    }

    /**
     * Register an {@link YaegerEntity} to be evaluated for collision detection. The {@link YaegerEntity} will only be added
     * if
     *
     * @param entity the {@link YaegerEntity} that should be registered
     */
    public void register(YaegerEntity entity) {
        if (entity instanceof AABBCollider) {
            register((AABBCollider) entity);
        }
        if (entity instanceof AABBCollided) {
            register((AABBCollided) entity);
        }
    }

    /**
     * Register a {@link AABBCollider} to be evaluated for collision detection.
     *
     * @param AABBCollider the {@link AABBCollider} that should be registered
     */
    public void register(AABBCollider AABBCollider) {
        AABBColliders.add(AABBCollider);
    }

    /**
     * Register a {@link AABBCollided} to be evaluated for collision detection.
     *
     * @param collided the {@link AABBCollided} that should be registered
     */
    public void register(AABBCollided collided) {
        collideds.add(collided);
    }

    /**
     * Remove the {@link Removeable} from the list of Objects that are taken into account
     *
     * @param removeable The {@link Removeable} that should be removed.
     */
    public void remove(Removeable removeable) {
        if (removeable instanceof AABBCollider) {
            removeCollider((AABBCollider) removeable);
        }
        if (removeable instanceof AABBCollided) {
            removeCollided((AABBCollided) removeable);
        }
    }

    /**
     * Check for collisions. Each {@link AABBCollided} is asked to check for collisions.
     */
    public void checkCollisions() {
        collideds.forEach(collided -> collided.checkForCollisions(AABBColliders));
    }

    private void removeCollider(AABBCollider AABBCollider) {
        AABBColliders.remove(AABBCollider);
    }

    private void removeCollided(AABBCollided collided) {
        collideds.remove(collided);
    }
}
