package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Cursor;
import nl.meron.yaeger.engine.Initializable;

/**
 * An {@code Entity} will serve as the {@code Root Interface} for all objects that are part of a
 * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
 */
public interface Entity extends Initializable, Bounded, Removeable, Placeable, SceneChild, NodeProvider {

    /**
     * Set if this {@link Entity} should be visible.
     *
     * @param visible A {@code boolean} stating whether this {@link Entity} should be visible
     */
    default void setVisible(boolean visible) {
        getGameNode().setVisible(visible);
    }

    /**
     * Set the cursor to the specified {@link Cursor} type.
     *
     * @param cursor The {@link Cursor} that should be used
     */
    default void setCursor(Cursor cursor) {
        getGameNode().getScene().setCursor(cursor);
    }

//    /**
//     * Set the initial position at which this {@link Entity} should be placed.
//     *
//     * @param initialPosition The {@link Point} at which this {@link Entity} should be placed
//     */
//    void setInitialPosition(Point initialPosition);
//
//    @Override
//    default void placeOnPosition(double x, double y) {
//
//        if (getGameNode() == null) {
//            setInitialPosition(new Point(x, y));
//        } else {
//            getGameNode().setLayoutX(x);
//            getGameNode().setLayoutY(y);
//        }
//    }


}
