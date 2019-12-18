package nl.meron.waterworld.entities.game;

import javafx.scene.input.KeyCode;
import nl.meron.waterworld.scenes.levels.Level;
import nl.meron.yaeger.engine.entities.collisions.CollisionSide;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.collisions.Collidable;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

import java.util.Set;

public class Player extends DynamicSpriteEntity implements KeyListener, Collidable {

    private static final String IMAGES_PLAYER_PNG = "waterworld/images/player.png";
    private int health;
    private Level level;

    public Player(final Point point, final Level level, final int health) {
        super(IMAGES_PLAYER_PNG, point, new Size(20, 40), 2);
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
            setMotion(3, Direction.LEFT.getValue());
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCurrentFrameIndex(1);
            setMotion(3, Direction.RIGHT.getValue());
        } else if (pressedKeys.contains(KeyCode.UP)) {
            setMotion(3, Direction.UP.getValue());
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(3, Direction.DOWN.getValue());
        } else if (pressedKeys.isEmpty()) {
            setSpeed(0);
        }
    }

    @Override
    public void onCollision(Collider collidingObject, CollisionSide collisionSide) {
        if (collidingObject instanceof Swordfish || collidingObject instanceof AnimatedShark) {
            doDamage();
        }
    }

    @Override
    public void configure() {

    }
}
