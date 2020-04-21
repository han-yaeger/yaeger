package nl.han.showcase.scenes.spawner.entities;

import javafx.scene.paint.Color;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import nl.han.yaeger.engine.scenes.SceneBorder;

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
