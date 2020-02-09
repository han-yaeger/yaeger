package nl.meron.showcase.scenes.spriteentities.entities;

import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

import java.util.Set;

public class BasketBall extends DynamicSpriteEntity implements KeyListener {

    public BasketBall(Location location) {
        super("showcase/entities/basketball.png", location, new Size(60, 60), 0);
        setRotationSpeed(1);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {

        if (pressedKeys.contains(KeyCode.T)) {

            var leftX = getLeftX();
            var rightX = getRightX();
            var pivotX = leftX + ((rightX - leftX) / 2);

            var topY = getTopY();
            var bottomY = getBottomY();
            var pivotY = topY + ((bottomY - topY) / 2);
            var rotate = new Rotate(45, pivotX, pivotY);

            getGameNode().getTransforms().add(rotate);

        }
    }
}
