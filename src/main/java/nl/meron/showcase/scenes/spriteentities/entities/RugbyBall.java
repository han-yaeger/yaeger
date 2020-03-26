package nl.meron.showcase.scenes.spriteentities.entities;

import nl.meron.showcase.scenes.spriteentities.entities.timers.RugbyBallTimer;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.WithTimers;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.DynamicSpriteEntity;


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
