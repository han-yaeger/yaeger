package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.interfaces.Collider;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

public class Swordfish extends UpdatableSpriteObject implements Collider {

    public Swordfish(double x, double y) {
        super("images/swordfish.png", x, y, 1, 270, 2);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setLocation(getSceneWidth(), getY());
        }
    }
}
