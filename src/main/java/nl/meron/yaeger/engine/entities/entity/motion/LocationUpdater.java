package nl.meron.yaeger.engine.entities.entity.motion;

import javafx.geometry.Point2D;
import nl.meron.yaeger.engine.entities.entity.Location;

/**
 * A {@link LocationUpdater} can calculate a new location.
 */
public interface LocationUpdater {

    /**
     * Perform an update.
     *
     * @param currentLocation The current location as a {@link Point2D}
     * @return A {@link Location} representing the new location
     */
    Point2D updateLocation(final Point2D currentLocation);
}
