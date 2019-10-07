package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.updatetasks.SceneBoundaryCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.meron.yaeger.engine.scenes.SceneBorder;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class Swordfish extends UpdatableSpriteEntity implements Collider, SceneBoundaryCrossingWatcher {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Position position) {
        super(IMAGES_SWORDFISH_PNG, position, new Size(300, 108), 1, new MovementVector(MovementVector.Direction.LEFT, 2));
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setPosition(new Position(getSceneWidth(), getY()));
        }
    }
}
