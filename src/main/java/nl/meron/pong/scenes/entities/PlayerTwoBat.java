package nl.meron.pong.scenes.entities;

import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.Size;

import java.util.Set;

public class PlayerTwoBat extends PongBat {
    public PlayerTwoBat(Point initialPoint, Size size) {
        super(initialPoint, size);
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
