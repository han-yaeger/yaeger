package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.core.entities.ShapeEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A {@link TextEntity} can be used to display a line of text on a {@link YaegerScene}.
 * <p>
 * For the displayed text, either a system-font can be used, through the use of the {@link Font} class, or a custom
 * font, through the use if the class {@link CustomFont}. The custom font, should then be a {@code *.ttf} file and
 * placed within the {@code resources/} folder. When placed in a sub-folder of {@code resource/}, this folder should be
 * open through the module-descriptor.
 */
public class TextEntity extends ShapeEntity<Text> {

    static final Font DEFAULT_FONT = Font.font("roboto", FontWeight.NORMAL, 11);

    private Font font = DEFAULT_FONT;
    private String text;

    /**
     * Create a new empty {@link TextEntity} on the given {@code initialLocation}.
     *
     * @param initialLocation the initial {@link Coordinate2D} of this {@link TextEntity}
     */
    public TextEntity(final Coordinate2D initialLocation) {
        this(initialLocation, "");
    }

    /**
     * Create a new {@link TextEntity} on the given {@link Point2D} for the given text.
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
        shape.ifPresentOrElse(t -> t.setText(displayText), () -> this.text = displayText);
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
     * Set the {@link CustomFont} to be used.
     *
     * @param customFont the {@link CustomFont} to be used
     */
    public void setFont(final CustomFont customFont) {
        shape.ifPresentOrElse(t -> t.setFont(customFont.getFont()),
                () -> this.font = customFont.getFont());
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
        shape.ifPresentOrElse(t -> t.setFont(font),
                () -> this.font = font);
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
        shape.ifPresent(t -> {
            t.setX(anchorLocation.getX());
            t.setY(anchorLocation.getY());
        });
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        shape.ifPresent(t -> {
            t.setTextOrigin(VPos.TOP);
            t.setText(text);
            t.setFont(font);
        });
    }
}
