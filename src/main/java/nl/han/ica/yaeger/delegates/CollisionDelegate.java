package nl.han.ica.yaeger.delegates;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.gameobjects.interfaces.Collided;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;

import java.util.HashSet;
import java.util.Set;

/**
 * A CollisionDelegate handles all behavior related to Object collisions.
 */
public class CollisionDelegate {

    private Set<Collided> collidedGameObjects;
    private Set<Collider> colliderGameObjects;

    /**
     * Create a new CollisionDelegate.
     */
    public CollisionDelegate() {
        collidedGameObjects = new HashSet<>();
        colliderGameObjects = new HashSet<>();
    }

    public void registerForCollisionDetection(GameObject collided) {
        if (collided instanceof Collider) {
            colliderGameObjects.add((Collider) collided);
        }
        if (collided instanceof Collided) {
            collidedGameObjects.add((Collided) collided);
        }
    }

    /**
     * Remove the GameObject from the list of Objects that are taken into account
     *
     * @param gameObject The GameObject that should be removed.
     */
    public void removeGameObject(GameObject gameObject) {
        colliderGameObjects.remove(gameObject);
        collidedGameObjects.remove(gameObject);
    }

    /**
     * Check for collisions. If a collision is detected, only the Collided will be notified of this collision.
     */
    public void checkCollisions() {

        for (Collided collided : collidedGameObjects) {
            for (Collider collider : colliderGameObjects) {
                if (collisionHasOccured(collided, collider)) {
                    CollisionSide collisionSide = findCollisionSide(collided, collider);
                    collided.hasCollidedWith(collider, collisionSide);
                    break;
                }
            }
        }
    }

    private boolean collisionHasOccured(Collided collided, Collider collider) {
        return !collided.equals(collider) && collided.getBounds().intersects(collider.getBounds());
    }

    private CollisionSide findCollisionSide(Collided collided, Collider collider) {

        Bounds topBoundingBox = createTopCollisionBoundingBox(collided);
        Bounds bottomBoundingBox = createBottomCollisionBoundingBox(collided);
        Bounds leftBoundingBox = createLeftCollisionBoundingBox(collided);
        Bounds rightBoundingBox = createRightCollisionBoundingBox(collided);

        if (collider.getBounds().intersects(topBoundingBox)) {
            return CollisionSide.TOP;
        } else if (collider.getBounds().intersects(bottomBoundingBox)) {
            return CollisionSide.BOTTOM;
        } else if (collider.getBounds().intersects(leftBoundingBox)) {
            return CollisionSide.LEFT;
        } else if (collider.getBounds().intersects(rightBoundingBox)) {
            return CollisionSide.RIGHT;
        } else {
            return CollisionSide.NONE;
        }
    }

    private Bounds createTopCollisionBoundingBox(Collided collided) {
        Bounds collidedBounds = collided.getBounds();

        double minX = collidedBounds.getMinX();
        double minY = collidedBounds.getMaxY() - 1;
        double minZ = collidedBounds.getMinZ();
        double width = collidedBounds.getWidth();
        double height = 1;
        double depth = collidedBounds.getDepth();

        BoundingBox topBoundingBox = new BoundingBox(minX, minY, minZ, width, height, depth);

        return topBoundingBox;
    }

    private Bounds createBottomCollisionBoundingBox(Collided collided) {
        Bounds collidedBounds = collided.getBounds();

        double minX = collidedBounds.getMinX();
        double minY = collidedBounds.getMinY();
        double minZ = collidedBounds.getMinZ();
        double width = collidedBounds.getWidth();
        double height = 1;
        double depth = collidedBounds.getDepth();

        BoundingBox bottomBoundingBox = new BoundingBox(minX, minY, minZ, width, height, depth);

        return bottomBoundingBox;
    }

    private Bounds createLeftCollisionBoundingBox(Collided collided) {
        Bounds collidedBounds = collided.getBounds();

        double minX = collidedBounds.getMinX();
        double minY = collidedBounds.getMaxY();
        double minZ = collidedBounds.getMinZ();
        double width = 1;
        double height = collidedBounds.getHeight();
        double depth = collidedBounds.getDepth();

        BoundingBox leftBoundingBox = new BoundingBox(minX, minY, minZ, width, height, depth);

        return leftBoundingBox;
    }

    private Bounds createRightCollisionBoundingBox(Collided collided) {
        Bounds collidedBounds = collided.getBounds();

        double minX = collidedBounds.getMaxX() - 1;
        double minY = collidedBounds.getMinY();
        double minZ = collidedBounds.getMinZ();
        double width = 1;
        double height = collidedBounds.getHeight();
        double depth = collidedBounds.getDepth();

        BoundingBox rightBoundingBox = new BoundingBox(minX, minY, minZ, width, height, depth);

        return rightBoundingBox;
    }


}
