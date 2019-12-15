package nl.meron.pong.scenes.entities;

import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.entities.events.userinput.KeyListener;

abstract class PongBat extends UpdatableSpriteEntity implements KeyListener {
    protected final int SPEED = 5;

    PongBat(Point initialPoint, Size size) {
        super("pong/bat.png", initialPoint, size);
    }
}
