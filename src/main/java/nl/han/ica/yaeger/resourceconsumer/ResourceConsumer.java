package nl.han.ica.yaeger.resourceconsumer;

import java.net.URL;

/**
 * A ResourceConsumer encapsulate a resource available on the class path. It can be used to load
 * an image file, a sound file, or any other file that should be loaded runtime and used by Yaeger.
 *
 */
public abstract class ResourceConsumer {

    /**
     * Create the full path for the given resource. The resource should be available on the class path.
     *
     * @param resource The recource on the class-path
     * @return The full URL of the resource
     */
    protected String createPathForResource(String resource) {
        URL url = getClass().getClassLoader().getResource(resource);
        String stringUrl = url.toString();

        return stringUrl;
    }
}
