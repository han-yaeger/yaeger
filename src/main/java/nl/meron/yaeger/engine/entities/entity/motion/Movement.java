package nl.meron.yaeger.engine.entities.entity.motion;

import nl.meron.yaeger.engine.entities.entity.Point;

/**
 * The base interface of
 */
@FunctionalInterface
@Deprecated
public interface Movement {

    /**
     * @param currentPoint The current {@link Point} from which the movement should be applied
     * @param timestamp    A timestamp
     * @return The new {@link Point} after this {@code Movement} has been applied
     */
    Point move(Point currentPoint, double timestamp);
}
