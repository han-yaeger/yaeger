package nl.han.ica.yaeger.metrics;

/**
 * Een {@code GameDimensions} encapsuleert de breedte en hoogte van het game.
 */
public class GameDimensions {
    private final int width;
    private final int height;

    /**
     * Maak een nieuwe {@code GameDimemsions} met de gegeven breedte en hoogte.
     *
     * @param width  The breedte.
     * @param height The hoogte.
     */
    public GameDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return De breedte.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return De hoogte
     */
    public int getHeight() {
        return height;
    }
}
