package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.SpriteObject;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

public class Bubble extends SpriteObject implements Updatable {
    public Bubble(double x, double y) {
        super("bubble.png", x, y, 20, 0, 0, 2, 0, 3);
    }
}
