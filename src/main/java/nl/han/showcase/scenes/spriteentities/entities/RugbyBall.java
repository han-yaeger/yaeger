package nl.han.showcase.scenes.spriteentities.entities;

import nl.han.showcase.scenes.spriteentities.entities.timers.RugbyBallTimer;
import nl.han.yaeger.engine.Size;
import nl.han.yaeger.engine.WithTimers;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;


public class RugbyBall extends DynamicSpriteEntity implements WithTimers {

    public RugbyBall(Location location) {
        super("showcase/entities/rugbyball.png", location, new Size(60, 60), 0);
        setRotationSpeed(7);
    }

    @Override
    public void setupTimers() {
        addTimer(new RugbyBallTimer(this, 1000));
    }

    public void changeDirection() {
        setRotationSpeed(getRotationSpeed() * -1);
    }
}
