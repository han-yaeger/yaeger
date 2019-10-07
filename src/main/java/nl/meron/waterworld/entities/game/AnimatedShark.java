package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.updatetasks.SceneBoundaryCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class AnimatedShark extends UpdatableSpriteEntity implements Collider, SceneBoundaryCrossingWatcher {
    private static final String IMAGES_ANIMATED_SHARK_PNG = "waterworld/images/shark.png";

    public AnimatedShark(Position position) {
        super(IMAGES_ANIMATED_SHARK_PNG, position, new Size(200, 200), 19, new MovementVector(MovementVector.Direction.LEFT, 4));
        setAutoCycle(25);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setPosition(new Position(getSceneWidth(), getY()));
        }
    }


}
