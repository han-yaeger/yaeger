package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.movement.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class AnimatedShark extends UpdatableSpriteEntity implements Collider {
    private static final String IMAGES_ANIMATED_SHARK_PNG = "waterworld/images/shark.png";

    public AnimatedShark(Point point) {
        super(IMAGES_ANIMATED_SHARK_PNG, point, new Size(200, 200), 19, new MovementVector(MovementVector.Direction.LEFT, 4));
        setAutoCycle(25);
    }
}
