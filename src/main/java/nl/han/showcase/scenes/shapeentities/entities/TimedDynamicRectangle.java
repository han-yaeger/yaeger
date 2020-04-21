package nl.han.showcase.scenes.shapeentities.entities;

import javafx.scene.paint.Color;
import nl.han.showcase.scenes.shapeentities.entities.timers.TimedDynamicRectangleTimer;
import nl.han.yaeger.engine.WithTimers;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.han.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class TimedDynamicRectangle extends DynamicRectangleEntity implements WithTimers, SceneBorderTouchingWatcher {

    private static final double ARC_MIN = 0;
    private static final double ARC_MAX = 110;
    private double currentArc = 0;
    private boolean decreasing = false;

    public TimedDynamicRectangle(Location initialPosition) {
        super(initialPosition);
        setWidth(40);
        setHeight(80);
        setFill(Color.YELLOW);
        setStrokeColor(Color.DARKKHAKI);
        setStrokeWidth(4);
        setArc();
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirectionBy(180);
    }

    @Override
    public void setupTimers() {
        addTimer(new TimedDynamicRectangleTimer(this, 10));
    }

    public void setArc() {
        if (!decreasing && currentArc < ARC_MAX - 1) {
            currentArc++;
        } else if (decreasing && currentArc > ARC_MIN + 1) {
            currentArc--;
        } else {
            decreasing = !decreasing;
        }

        setArcHeight(currentArc);
        setArcWidth(currentArc);
    }
}
