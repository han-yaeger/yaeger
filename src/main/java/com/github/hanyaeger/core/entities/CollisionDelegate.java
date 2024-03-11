package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.Collidable;
import com.github.hanyaeger.api.entities.YaegerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CollisionDelegate} handles all behavior related to Object collisions.
 */
public class CollisionDelegate {

    private final List<Collidable> collidables;

    /**
     * Create a new {@link CollisionDelegate}.
     */
    public CollisionDelegate() {
        collidables = new ArrayList<>();
    }

    /**
     * Register an {@link YaegerEntity} to be evaluated for collision detection. The {@link YaegerEntity} will only be added
     * if is an {@link Collidable} or {@link Collidable}.
     *
     * @param entity the {@link YaegerEntity} that should be registered
     * @return a {@code boolean} stated whether this {@link YaegerEntity} is either a {@link Collidable} or a {@link Collidable}
     */
    public boolean register(final YaegerEntity entity) {
        if (entity instanceof Collidable collidable) {
            register(collidable);
            return true;
        }
        return false;
    }

    /**
     * Register a {@link Collidable} to be evaluated for collision detection.
     *
     * @param collidable the {@link Collidable} that should be registered
     */
    public void register(final Collidable collidable) {
        collidables.add(collidable);
    }

    /**
     * Remove the {@link Removable} from the list of Objects that are taken into account
     *
     * @param removable The {@link Removable} that should be removed.
     */
    public void remove(final Removable removable) {
        if (removable instanceof Collidable collidable) {
            removeCollidable(collidable);
        }
    }

    /**
     * Check for collisions. Each {@link Collidable} is asked to check for collisions.
     */
    public void checkCollisions() {
        if (collidables.size() < 2) {
            return;
        }
        collidables.forEach(collided -> collided.checkForCollisions(collidables));
    }

    private void removeCollidable(final Collidable collidable) {
        collidables.remove(collidable);
    }
}
