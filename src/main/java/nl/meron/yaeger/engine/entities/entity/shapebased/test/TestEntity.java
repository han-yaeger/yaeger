package nl.meron.yaeger.engine.entities.entity.shapebased.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.shapebased.ShapeEntity;
import nl.meron.yaeger.engine.scenes.YaegerScene;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}. The text will be placed, using
 * the top left corner as its anchor point.
 */
public class TestEntity extends ShapeEntity {

    private String initialText;
    private Text text;

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point2D}.
     *
     * @param initialPosition the initial {@link Point} of this {@code TextEntity}
     */
    public TestEntity(final Point initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link TestEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public TestEntity(final Point initialPosition, final String text) {
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

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        text.setTextOrigin(VPos.TOP);

        if (initialText != null && !initialText.isEmpty()) {
            text.setText(initialText);
        }
    }


    @Inject
    public void setTextDelegate(final Text text) {
        this.text = text;
    }

    @Override
    public Node getGameNode() {
        return text;
    }

    @Override
    public void placeOnPosition(double x, double y) {
        if (text == null) {
            initialPosition = new Point(x, y);
        } else {
            text.setX(x);
            text.setY(y);
        }
    }
}
