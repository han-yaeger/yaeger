package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.RotationBuffer;
import com.github.hanyaeger.api.engine.entities.entity.shape.ShapeEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * A {@code TextEntity} can be used to display a line of text on a {@link YaegerScene}. The text will be placed, using
 * the top left corner as its anchor point.
 */
public class TextEntity extends ShapeEntity<Text> {

    private Optional<Font> font = Optional.empty();
    private Optional<String> text;

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Point2D}.
     *
     * @param initialPosition the initial {@link Coordinate2D} of this {@code TextEntity}
     */
    public TextEntity(final Coordinate2D initialPosition) {
        this(initialPosition, "");
    }

    /**
     * Instantiate a new {@link TextEntity} for the given {@link Point2D} and textDelegate.
     *
     * @param initialPosition the initial {@link Point2D} of this {@code TextEntity}
     * @param text            a {@link String} containing the initial textDelegate to be displayed
     */
    public TextEntity(final Coordinate2D initialPosition, final String text) {
        super(initialPosition);
        this.text = Optional.of(text);
    }

    /**
     * Set the {@link String} that should be shown.
     *
     * @param displayText the {@link String} that should be shown
     */
    public void setText(final String displayText) {
        shape.ifPresentOrElse(text -> text.setText(displayText), () -> this.text = Optional.of(displayText));
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
        shape.ifPresentOrElse(text -> text.setFont(font), () -> this.font = Optional.of(font));
    }

    @Override
    public void setReferenceX(double x) {
        shape.ifPresentOrElse(text -> text.setX(x), () -> this.x = x);
    }

    @Override
    public void setReferenceY(double y) {
        shape.ifPresentOrElse(text -> text.setY(y), () -> this.y = y);
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        shape.get().setTextOrigin(VPos.TOP);
        font.ifPresent(font1 -> shape.get().setFont(font1));
        text.ifPresent(text -> shape.get().setText(text));
    }
}
