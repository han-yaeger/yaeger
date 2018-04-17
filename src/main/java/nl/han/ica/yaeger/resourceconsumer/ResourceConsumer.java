package nl.han.ica.yaeger.resourceconsumer;

import java.net.URL;

public abstract class ResourceConsumer {

    /**
     * Create the full path for the given resource. The resource should be available on the class path.
     *
     * @param resource  The recource on the class-path
     * @return          The full URL of the resource
     */
    public String createPathForResource(String resource) {
        URL url = getClass().getClassLoader().getResource(resource);
        String stringUrl = url.toString();

        return stringUrl;
    }
}
