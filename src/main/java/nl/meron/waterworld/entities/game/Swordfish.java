package nl.meron.waterworld.entities.game;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.movement.MovementVector;
import nl.meron.yaeger.engine.entities.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class Swordfish extends UpdatableSpriteEntity implements Collider {

    private static final String IMAGES_SWORDFISH_PNG = "waterworld/images/swordfish.png";

    public Swordfish(final Point point) {
        super(IMAGES_SWORDFISH_PNG, point, new Size(300, 108), 1, new MovementVector(MovementVector.Direction.LEFT, 2));
    }
}
