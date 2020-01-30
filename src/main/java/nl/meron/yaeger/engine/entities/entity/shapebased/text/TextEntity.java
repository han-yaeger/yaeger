package nl.meron.yaeger.engine.entities.entity.shapebased.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.ShapeBasedEntity;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}. The text will be placed, using
 * the top left corner as its anchor point.
 */
public class TextEntity extends ShapeBasedEntity {

    private Color fill;
    private Font font;
    private String initialText;

    /**
     * Instantiate a new {@code TextEntity}.
     */
    public TextEntity() {
        this(new Point(0, 0));
    }

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point2D}.
     *
     * @param initialPosition the initial {@link Point} of this {@code TextEntity}
     */
    public TextEntity(final Point initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link TextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public TextEntity(final Point initialPosition, final String text) {
        super(initialPosition);
        this.initialText = text;
    }

    /**
     * Set the {@link String} that should be shown.
     *
     * @param text the {@link String} that should be shown
     */
    public void setText(final String text) {
        this.initialText = text;
        if (this.getGameNode() != null) {
            ((Text) getGameNode()).setText(text);
        }
    }

    /**
     * Set the color of the text.
     *
     * @param color an instance of {@link Color}
     */
    public void setFill(final Color color) {
        this.fill = color;
        if (getGameNode() != null) {
            ((Text) getGameNode()).setFill(color);
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
    public void setFont(final Font font) {
        this.font = font;
        if (getGameNode() != null) {
            ((Text) getGameNode()).setFont(font);
        }
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        var textGameNode = (Text) getGameNode();
        textGameNode.setTextOrigin(VPos.TOP);

        if (font != null) {
            textGameNode.setFont(font);
        }
        if (fill != null) {
            textGameNode.setFill(fill);
        }
        if (initialText != null && !initialText.isEmpty()) {
            textGameNode.setText(initialText);
        }
    }

    @Inject
    public void setTextDelegate(final Text text) {
        this.shape = text;
    }
}
