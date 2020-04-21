package nl.han.waterworld.entities.game;

import javafx.scene.input.KeyCode;
import nl.han.waterworld.scenes.levels.Level;
import nl.han.yaeger.engine.entities.entity.collisions.AABBCollided;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

import java.util.Set;

public class Player extends DynamicSpriteEntity implements KeyListener, AABBCollided, Collider {

    private static final String IMAGES_PLAYER_PNG = "waterworld/images/player.png";
    private int health;
    private Level level;

    public Player(final Location location, final Level level, final int health) {
        super(IMAGES_PLAYER_PNG, location, new Size(20, 40), 2);
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
            setMotionTo(3, Direction.LEFT.getValue());
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCurrentFrameIndex(1);
            setMotionTo(3, Direction.RIGHT.getValue());
        } else if (pressedKeys.contains(KeyCode.UP)) {
            setMotionTo(3, Direction.UP.getValue());
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotionTo(3, Direction.DOWN.getValue());
        } else if (pressedKeys.isEmpty()) {
            setSpeedTo(0);
        }
    }

    @Override
    public void onCollision(Collider collidingObject) {
        if (collidingObject instanceof Swordfish || collidingObject instanceof AnimatedShark) {
            doDamage();
        }
    }
}
