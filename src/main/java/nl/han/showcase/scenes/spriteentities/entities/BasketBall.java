package nl.han.showcase.scenes.spriteentities.entities;

import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;

public class BasketBall extends DynamicSpriteEntity {

    public BasketBall(Location location) {
        super("showcase/entities/basketball.png", location, new Size(60, 60), 0);
        setRotationSpeed(1);
    }
}
