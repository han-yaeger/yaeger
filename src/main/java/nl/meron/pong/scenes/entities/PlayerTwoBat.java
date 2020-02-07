package nl.meron.pong.scenes.entities;

import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;

import java.util.Set;


public class PlayerTwoBat extends Bat {

    /**
     * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
     */
    public PlayerTwoBat(Point initialPosition) {
        super(initialPosition);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.K)) {
            setMotionTo(SPEED, Direction.UP.getValue());
        } else if (pressedKeys.contains(KeyCode.M)) {
            setMotionTo(SPEED, Direction.DOWN.getValue());
        } else {
            setSpeedTo(0);
        }
    }
}
