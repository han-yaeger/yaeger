package nl.meron.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * A {@link SideCollided} represents an {@link nl.meron.yaeger.engine.entities.entity.Entity} that can be collided with by a
 * {@link Collider} and that is aware of which of its side the {@link Collider} had collided with. In such a case,
 * the {@link SideCollided} is the {@link nl.meron.yaeger.engine.entities.entity.Entity} thatgets notified of the collision and of the side.
 *
 * <p>Each Game world Update a {@link SideCollided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link nl.meron.yaeger.engine.scenes.YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link nl.meron.yaeger.engine.entities.entity.Entity} that really need to be part of the collision detection implement
 * the {@link SideCollided} or {@link Collider} interfaces.
 * <p>
 * If it is not required to know the side of the collision, implement the {@link AABBCollided} interface, which will save you valuable
 * processortime.</p>
 */

public interface SideCollided extends AABBCollided {

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
