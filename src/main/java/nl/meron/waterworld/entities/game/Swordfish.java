package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Swordfish extends DynamicSpriteEntity implements Collider, SceneBorderCrossingWatcher {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Point point) {
        super(IMAGES_SWORDFISH_PNG, point, new Size(300, 108), 1);
        setMotionTo(2, Direction.LEFT.getValue());
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            placeOnPosition(getSceneWidth(), getY());
        }
    }
}
