package nl.han.ica.waterworld.entities.game;

import nl.han.ica.yaeger.engine.entities.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.MovementVector;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Size;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.engine.scenes.SceneBorder;

public class AnimatedShark extends UpdatableSpriteEntity implements Collider {
    private static final String IMAGES_ANIMATED_SHARK_PNG = "images/shark.png";

    public AnimatedShark(Position position) {
        super(IMAGES_ANIMATED_SHARK_PNG, position, new Size(200, 200), 19, new MovementVector(MovementVector.Direction.LEFT, 4));
        setAutoCycle(25);
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setPosition(new Position(getSceneWidth(), getY()));
        }
    }
}
