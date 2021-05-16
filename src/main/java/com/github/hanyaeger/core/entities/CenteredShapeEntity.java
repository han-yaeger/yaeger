package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.shape.Shape;

/**
 * A {@link CenteredShapeEntity} is a special case of a {@link ShapeEntity}, based on a {@link Shape}
 * of which the center is the reference point when placed on a {@link javafx.scene.Scene}. Because
 * Yaeger uses translations to facilitate various anchor points, these translations will need to
 * differ from shapes, of which the top left point is used for placement.
 *
 * @param <T> The Generic type to be used, which should extend {@link Shape}.
 */
public abstract class CenteredShapeEntity<T extends Shape> extends ShapeEntity<T> {

    /**
     * Instantiate a new {@link CenteredShapeEntity} for the given {@link Coordinate2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@link CenteredShapeEntity}
     */
    protected CenteredShapeEntity(Coordinate2D initialPosition) {
        super(initialPosition);
    }

    @Override
    public void applyTranslationsForAnchorPoint() {
        getNode().ifPresent(node -> {

            var localBounds = getNode().get().getBoundsInLocal();
            switch (getAnchorPoint()) {
                case TOP_LEFT:
                    node.setTranslateX(localBounds.getWidth() / 2);
                    node.setTranslateY(localBounds.getHeight() / 2);
                    break;
                case TOP_CENTER:
                    node.setTranslateY(localBounds.getHeight() / 2);
                    break;
                case TOP_RIGHT:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(localBounds.getHeight() / 2);
                    break;
                case CENTER_LEFT:
                    node.setTranslateX(localBounds.getWidth() / 2);
                    break;
                case CENTER_RIGHT:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    break;
                case BOTTOM_LEFT:
                    node.setTranslateX(localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case BOTTOM_CENTER:
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case BOTTOM_RIGHT:
                    node.setTranslateX(-localBounds.getWidth() / 2);
                    node.setTranslateY(-localBounds.getHeight() / 2);
                    break;
                case CENTER_CENTER:
                default:
                    break;
            }
        });
    }
}
