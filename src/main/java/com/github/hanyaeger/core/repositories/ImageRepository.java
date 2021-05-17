package com.github.hanyaeger.core.repositories;

import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.factories.image.ImageFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.image.Image;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * An {@code ImageRepository} provides a central repository for acquiring sprites.
 */
@Singleton
public class ImageRepository implements ResourceConsumer, Destroyable {

    private final Map<String, Image> imageMap = new WeakHashMap<>();
    private ImageFactory factory;

    /**
     * Return an {@link Image} with content loaded from the specified
     * url.
     *
     * @param url the string representing the URL to use in fetching the pixel
     *            data
     * @return an {@link Image} with content loaded from the specified url
     */
    public Image get(final String url) {

        if (imageMap.containsKey(url)) {
            return imageMap.get(url);
        } else {
            var image = factory.create(createPathForResource(url));
            imageMap.put(url, image);
            return image;
        }
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
     * @return an {@link Image} with content loaded from the specified url
     */
    public Image get(final String url, final double requestedWidth, final double requestedHeight, final boolean preserveRatio) {

        String imageKey = constructKey(url, requestedWidth, requestedHeight, preserveRatio);

        if (imageMap.containsKey(imageKey)) {
            return imageMap.get(imageKey);
        } else {
            var image = factory.create(createPathForResource(url), requestedWidth, requestedHeight, preserveRatio);
            imageMap.put(imageKey, image);
            return image;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than {@link Integer#MAX_VALUE} elements, returns
     * {@link Integer#MAX_VALUE}.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return imageMap.size();
    }

    @Override
    public void destroy() {
        imageMap.clear();
    }

    private String constructKey(final String url, final double requestedWidth, final double requestedHeight, final boolean preserveRatio) {
        return ((int) requestedWidth) + "-" + ((int) requestedHeight) + "-" + preserveRatio + "-" + url;
    }


    /**
     * @param factory the {@link ImageFactory} to be used
     */
    @Inject
    public void setFactory(final ImageFactory factory) {
        this.factory = factory;
    }
}
