package nl.meron.pong.scenes.entities;

import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;

abstract class PongBat extends DynamicSpriteEntity implements KeyListener, Collider {
    protected final int SPEED = 5;

    PongBat(Point initialPoint, Size size) {
        super("pong/bat.png", initialPoint, size);
    }

    @Override
    public void configure() {

    }
}
