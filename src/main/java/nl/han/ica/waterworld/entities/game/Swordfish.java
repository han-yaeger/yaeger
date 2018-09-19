package nl.han.ica.waterworld.entities.game;

import nl.han.ica.yaeger.entities.enumerations.SceneBorder;
import nl.han.ica.yaeger.entities.interfaces.Collider;
import nl.han.ica.yaeger.entities.sprites.UpdatableSpriteEntity;

public class Swordfish extends UpdatableSpriteEntity implements Collider {

    public Swordfish() {
        super("images/swordfish.png", 300, 108, 1, 270, 2);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setLocation(getSceneWidth(), getY());
        }
    }
}
