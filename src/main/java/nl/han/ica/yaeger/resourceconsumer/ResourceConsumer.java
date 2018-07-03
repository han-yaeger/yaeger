package nl.han.ica.yaeger.resourceconsumer;

/**
 * A ResourceConsumer offers a default method, that can be used for acquiring the full path of a resource available
 * on the class path. All classes that must perform such a task, should implement this interface and us the method is
 * exposes.
 */
public interface ResourceConsumer {

    /**
     * Create the full path for the given resource. The resource should be available on the class path.
     *
     * @param resource The recource on the class-path
     * @return The full URL of the resource
     */
    default String createPathForResource(String resource) {
        var url = getClass().getClassLoader().getResource(resource);

        return url.toString();
    }
}
