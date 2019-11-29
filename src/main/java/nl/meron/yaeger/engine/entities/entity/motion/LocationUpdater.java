package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.Point;

/**
 * A {@link LocationUpdater} can calculate a new location.
 */
public interface LocationUpdater {

    /**
     * Perform an update.
     *
     * @param currentLocation The current location as a {@link Point2D}
     * @return A {@link Point} representing the new location
     */
    Point2D updateLocation(Point2D currentLocation);
}
