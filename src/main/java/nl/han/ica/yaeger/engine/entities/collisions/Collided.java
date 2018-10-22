package nl.han.ica.yaeger.engine.entities.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.han.ica.yaeger.engine.entities.entity.Bounded;

import java.util.Set;

/**
 * A {@code Collided} represents a Entity that can be collided with by a {@code Collider}
 */
public interface Collided extends Bounded {

    /**
     * Perform collision detection with a {@code Set} of {@code Collider}s. Only the first collision
     * is detected.
     *
     * @param colliders A {@code Set} of colliders that should be checked for collisions.
     */
    default void checkForCollisions(Set<Collider> colliders) {
        if (colliders == null || colliders.isEmpty()) {
            return;
        }

        for (Collider collider : colliders) {
            if (collisionHasOccured(collider)) {
                CollisionSide collisionSide = findCollisionSide(collider);
                onCollision(collider, collisionSide);
                break;
            }
        }
    }

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     * @param collisionSide     The side of the collision.
     */
    void onCollision(Collider collidingObject, CollisionSide collisionSide);

    private boolean collisionHasOccured(Collider collider) {
        return getBounds().intersects(collider.getBounds()) && !this.equals(collider);
    }

    private CollisionSide findCollisionSide(Collider collider) {

        if (collider.getBounds().intersects(createTopCollisionBoundingBox())) {
            return CollisionSide.TOP;
        } else if (collider.getBounds().intersects(createBottomCollisionBoundingBox())) {
            return CollisionSide.BOTTOM;
        } else if (collider.getBounds().intersects(createLeftCollisionBoundingBox())) {
            return CollisionSide.LEFT;
        } else if (collider.getBounds().intersects(createRightCollisionBoundingBox())) {
            return CollisionSide.RIGHT;
        } else {
            return CollisionSide.NONE;
        }
    }

    private Bounds createTopCollisionBoundingBox() {
        double minX = getBounds().getMinX();
        double minY = getBounds().getMaxY() - 1;
        double minZ = getBounds().getMinZ();
        double width = getBounds().getWidth();
        double height = 1;
        double depth = getBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createBottomCollisionBoundingBox() {
        double minX = getBounds().getMinX();
        double minY = getBounds().getMinY();
        double minZ = getBounds().getMinZ();
        double width = getBounds().getWidth();
        double height = 1;
        double depth = getBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createLeftCollisionBoundingBox() {
        double minX = getBounds().getMinX();
        double minY = getBounds().getMinY();
        double minZ = getBounds().getMinZ();
        double width = 1;
        double height = getBounds().getHeight();
        double depth = getBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createRightCollisionBoundingBox() {
        double minX = getBounds().getMaxX() - 1;
        double minY = getBounds().getMinY();
        double minZ = getBounds().getMinZ();
        double width = 1;
        double height = getBounds().getHeight();
        double depth = getBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }
}
