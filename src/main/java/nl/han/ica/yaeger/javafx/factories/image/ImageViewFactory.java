package nl.han.ica.yaeger.javafx.factories.image;

import com.google.inject.Singleton;
import javafx.beans.NamedArg;
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
     * @param image the {@link Image} for which an {@link ImageView should be created
     * @return An instance of {@link ImageView}
     */
    public ImageView create(@NamedArg("image") Image image) {
        return new ImageView(image);
    }
}
