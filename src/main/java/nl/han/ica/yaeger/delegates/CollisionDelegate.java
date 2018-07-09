package nl.han.ica.yaeger.delegates;

import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.gameobjects.interfaces.Collided;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;

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

    public void registerForCollisionDetection(GameObject collided) {
        if (collided instanceof Collider) {
            colliders.add((Collider) collided);
        }
        if (collided instanceof Collided) {
            collideds.add((Collided) collided);
        }
    }

    /**
     * Remove the GameObject from the list of Objects that are taken into account
     *
     * @param gameObject The GameObject that should be removed.
     */
    public void removeGameObject(GameObject gameObject) {
        if (gameObject instanceof Collider) {
            removeCollider((Collider) gameObject);
        }
        if (gameObject instanceof Collided) {
            removeCollided((Collided) gameObject);
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
