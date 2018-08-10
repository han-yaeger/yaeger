package nl.han.ica.waterworld.gameobjects;

import javafx.scene.input.KeyCode;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.delegates.CollisionSide;
import nl.han.ica.yaeger.gameobjects.interfaces.Collidable;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.interfaces.KeyListener;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

import java.util.Set;

public class Player extends UpdatableSpriteObject implements KeyListener, Collidable {

    private int health = 10;
    private Waterworld waterworld;

    public Player(final double x, final double y, final Waterworld waterworld) {
        super("images/player.png", x, y, 2, 0, 0);
        this.waterworld = waterworld;
    }

    public void doDamage() {
        health--;
        waterworld.setHealthText(health);
        if (health == 0) {
            waterworld.playerDied();
            remove();
        }
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.LEFT)) {
            setCurrentFrameIndex(0);
            setSpeed(3);
            setDirection(270);
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCurrentFrameIndex(1);
            setSpeed(3);
            setDirection(90);
        } else if (pressedKeys.contains(KeyCode.UP)) {
            setSpeed(3);
            setDirection(0);
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setSpeed(3);
            setDirection(180);
        } else if (pressedKeys.isEmpty()) {
            setSpeed(0);
        }
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        if (collidingObject instanceof Swordfish) {
            doDamage();
        }
    }
}
