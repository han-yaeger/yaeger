package nl.meron.pong;

import javafx.scene.input.KeyCode;
import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.scenes.SceneBorder;

import java.util.Set;

public class PlayerOneBat extends PongBat {

    public PlayerOneBat(Position initialPosition, Size size) {
        super(initialPosition, size);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        setSpeed(0);
        System.out.println("Boundary Crossed");
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.A)) {
            setSpeed(SPEED);
            setDirection(MovementVector.Direction.UP);
        } else if (pressedKeys.contains(KeyCode.Z)) {
            setSpeed(SPEED);
            setDirection(MovementVector.Direction.DOWN);
        }
    }
}
