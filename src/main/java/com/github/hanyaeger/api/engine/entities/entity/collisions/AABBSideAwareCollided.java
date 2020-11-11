package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * A {@link AABBSideAwareCollided} represents an {@link YaegerEntity} that can be collided with by a
 * {@link AABBCollider} and that is aware of which of its side the {@link AABBCollider} had collided with. In such a case,
 * the {@link AABBSideAwareCollided} is the {@link YaegerEntity} thatgets notified of the collision and of the side.
 *
 * <p>Each Game world Update a {@link AABBSideAwareCollided} is checked against all instances of{@link AABBCollider}. If many instances
 * of {@link AABBCollider} are part of the {@link YaegerScene}, this
 * could lead to many calculations, which could slow down the game and framerate. Thus ensure only those instances of
 * {@link YaegerEntity} that really need to be part of the collision detection implement
 * the {@link AABBSideAwareCollided} or {@link AABBCollider} interfaces.
 * <p>
 * If it is not required to know the side of the collision, implement the {@link AABBCollided} interface, which will save you valuable
 * processor time.</p>
 */
public interface AABBSideAwareCollided extends AABBCollided {

    /**
     * This method is called if a collision has occurred.
     *
     * @param collidingObject the EntityCollection you are colliding with
     * @param side            the side with which a collision has occurred
     */
    void onCollision(final AABBCollider collidingObject, final CollisionSide side);

    @Override
    default void onCollision(final AABBCollider AABBCollider) {
        var side = findCollisionSide(AABBCollider.getNonTransformedBounds());

        onCollision(AABBCollider, side);
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
