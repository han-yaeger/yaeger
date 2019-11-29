package nl.meron.pong;

import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;

import java.util.Set;

public class PlayerOneBat extends PongBat {

    public PlayerOneBat(Point initialPoint, Size size) {
        super(initialPoint, size);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.A)) {
            setSpeed(SPEED);
            setDirection(MotionVector.Direction.UP);
        } else if (pressedKeys.contains(KeyCode.Z)) {
            setSpeed(SPEED);
            setDirection(MotionVector.Direction.DOWN);
        }  else {
            setSpeed(0);
        }
    }
}
