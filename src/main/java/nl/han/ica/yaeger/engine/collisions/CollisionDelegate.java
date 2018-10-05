package nl.han.ica.yaeger.engine.collisions;

import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Collided;
import nl.han.ica.yaeger.engine.entities.entity.Collider;

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

    public void registerForCollisionDetection(Entity collided) {
        if (collided instanceof Collider) {
            colliders.add((Collider) collided);
        }
        if (collided instanceof Collided) {
            collideds.add((Collided) collided);
        }
    }

    /**
     * Remove the Entity from the list of Objects that are taken into account
     *
     * @param entity The Entity that should be removed.
     */
    public void removeGameObject(Entity entity) {
        if (entity instanceof Collider) {
            removeCollider((Collider) entity);
        }
        if (entity instanceof Collided) {
            removeCollided((Collided) entity);
        }
    }

    /**
     * Check for collisions. Each collidble is asked to check for collisions.
     */
    public void checkCollisions() {
        collideds.stream().forEach(collided -> collided.checkForCollisions(colliders));
    }

    private void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    private void removeCollided(Collided collided) {
        collideds.remove(collided);
    }
}
