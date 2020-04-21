package nl.han.pong.scenes.entities;

import javafx.scene.input.KeyCode;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;

import java.util.Set;


public class PlayerOneBat extends Bat {

    /**
     * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
     */
    public PlayerOneBat(Location initialPosition) {
        super(initialPosition);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.A)) {
            setMotionTo(SPEED, Direction.UP.getValue());
        } else if (pressedKeys.contains(KeyCode.Z)) {
            setMotionTo(SPEED, Direction.DOWN.getValue());
        } else {
            setSpeedTo(0);
        }
    }
}
