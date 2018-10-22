package nl.han.ica.yaeger.engine.entities.collisions;

import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Removeable;

import java.util.HashSet;
import java.util.Set;

/**
 * A CollisionDelegate handles all behavior related to Object collisions.
 */
public class CollisionDelegate {

    private Set<Collided> collideds;
    private Set<Collider> colliders;

    /**
     * Create a new CollisionDelegate.
     */
    public CollisionDelegate() {
        collideds = new HashSet<>();
        colliders = new HashSet<>();
    }

    /**
     * Register an {@link Entity} to be evaluated for collision detection. The {@link Entity} will only be added
     * if
     *
     * @param entity the {@link Entity} that should be registered
     */
    public void register(Entity entity) {
        if (entity instanceof Collider) {
            register((Collider) entity);
        }
        if (entity instanceof Collided) {
            register((Collided) entity);
        }
    }

    /**
     * Register a {@link Collider} to be evaluated for collision detection.
     *
     * @param collider the {@link Collider} that should be registered
     */
    public void register(Collider collider) {
        colliders.add(collider);
    }

    /**
     * Register a {@link Collided} to be evaluated for collision detection.
     *
     * @param collided the {@link Collided} that should be registered
     */
    public void register(Collided collided) {
        collideds.add(collided);
    }

    /**
     * Register a {@link Collidable} to be evaluated for collision detection.
     *
     * @param collidable the {@link Collidable} that should be registered
     */
    public void register(Collidable collidable) {
        collideds.add(collidable);
        colliders.add(collidable);
    }

    /**
     * Remove the {@link Removeable} from the list of Objects that are taken into account
     *
     * @param removeable The {@link Removeable} that should be removed.
     */
    public void remove(Removeable removeable) {
        if (removeable instanceof Collider) {
            removeCollider((Collider) removeable);
        }
        if (removeable instanceof Collided) {
            removeCollided((Collided) removeable);
        }
    }

    /**
     * Check for collisions. Each {@link Collidable} is asked to check for collisions.
     */
    public void checkCollisions() {
        collideds.forEach(collided -> collided.checkForCollisions(colliders));
    }

    private void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    private void removeCollided(Collided collided) {
        collideds.remove(collided);
    }
}
