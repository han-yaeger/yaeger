package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class AnimatedShark extends DynamicSpriteEntity implements Collider, SceneBorderCrossingWatcher {
    private static final String IMAGES_ANIMATED_SHARK_PNG = "waterworld/images/shark.png";

    public AnimatedShark(Location location) {
        super(IMAGES_ANIMATED_SHARK_PNG, location, new Size(200, 200), 19);
        setAutoCycle(25);
        setMotionTo(4, Direction.LEFT.getValue());
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            placeOnLocation(getSceneWidth(), getLocation().getY());
        }
    }
}
