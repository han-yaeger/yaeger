package nl.meron.pong;

import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.userinput.KeyListener;

abstract class PongBat extends UpdatableSpriteEntity  implements KeyListener{
    protected final int SPEED = 5;

    PongBat( Position initialPosition, Size size) {
        super("pong/bat.png", initialPosition, size);
    }
}
