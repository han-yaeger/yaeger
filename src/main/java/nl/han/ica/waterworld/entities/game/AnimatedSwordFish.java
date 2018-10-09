package nl.han.ica.waterworld.entities.game;

import nl.han.ica.yaeger.engine.collisions.Collider;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.BoundingBox;
import nl.han.ica.yaeger.engine.entities.entity.sprites.Movement;
import nl.han.ica.yaeger.engine.entities.entity.sprites.UpdatableSpriteEntity;
import nl.han.ica.yaeger.engine.scene.SceneBorder;

public class AnimatedSwordFish extends UpdatableSpriteEntity implements Collider {
    private static final String IMAGES_ANIMATED_SWORDFISH_PNG = "images/animated-swordfish.png";

    public AnimatedSwordFish(Position position) {
        super(IMAGES_ANIMATED_SWORDFISH_PNG, position, new BoundingBox(1489, 50), 12, new Movement(Movement.Direction.LEFT, 3));
    }

    @Override
    protected void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setLocation(getSceneWidth(), getY());
        }
    }
}
