package nl.meron.pong;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.movement.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class Ball extends UpdatableSpriteEntity {

    public Ball(Point point, Double direction) {
        super("pong/ball.png", point, new Size(20, 20), 0, new MovementVector(direction, 1d));
    }
}
