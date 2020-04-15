package nl.meron.showcase.scenes.spawner.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Raindrop extends DynamicRectangleEntity implements SceneBorderCrossingWatcher {

    public Raindrop(final Location initialPosition, final double speed, final double width, final double height) {
        super(initialPosition);
        setWidth(width);
        setHeight(height);
        setOpacity(0.4);
        setFill(Color.WHITESMOKE);
        setStrokeWidth(0);
        setMotionTo(speed, Direction.DOWN.getValue());
    }

    @Override
    public void notifyBoundaryCrossing(final SceneBorder border) {
        remove();
    }
}
