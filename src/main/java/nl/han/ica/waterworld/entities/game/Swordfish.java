package nl.han.ica.waterworld.entities.game;

import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Size;
import nl.han.ica.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.han.ica.yaeger.engine.scenes.SceneBorder;
import nl.han.ica.yaeger.engine.entities.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class Swordfish extends UpdatableSpriteEntity implements Collider {

    private static final String IMAGES_SWORDFISH_PNG = "images/swordfish.png";

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
