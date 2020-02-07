package nl.meron.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class DynamicRectangle extends DynamicRectangleEntity implements SceneBorderTouchingWatcher {

    public DynamicRectangle(Point initialPosition) {
        super(initialPosition);
        setWidth(40);
        setHeight(80);
        setFill(Color.PALEGREEN);
        setStrokeColor(Color.SPRINGGREEN);
        setStrokeWidth(4);
        setMotionTo(5, Direction.RIGHT.getValue());
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirectionBy(180);
    }
}
