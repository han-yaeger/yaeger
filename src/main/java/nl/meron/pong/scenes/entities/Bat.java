package nl.meron.pong.scenes.entities;

import javafx.scene.paint.Color;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.collisions.Collider;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.shape.rectangle.DynamicRectangleEntity;

import static nl.meron.pong.scenes.LevelOne.GREEN_COLOR;

public abstract class Bat extends DynamicRectangleEntity implements KeyListener, Collider {
    protected final int SPEED = 5;

    public Bat(Location initialPosition) {
        super(initialPosition);
        setHeight(75);
        setWidth(20);
        setFill(Color.BLACK);
        setStrokeWidth(1);
        setStrokeColor(GREEN_COLOR);
    }
}
