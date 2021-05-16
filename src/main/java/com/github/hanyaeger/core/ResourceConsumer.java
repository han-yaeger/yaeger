package com.github.hanyaeger.core;

import com.github.hanyaeger.core.exceptions.ResourceNotAvailableException;

/**
 * Implementing a {@link ResourceConsumer} exposes a default methode that can be used to acquire the absolute
 * path to a resource available on the class path.
 */
public interface ResourceConsumer {

    /**
     * Returns the absolute path for a given {@code Resource}.
     *
     * @param resource the {@code Resource}, which should be available on the class path
     * @return the relative path to the {@code Resource}
     * @throws ResourceNotAvailableException an {@link ResourceNotAvailableException} is thrown if the
     *                                             resource can not be found on the class path
     */
    default String createPathForResource(final String resource) {

        if (resource == null || resource.isEmpty()) {
            return "";
        }

        var url = getClass().getClassLoader().getResource(resource);

        if (url == null) {
            throw new ResourceNotAvailableException(resource);
        }
        return url.toString();
    }
}
