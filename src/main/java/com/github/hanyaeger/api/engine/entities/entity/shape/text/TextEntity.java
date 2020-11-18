package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.shape.ShapeEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A {@link TextEntity} can be used to display a line of text on a {@link YaegerScene}.
 */
public class TextEntity extends ShapeEntity<Text> {

    static final Font DEFAULT_FONT = Font.font("roboto", FontWeight.NORMAL, 11);

    private Font font = DEFAULT_FONT;
    private String text;

    /**
     * Create a new empty {@link TextEntity} on the given {@link Coordinate2D}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link TextEntity}
     */
    public TextEntity(final Coordinate2D initialLocation) {
        this(initialLocation, "");
    }

    /**
     * Crwate a new {@link TextEntity} on the given {@link Point2D} for the given text.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link TextEntity}
     * @param text            a {@link String} containing the initial text to be displayed
     */
    public TextEntity(final Coordinate2D initialLocation, final String text) {
        super(initialLocation);
        this.text = text;
    }

    /**
     * Set the {@link String} that should be shown.
     *
     * @param displayText the {@link String} that should be shown
     */
    public void setText(final String displayText) {
        shape.ifPresentOrElse(text -> text.setText(displayText), () -> this.text = displayText);
    }

    /**
     * Return the {@code text} that is being displayed.
     *
     * @return the {@code text} that is being displayed as a {@link String}
     */
    public String getText() {
        return shape.map(Text::getText).orElse(text);
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
        shape.ifPresentOrElse(text -> text.setFont(font), () -> this.font = font);
    }

    /**
     * Return the {@link Font} currently used fot this {@link TextEntity}.
     *
     * @return the {@link Font} currently used fot this {@link TextEntity}
     */
    public Font getFont() {
        return shape.map(Text::getFont).orElse(font);
    }

    @Override
    public final void setAnchorLocation(final Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        shape.ifPresent(text -> {
            text.setX(anchorLocation.getX());
            text.setY(anchorLocation.getY());
        });
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        shape.ifPresent(shape -> {
            shape.setTextOrigin(VPos.TOP);
            shape.setText(text);
            shape.setFont(font);
        });
    }
}
