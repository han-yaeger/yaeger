package com.github.hanyaeger.core.factories.image;

import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * An {@code ImageViewFactory} should be used for creating instance of {@link ImageView}.
 */
@Singleton
public class ImageViewFactory {

    /**
     * Constructs an {@link ImageView} for the given {@link Image}.
     *
     * @param image The {@link Image} for which an {@link ImageView} should be created.
     * @return An instance of {@link ImageView}.
     */
    public ImageView create(final Image image) {

        var imageView = new ImageView(image);
        imageView.setManaged(false);
        imageView.setFocusTraversable(false);
        return imageView;
    }
}
