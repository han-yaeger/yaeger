package nl.meron.showcase.scenes.spriteentities.entities;

import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

import java.util.Set;

public class BasketBall extends DynamicSpriteEntity implements KeyListener {

    public BasketBall(Point point) {
        super("showcase/entities/basketball.png", point, new Size(60, 60), 0);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {

        if (pressedKeys.contains(KeyCode.T)) {

            var leftX = getLeftSideX();
            var rightX = getRightSideX();
            var pivotX = leftX + ((rightX - leftX) / 2);

            var topY = getTopY();
            var bottomY = getBottomY();
            var pivotY = topY + ((bottomY - topY) / 2);
            var rotate = new Rotate(45, pivotX, pivotY);

            getGameNode().getTransforms().add(rotate);

        }
    }
}
