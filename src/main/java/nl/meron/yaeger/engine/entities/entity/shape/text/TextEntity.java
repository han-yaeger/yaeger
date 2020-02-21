package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;
import nl.meron.yaeger.engine.scenes.YaegerScene;

import java.util.Optional;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}. The text will be placed, using
 * the top left corner as its anchor point.
 */
public class TextEntity extends JavaFXEntity {

    private Color fill;
    private Font font;
    private String initialText;
    private Optional<Text> text;

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point2D}.
     *
     * @param initialPosition the initial {@link Location} of this {@code TextEntity}
     */
    public TextEntity(final Location initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link TextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public TextEntity(final Location initialPosition, final String text) {
        super(initialPosition);
        this.text = Optional.empty();
        this.initialText = text;
    }

    /**
     * Set the {@link String} that should be shown.
     *
     * @param displayText the {@link String} that should be shown
     */
    public void setText(final String displayText) {
        text.ifPresentOrElse(text -> text.setText(displayText), () -> this.initialText = displayText);
    }

    /**
     * Set the color of the text.
     *
     * @param color an instance of {@link Color}
     */
    public void setFill(final Color color) {
        text.ifPresentOrElse(text -> text.setFill(color), () -> this.fill = color);
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
        text.ifPresentOrElse(text -> text.setFont(font), () -> this.font = font);
    }

    @Override
    public void setX(double x) {
        text.ifPresentOrElse(text -> text.setX(x), () -> initialX = x);
    }

    @Override
    public void setY(double y) {
        text.ifPresentOrElse(text -> text.setY(y), () -> initialY = y);
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        text.get().setTextOrigin(VPos.TOP);

        if (font != null) {
            text.get().setFont(font);
        }
        if (fill != null) {
            text.get().setFill(fill);
        }
        if (initialText != null && !initialText.isEmpty()) {
            text.get().setText(initialText);
        }
    }

    @Inject
    public void setTextDelegate(final Text text) {
        this.text = Optional.of(text);
    }

    @Override
    public Optional<Node> getGameNode() {
        return Optional.of(text.get());
    }
}
