package com.github.hanyaeger.core.factories;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A {@link BackgroundFactory} can be used to create instances of {@link javafx.scene.layout.Background}
 * to be used as the background of a {@link Pane}. This can be either an image based background, or a background
 * that only contains a fill color.
 */
public class BackgroundFactory {

    /**
     * Create a {@link Background} with the specified fill {@link Color}.
     *
     * @param color the {@link Color} to be used
     * @return a {@link Background} that encapsulates an {@link BackgroundFill} with the given {@link Color}
     */
    public Background createFillBackground(final Color color) {
        return new Background(new BackgroundFill(color, null, null));
    }

    /**
     * Create a {@link Background} with the specified image. The created {@link Background} will be either a
     * fullscreen version, or a tiled version.
     *
     * @param image      the {@link Image} to be used
     * @param fullscreen a {@code boolean} that states whether the {@link Image} should be fullscreen. If {@code false}
     *                   the {@link Image} will be horizontally and vertically tiled.
     * @return a {@link Background} that encapsulates an {@link BackgroundImage} with the given {@link Image}
     */

    public Background createImageBackground(final Image image, final boolean fullscreen) {
        var repeat = BackgroundRepeat.REPEAT;
        var size = BackgroundSize.DEFAULT;

        if (fullscreen) {
            repeat = BackgroundRepeat.NO_REPEAT;
            size = new BackgroundSize(1.0, 1.0, true, true, false, false);
        }

        return new Background(new BackgroundImage(image, repeat, repeat, BackgroundPosition.DEFAULT, size));
    }
}
