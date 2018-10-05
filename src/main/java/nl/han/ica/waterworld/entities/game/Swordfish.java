package nl.han.ica.waterworld.entities.game;

import nl.han.ica.yaeger.engine.entities.entity.sprites.BoundingBox;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Movement;
import nl.han.ica.yaeger.engine.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.engine.entities.entity.Collider;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;

public class Swordfish extends UpdatableSpriteEntity implements Collider {

    public Swordfish() {
        super("images/swordfish.png", new BoundingBox(300, 108), 1, new Movement(270, 2));
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setLocation(getSceneWidth(), getY());
        }
    }
}
