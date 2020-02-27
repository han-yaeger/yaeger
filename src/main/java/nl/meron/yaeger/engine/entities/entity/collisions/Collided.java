package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import nl.meron.yaeger.engine.entities.entity.Bounded;

import java.util.Set;

/**
 * A {@link Collided} represents a Entity that can be collided with by a {@link Collider}
 */
public interface Collided extends Bounded {

    /**
     * Perform collision detection with a {@link Set} of {@link Collider}s. Only the first collision
     * is detected.
     *
     * @param colliders A {@link Set} of colliders that should be checked for collisions.
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
     * @param collisionSide   The side of the collision.
     */
    void onCollision(Collider collidingObject, CollisionSide collisionSide);

    private boolean collisionHasOccured(Collider collider) {
        return getNonTransformedBounds().intersects(collider.getNonTransformedBounds()) && !this.equals(collider);
    }

    private CollisionSide findCollisionSide(Collider collider) {

        if (collider.getNonTransformedBounds().intersects(createTopCollisionBoundingBox())) {
            return CollisionSide.TOP;
        } else if (collider.getNonTransformedBounds().intersects(createBottomCollisionBoundingBox())) {
            return CollisionSide.BOTTOM;
        } else if (collider.getNonTransformedBounds().intersects(createLeftCollisionBoundingBox())) {
            return CollisionSide.LEFT;
        } else if (collider.getNonTransformedBounds().intersects(createRightCollisionBoundingBox())) {
            return CollisionSide.RIGHT;
        } else {
            return CollisionSide.NONE;
        }
    }

    private Bounds createTopCollisionBoundingBox() {
        double minX = getNonTransformedBounds().getMinX();
        double minY = getNonTransformedBounds().getMaxY() - 1;
        double minZ = getNonTransformedBounds().getMinZ();
        double width = getNonTransformedBounds().getWidth();
        double height = 1;
        double depth = getNonTransformedBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createBottomCollisionBoundingBox() {
        double minX = getNonTransformedBounds().getMinX();
        double minY = getNonTransformedBounds().getMinY();
        double minZ = getNonTransformedBounds().getMinZ();
        double width = getNonTransformedBounds().getWidth();
        double height = 1;
        double depth = getNonTransformedBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createLeftCollisionBoundingBox() {
        double minX = getNonTransformedBounds().getMinX();
        double minY = getNonTransformedBounds().getMinY();
        double minZ = getNonTransformedBounds().getMinZ();
        double width = 1;
        double height = getNonTransformedBounds().getHeight();
        double depth = getNonTransformedBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }

    private Bounds createRightCollisionBoundingBox() {
        double minX = getNonTransformedBounds().getMaxX() - 1;
        double minY = getNonTransformedBounds().getMinY();
        double minZ = getNonTransformedBounds().getMinZ();
        double width = 1;
        double height = getNonTransformedBounds().getHeight();
        double depth = getNonTransformedBounds().getDepth();

        return new BoundingBox(minX, minY, minZ, width, height, depth);
    }
}
