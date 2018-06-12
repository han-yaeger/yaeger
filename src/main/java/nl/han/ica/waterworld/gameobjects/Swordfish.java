package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

public class Swordfish extends UpdatableSpriteObject {

    public Swordfish(double x, double y) {
        super("swordfish.png", x, y, 270, 2);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setLocation(getSceneWidth(), getY());
        }
    }
}
