package nl.han.yaeger.engine.entities.entity.collisions;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * A {@link AABBSideAwareCollided} represents an {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that can be collided with by a
 * {@link Collider} and that is aware of which of its side the {@link Collider} had collided with. In such a case,
 * the {@link AABBSideAwareCollided} is the {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} thatgets notified of the collision and of the side.
 *
 * <p>Each Game world Update a {@link AABBSideAwareCollided} is checked against all instances of{@link Collider}. If many instances
 * of {@link Collider} are part of the {@link nl.han.yaeger.engine.scenes.YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that really need to be part of the collision detection implement
 * the {@link AABBSideAwareCollided} or {@link Collider} interfaces.
 * <p>
 * If it is not required to know the side of the collision, implement the {@link AABBCollided} interface, which will save you valuable
 * processor time.</p>
 */
public interface AABBSideAwareCollided extends AABBCollided {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject The EntityCollection you are colliding with.
     * @param side            The side with which a collision has occurred.
     */
    void onCollision(final Collider collidingObject, final CollisionSide side);

    @Override
    default void onCollision(final Collider collider) {
        var side = findCollisionSide(collider.getNonTransformedBounds());

        onCollision(collider, side);
    }

    private CollisionSide findCollisionSide(final Bounds colliderBounds) {

        var collidedBounds = getTransformedBounds();

        if (colliderBounds.intersects(createTopCollisionBoundingBox(collidedBounds))) {
            return CollisionSide.TOP;
        } else if (colliderBounds.intersects(createBottomCollisionBoundingBox(collidedBounds))) {
            return CollisionSide.BOTTOM;
        } else if (colliderBounds.intersects(createLeftCollisionBoundingBox(collidedBounds))) {
            return CollisionSide.LEFT;
        } else if (colliderBounds.intersects(createRightCollisionBoundingBox(collidedBounds))) {
            return CollisionSide.RIGHT;
        } else {
            return CollisionSide.UNKNOWN;
        }
    }

    private Bounds createTopCollisionBoundingBox(final Bounds collidedBounds) {
        return new BoundingBox(collidedBounds.getMinX(), collidedBounds.getMaxY() - 1, collidedBounds.getMinZ(), collidedBounds.getWidth(), 1, collidedBounds.getDepth());
    }

    private Bounds createBottomCollisionBoundingBox(final Bounds collidedBounds) {
        return new BoundingBox(collidedBounds.getMinX(), collidedBounds.getMinY(), collidedBounds.getMinZ(), collidedBounds.getWidth(), 1, collidedBounds.getDepth());
    }

    private Bounds createLeftCollisionBoundingBox(final Bounds collidedBounds) {
        return new BoundingBox(collidedBounds.getMinX(), collidedBounds.getMinY(), collidedBounds.getMinZ(), 1, collidedBounds.getHeight(), collidedBounds.getDepth());
    }

    private Bounds createRightCollisionBoundingBox(final Bounds collidedBounds) {
        return new BoundingBox(collidedBounds.getMaxX() - 1, collidedBounds.getMinY(), collidedBounds.getMinZ(), 1, collidedBounds.getHeight(), collidedBounds.getDepth());
    }
}
