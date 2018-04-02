package nl.han.ica.yaeger;

/**
 * A GameDimensions encapsulates the width and height of a Scene.
 */
public class GameDimensions {
    private final int width;
    private final int height;

    /**
     * Create a new GameDimensions with the given width and height.
     *
     * @param width  The width of the Scene
     * @param height The height of the Scene
     */
    public GameDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return The width of the Scene
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the Scene
     */
    public int getHeight() {
        return height;
    }
}
