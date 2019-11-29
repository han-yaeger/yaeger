package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.entities.events.scene.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Swordfish extends UpdatableSpriteEntity implements Collider, SceneBorderCrossingWatcher {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Point point) {
        super(IMAGES_SWORDFISH_PNG, point, new Size(300, 108), 1, new MotionVector(MotionVector.Direction.LEFT, 2));
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setPoint(new Point(getSceneWidth(), getY()));
        }
    }
}
