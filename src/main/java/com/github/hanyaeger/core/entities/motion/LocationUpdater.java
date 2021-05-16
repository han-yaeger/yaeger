package com.github.hanyaeger.core.entities.motion;

import javafx.geometry.Point2D;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * A {@link LocationUpdater} can calculate a new location.
 */
public interface LocationUpdater {

    /**
     * Perform an update.
     *
     * @param currentLocation The current location as a {@link Point2D}
     * @return A {@link Coordinate2D} representing the new location
     */
    Coordinate2D updateLocation(final Point2D currentLocation);
}
