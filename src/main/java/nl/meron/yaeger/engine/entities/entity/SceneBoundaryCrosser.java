package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.scenes.SceneBorder;

@FunctionalInterface
public interface SceneBoundaryCrosser {

    /**
     * This method is being called when this {@code SceneBoundaryCrosser} crosses a boundary of the {@link javafx.scene.Scene}.
     *
     * @param border The border at which the {@link javafx.scene.Scene} is being crossed
     */
    void notifyBoundaryCrossing(SceneBorder border);
}
