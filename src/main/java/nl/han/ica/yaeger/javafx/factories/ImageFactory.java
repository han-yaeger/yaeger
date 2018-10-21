package nl.han.ica.yaeger.javafx.factories;

import javafx.beans.NamedArg;
import javafx.scene.image.Image;

/**
 * An {@code ImageFactory} should be used for creating instance of {@link Image}.
 */
public class ImageFactory {

    /**
     * Constructs an {@link Image} with content loaded from the specified
     * url.
     *
     * @param url the string representing the URL to use in fetching the pixel
     *            data
     * @return An instance of {@link Image}
     * @throws NullPointerException     if URL is null
     * @throws IllegalArgumentException if URL is invalid or unsupported
     */
    public Image create(@NamedArg("url") String url) {
        return new Image(url);
    }

    /**
     * Construct a new {@code Image} with the specified parameters.
     *
     * @param url             the string representing the URL to use in fetching the pixel
     *                        data
     * @param requestedWidth  the image's bounding box width
     * @param requestedHeight the image's bounding box height
     * @param preserveRatio   indicates whether to preserve the aspect ratio of
     *                        the original image when scaling to fit the image within the
     *                        specified bounding box
     * @throws NullPointerException     if URL is null
     * @throws IllegalArgumentException if URL is invalid or unsupported
     */
    public Image create(@NamedArg("url") String url, @NamedArg("requestedWidth") double requestedWidth, @NamedArg("requestedHeight") double requestedHeight,
                        @NamedArg("preserveRatio") boolean preserveRatio) {
        return new Image(url, requestedWidth, requestedHeight, preserveRatio, true);
    }
}
