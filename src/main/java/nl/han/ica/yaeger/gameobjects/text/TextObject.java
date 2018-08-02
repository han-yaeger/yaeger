package nl.han.ica.yaeger.gameobjects.text;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.han.ica.yaeger.gameobjects.GameObject;

public class TextObject extends GameObject {

    private Text text;

    /**
     * Create a new {@code TextObject} at the given coordinates.
     *
     * @param x The x-coordinate at which the  {@code TextObject} should be positioned.
     * @param y The y-coordinate at which the  {@code TextObject} should be positioned.
     */
    public TextObject(final double x, final double y) {
        this(x, y, "");
    }

    /**
     * Create a new {@code SpriteObject} for a given Image.
     *
     * @param x    The x-coordinate at which the  {@code TextObject} should be positioned.
     * @param y    The y-coordinate at which the  {@code TextObject} should be positioned.
     * @param text The initial text that should be shown.
     */
    public TextObject(final double x, final double y, final String text) {
        this.text = new Text(x, y, text);
    }

    /**
     * Set the text that is being displayed.
     *
     * @param text The new text to be shown.
     */
    public void setText(final String text) {
        this.text.setText(text);
    }

    public void setSize(final int size) {
        text.setFont(Font.font(size));
    }

    @Override
    public Node getGameNode() {
        return text;
    }
}
