package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.MotionVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.entities.events.scene.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class AnimatedShark extends UpdatableSpriteEntity implements Collider, SceneBorderCrossingWatcher {
    private static final String IMAGES_ANIMATED_SHARK_PNG = "waterworld/images/shark.png";

    public AnimatedShark(Point point) {
        super(IMAGES_ANIMATED_SHARK_PNG, point, new Size(200, 200), 19, new MotionVector(MotionVector.Direction.LEFT, 4));
        setAutoCycle(25);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setPoint(new Point(getSceneWidth(), getY()));
        }
    }
}
