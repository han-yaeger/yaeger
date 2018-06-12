package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.enumerations.SceneBorder;
import nl.han.ica.yaeger.gameobjects.sprites.UpdatableSpriteObject;

public class Bubble extends UpdatableSpriteObject {

    public Bubble(double x, double y) {
        super("bubble.png", x, y, 0, 2, 0, 1);
        scaleToWidth(20);
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.TOP)) {
            remove();
        }
    }
}
