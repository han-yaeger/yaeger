package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

import java.util.Set;

public class Player extends UpdatableSpriteObject implements KeyListener, Collider {

    public Player(double x, double y) {
        super("images/player.png", x, y, 0, 0);
    }

    @Override
    public void onPressedKeysChange(Set<String> pressedKeys) {
        System.out.println("new keys pressed" + pressedKeys.toString());
    }
}
