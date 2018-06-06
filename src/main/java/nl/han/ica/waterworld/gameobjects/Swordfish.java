package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.SpriteObject;
import nl.han.ica.yaeger.gameobjects.enumerations.LeftScreenLocation;
import nl.han.ica.yaeger.gameobjects.interfaces.LeftScreenObserver;
import nl.han.ica.yaeger.gameobjects.interfaces.Updatable;

public class Swordfish extends SpriteObject implements LeftScreenObserver, Updatable {

    public Swordfish(double x, double y) {
        super("swordfish.png", x, y, 0, 0, 270, 1, 0, 0);
    }

    @Override
    public void hasLeftTheScreen(LeftScreenLocation location) {
        System.out.println("I have left the screen. alas!");
    }
}
