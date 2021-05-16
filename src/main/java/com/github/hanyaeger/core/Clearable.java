package com.github.hanyaeger.core;

/**
 * Denote that this {@code Object} can be cleared. Calling {@code clear()} does not guaranty maximum minimization of
 * memory footprint. To guaranty this, implement the interface {@link Destroyable}.
 */
@FunctionalInterface
public interface Clearable {

    /**
     * Implement this method and use it to clear the content of this Object.
     */
    void clear();
}
