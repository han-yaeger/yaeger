package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shapebased.test.DynamicTestEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class SceneBorderTouchingDynamicTestEntity extends DynamicTestEntity implements SceneBorderTouchingWatcher {

    public static final String TEXT = "SceneBorderTouching";

    public SceneBorderTouchingDynamicTestEntity(final Point position) {
        super(position, TEXT);
        setMotionTo(6, Direction.RIGHT.getValue());
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirectionBy(180);
    }
}
