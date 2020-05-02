package nl.han.yaeger.engine.entities.entity.collisions;

import nl.han.yaeger.engine.entities.entity.Bounded;
import nl.han.yaeger.engine.entities.entity.motion.DirectionProvider;
import nl.han.yaeger.engine.entities.entity.motion.SpeedProvider;

/**
 * A {@link Collider} represents an {@link nl.han.yaeger.engine.entities.entity.YaegerEntity} that can be collided with
 * by a {@link AABBCollided}
 * <p>
 * In case of a collision, only the {@link AABBCollided} will be notified.
 */
public interface Collider extends Bounded, SpeedProvider, DirectionProvider {
}
