package nl.han.showcase.scenes.spriteentities.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class TennisBall extends DynamicSpriteEntity implements SceneBorderTouchingWatcher {

    public TennisBall(Location location) {
        super("showcase/entities/tennisball.png", location, new Size(30, 30), 0);
        setMotionTo(4, Direction.RIGHT.getValue());
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        changeDirectionBy(180);
    }
}
