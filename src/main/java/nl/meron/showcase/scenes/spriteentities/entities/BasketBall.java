package nl.meron.showcase.scenes.spriteentities.entities;

import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

import java.util.Set;

public class BasketBall extends DynamicSpriteEntity {

    public BasketBall(Location location) {
        super("showcase/entities/basketball.png", location, new Size(60, 60), 0);
        setRotationSpeed(1);
    }
}
