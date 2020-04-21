package nl.han.yaeger.engine.entities.entity;

/**
 * Implementing the interface {@link Rotatable} will result in de availability of the {@link Rotatable#setRotate(double)}
 * method.
 */
public interface Rotatable extends NodeProvider {

    /**
     * Set the rotation of the {@link Entity} to the specified value.
     *
     * @param degrees the rotation in degrees as a {@code double}
     */
    default void setRotate(final double degrees) {
        getGameNode().ifPresent(node -> node.setRotate(degrees));
    }
}
