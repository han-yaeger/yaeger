package nl.han.ica.waterworld.gameobjects;

import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

import java.util.Set;

public class Player extends UpdatableSpriteObject implements KeyListener, Collider {

    public Player(double x, double y) {
        super("images/player.png", x, y, 2, 0, 0);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.LEFT)) {
            setCurrentFrameIndex(0);
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCurrentFrameIndex(1);
        }
    }
}
