package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.sprite.Size;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.events.scene.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Swordfish extends DynamicSpriteEntity implements Collider, SceneBorderCrossingWatcher {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Point point) {
        super(IMAGES_SWORDFISH_PNG, point, new Size(300, 108), 1);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            placeOnPosition(new Point(getSceneWidth(), getY()));
        }
    }

    @Override
    public void configure() {
        setMotion(2, Direction.LEFT.getValue());
    }
}
