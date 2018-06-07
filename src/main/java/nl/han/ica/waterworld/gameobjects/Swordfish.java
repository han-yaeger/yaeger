package nl.han.ica.waterworld.gameobjects;

import nl.han.ica.yaeger.gameobjects.Sprites.UpdatableSpriteObject;
import nl.han.ica.yaeger.gameobjects.enumerations.LeftScreenLocation;
import nl.han.ica.yaeger.gameobjects.interfaces.LeftScreenObserver;

public class Swordfish extends UpdatableSpriteObject implements LeftScreenObserver {

    public Swordfish(double x, double y) {
        super("swordfish.png", x, y, 270, 1);
    }

    @Override
    public void hasLeftTheScreen(LeftScreenLocation location) {
        System.out.println("I have left the screen. alas!");
    }
}
