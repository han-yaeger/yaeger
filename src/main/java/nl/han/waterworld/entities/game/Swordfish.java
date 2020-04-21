package nl.han.waterworld.entities.game;

import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.collisions.Collider;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.han.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class Swordfish extends DynamicSpriteEntity implements Collider, SceneBorderCrossingWatcher {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Location location) {
        super(IMAGES_SWORDFISH_PNG, location, new Size(300, 108), 1);
        setMotionTo(2, Direction.LEFT.getValue());
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setOriginX(getSceneWidth());
        }
    }
}
