package nl.han.ica.yaeger.engine.repositories;

import com.google.inject.Inject;
import javafx.scene.image.Image;
import nl.han.ica.yaeger.engine.Destructable;
import nl.han.ica.yaeger.javafx.factories.ImageFactory;
import nl.han.ica.yaeger.engine.resourceconsumer.ResourceConsumer;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * An {@code ImageRepository} provides a central repository for acquiring sprites.
 */
public class ImageRepository implements ResourceConsumer, Destructable {

    private Map<String, Image> spriteMap;
    private ImageFactory factory;

    private static ImageRepository spriteRepository;

    private ImageRepository() {
        spriteMap = new WeakHashMap<>();
    }

    /**
     * Return an instance of the {@code ImageRepository}. Note that this is a Singleton.
     *
     * @return A Singleton instance of a {@code ImageRepository}
     */
    public static ImageRepository getInstance() {
        if (spriteRepository == null) {
            spriteRepository = new ImageRepository();
        }

        return spriteRepository;
    }

    /**
     * Return an {@link Image} with content loaded from the specified
     * url.
     *
     * @param url the string representing the URL to use in fetching the pixel
     *            data
     * @return an {@link Image} with content loaded from the specified url
     */
    public Image get(String url) {
        if (spriteMap.containsKey(url)) {
            return spriteMap.get(url);
        } else {
            var image = factory.create(createPathForResource(url));
            spriteMap.put(url, image);
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
    public Image get(String url, int requestedWidth, int requestedHeight, boolean preserveRatio) {

        String imageKey = constructKey(url, requestedWidth, requestedHeight, preserveRatio);

        if (spriteMap.containsKey(imageKey)) {
            return spriteMap.get(imageKey);
        } else {
            var image = factory.create(createPathForResource(url), requestedWidth, requestedHeight, preserveRatio);
            spriteMap.put(imageKey, image);
            return image;
        }
    }

    @Inject
    public void setFactory(ImageFactory factory) {
        this.factory = factory;
    }

    private String constructKey(String url, int requestedWidth, int requestedHeight, boolean preserveRatio) {
        return requestedWidth + "-" + requestedHeight + "-" + preserveRatio + "-" + url;
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        if (spriteMap == null) {
            return 0;
        }
        return spriteMap.size();
    }

    @Override
    public void destroy() {
        spriteMap.clear();
    }
}
