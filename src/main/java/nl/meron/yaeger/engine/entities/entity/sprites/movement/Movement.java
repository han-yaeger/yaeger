package nl.meron.yaeger.engine.entities.entity.sprites.movement;

import nl.meron.yaeger.engine.entities.entity.Position;

/**
 * The base interface of
 */
@FunctionalInterface
public interface Movement {

    /**
     * @param currentPosition The current {@link Position} from which the movement should be applied
     * @param timestamp       A timestamp
     * @return The new {@link Position} after this {@code Movement} has been applied
     */
    Position move(Position currentPosition, double timestamp);
}
