package nl.han.ica.yaeger.engine;

/**
 * Denote that this {@code Object} is Destroyable and hence ensures maximum Objects eligible for Garbage Collection.
 */
public interface Destroyable {

    /**
     * Implement this method and use it to ensure that the maximum number of Objects are eligible for Garbage Collection
     * after this method has been called.
     */
    void destroy();
}
