package com.github.hanyaeger.api.entities;

/**
 * When used with an {@link Animation} that support a callback, this interface should be implemented
 * by the object that supplies the callback-method.
 */
@FunctionalInterface
public interface AnimationCallback {

    /**
     * Method to be implemented to handle the callback. This method will be called whenever an
     * {@link Animation} finishes.
     */
    void call();
}
