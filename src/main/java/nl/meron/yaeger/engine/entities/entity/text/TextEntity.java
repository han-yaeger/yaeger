package nl.meron.yaeger.engine.entities.entity.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.Configurable;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}. The text will be placed, using
 * the top left corner as its anchor point.
 */
public class TextEntity implements Entity {

    private Text textDelegate;
    private Point2D initialPosition;
    private Color fill;
    private Font font;
    private String initialText;
    private boolean visible = true;

    /**
     * Instantiate a new {@code TextEntity}.
     */
    public TextEntity() {
        this(new Point2D(0, 0));
    }

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point2D}.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     */
    public TextEntity(final Point2D initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link TextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public TextEntity(final Point2D initialPosition, final String text) {
        this.initialPosition = initialPosition;
        this.initialText = text;
    }

    /**
     * Set the {@link String} that should be shown.
     *
     * @param text the {@link String} that should be shown
     */
    public void setText(final String text) {
        this.initialText = text;
        if (this.textDelegate != null) {
            this.textDelegate.setText(text);
        }
    }

    /**
     * Set the color of the text.
     *
     * @param color an instance of {@link Color}
     */
    public void setFill(Color color) {
        this.fill = color;
        if (textDelegate != null) {
            textDelegate.setFill(color);
        }
    }

    /**
     * Set the {@link Font} to be used. A {@link Font} encapsulates multiple properties.
     *
     * <p>
     * To only set the font type and size:
     * {@code setFont(Font.FONT ("Verdana", 20));}
     * <p>
     * It is also possible to set more properties:
     * {@code setFont(Font.FONT("Verdana", FontWeight.BOLD, 70));}
     *
     * @param font the {@link Font} to be used
     */
    public void setFont(Font font) {
        this.font = font;

        if (textDelegate != null) {
            textDelegate.setFont(font);
        }
    }

    @Override
    public void placeOnPosition(Point2D position) {
        this.initialPosition = position;

        if (textDelegate != null) {
            textDelegate.setX(position.getX());
            textDelegate.setY(position.getY());
        }
    }

    @Override
    public void remove() {
        textDelegate.setVisible(false);
        textDelegate.setText(null);
        notifyRemove();
    }

    @Override
    public Node getGameNode() {
        return textDelegate;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (textDelegate != null) {
            textDelegate.setVisible(visible);
        }
    }

    @Override
    public void init(Injector injector) {
        textDelegate.setTextOrigin(VPos.TOP);
        if (initialPosition != null) {
            placeOnPosition(initialPosition);
        }
        if (font != null) {
            textDelegate.setFont(font);
        }
        if (fill != null) {
            textDelegate.setFill(fill);
        }
        if (initialText != null && !initialText.isEmpty()) {
            textDelegate.setText(initialText);
        }
        textDelegate.setVisible(visible);
    }

    @Inject
    public void setTextDelegate(Text text) {
        this.textDelegate = text;
    }
}
