package nl.meron.showcase.scenes.entities;

import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Ball extends DynamicSpriteEntity implements SceneBorderTouchingWatcher {

    public Ball(Point point) {
        super("showcase/images/tennisbal.png", point, new Size(30, 30), 0);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirection(180);
    }

    @Override
    public void configure() {
        setMotion(4, Direction.RIGHT.getValue());
    }
}
