package nl.meron.pong;

import nl.meron.yaeger.engine.entities.entity.Position;
import nl.meron.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.meron.yaeger.engine.entities.entity.sprites.Size;
import nl.meron.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.meron.yaeger.engine.entities.entity.updatetasks.SceneBoundaryCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class Ball extends UpdatableSpriteEntity implements SceneBoundaryCrossingWatcher {

    public Ball(Position position, Double direction) {
        super("pong/ball.png", position, new Size(20, 20), 0, new MovementVector(direction, 1d));
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            System.out.println("LEFT-BORDER");
            remove();
        } else if (border.equals(SceneBorder.RIGHT)) {
            System.out.println("RIGHT-BORDER");
            remove();
        }
    }
}
