package nl.meron.yaeger.engine.entities.entity.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.engine.entities.entity.Point;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}.
 */
public class TextEntity implements Entity {

    private Text textDelegate;
    private Point anchorPoint;
    private Color fill;
    private Font font;
    private String initialText;
    private boolean visible = true;

    /**
     * Instantiate a new {@code TextEntity}.
     */
    public TextEntity() {
        this(new Point(0, 0));
    }

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point}.
     *
     * @param anchorPoint the initial {@link Point} of this {@code TextEntity}
     */
    public TextEntity(final Point anchorPoint) {
        this(anchorPoint, "");
    }

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point} and textDelegate.
     *
     * @param anchorPoint the initial {@link Point} of this {@code TextEntity}
     * @param text        a {@link String} containing the initial textDelegate to be displayed
     */
    public TextEntity(final Point anchorPoint, final String text) {
        this.anchorPoint = anchorPoint;
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
     * Set the color of the textDelegate.
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

    /**
     * Set the position of this {@code TextEntity}.
     *
     * @param anchorPoint a {@link Point} encapsulating the x and y coordinate
     */
    public void setAnchorPoint(Point anchorPoint) {
        this.anchorPoint = anchorPoint;

        if (textDelegate != null) {
            textDelegate.setX(anchorPoint.getX());
            textDelegate.setY(anchorPoint.getY());
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
    public Point getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public void init(Injector injector) {
        if (anchorPoint != null) {
            textDelegate.setX(anchorPoint.getX());
            textDelegate.setY(anchorPoint.getY());
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