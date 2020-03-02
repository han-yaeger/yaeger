package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public interface SideCollided extends Collided {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     */
    void onCollision(Collider collidingObject, CollisionSide side);

    @Override
    default void onCollision(Collider collider) {

        var side = findCollisionSide(collider.getNonTransformedBounds());

        onCollision(collider, side);
    }

    private CollisionSide findCollisionSide(Bounds bounds) {

        if (bounds.intersects(createTopCollisionBoundingBox())) {
            return CollisionSide.TOP;
        } else if (bounds.intersects(createBottomCollisionBoundingBox())) {
            return CollisionSide.BOTTOM;
        } else if (bounds.intersects(createLeftCollisionBoundingBox())) {
            return CollisionSide.LEFT;
        } else if (bounds.intersects(createRightCollisionBoundingBox())) {
            return CollisionSide.RIGHT;
        } else {
            return CollisionSide.UNKNOWN;
        }
    }

    private Bounds createTopCollisionBoundingBox() {
        var bounds = getTransformedBounds();

        return new BoundingBox(bounds.getMinX(), bounds.getMaxY() - 1, bounds.getMinZ(), bounds.getWidth(), 1, bounds.getDepth());
    }

    private Bounds createBottomCollisionBoundingBox() {
        var bounds = getTransformedBounds();

        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ(), bounds.getWidth(), 1, bounds.getDepth());
    }

    private Bounds createLeftCollisionBoundingBox() {
        var bounds = getTransformedBounds();

        return new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ(), 1, bounds.getHeight(), bounds.getDepth());
    }

    private Bounds createRightCollisionBoundingBox() {
        var bounds = getTransformedBounds();
        return new BoundingBox(bounds.getMaxX() - 1, bounds.getMinY(), bounds.getMinZ(), 1, bounds.getHeight(), bounds.getDepth());
    }
}
