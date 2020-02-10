package nl.meron.yaeger.engine.media;

import nl.meron.yaeger.engine.exceptions.YaegerResourceNotAvailableException;

/**
 * Implementing a {@code ResourceConsumer} exposes a default methode that can be used to acquire the absolute path to a
 * resource available on the class path.
 */
public interface ResourceConsumer {

    /**
     * Returns the absolute path for a given {@code Resource}.
     *
     * @param resource the {@code Resource}, which should be available on the class path
     * @return the relative path to the {@code Resource}
     * @throws YaegerResourceNotAvailableException an {@link YaegerResourceNotAvailableException} is thrown if the
     *                                             resource can not be found on the class path
     */
    default String createPathForResource(final String resource) {

        if (resource == null || resource.isEmpty()) {
            return "";
        }

        var url = getClass().getClassLoader().getResource(resource);

        if (url == null) {
            throw new YaegerResourceNotAvailableException(resource);
        }
        return url.toString();
    }
}
