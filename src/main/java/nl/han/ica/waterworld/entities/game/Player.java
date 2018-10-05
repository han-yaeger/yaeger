package nl.han.ica.waterworld.entities.game;

import javafx.scene.input.KeyCode;
import nl.han.ica.waterworld.scenes.Level;
import nl.han.ica.yaeger.engine.collisions.CollisionSide;
import nl.han.ica.yaeger.engine.entities.entity.sprites.BoundingBox;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Movement;
import nl.han.ica.yaeger.engine.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.engine.entities.entity.Collidable;
import nl.han.ica.yaeger.engine.entities.entity.Collider;
import nl.han.ica.yaeger.engine.KeyListener;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

import java.util.Set;

public class Player extends UpdatableSpriteEntity implements KeyListener, Collidable {

    private int health;
    private Level level;

    public Player(final Level level, final int health) {
        super("images/player.png", new BoundingBox(40, 40), 2, new Movement(0, 0));
        this.level = level;
        this.health = health;
    }

    void doDamage() {
        health--;
        level.setHealthText(health);
        if (health <= 0) {
            level.playerDied();
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
            setDirection(360);
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

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        // Not needed
    }
}
