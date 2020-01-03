package nl.meron.yaeger.engine.entities.entity.collisions;

import nl.meron.yaeger.engine.entities.entity.Bounded;
import nl.meron.yaeger.engine.entities.entity.motion.DirectionProvider;
import nl.meron.yaeger.engine.entities.entity.motion.SpeedProvider;

/**
 * A {@link Collider} represents an {@link nl.meron.yaeger.engine.entities.entity.Entity} that can be collided with
 * by a {@link Collided}
 * <p>
 * In case of a collision, only the {@link Collided} will be notified.
 */
public interface Collider extends Bounded, SpeedProvider, DirectionProvider {
}
