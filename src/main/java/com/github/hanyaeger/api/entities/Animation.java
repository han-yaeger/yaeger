package com.github.hanyaeger.api.entities;

/**
 * An {@code Animation} encapsulate an animation that uses only a selection of the frames from a sprite sheet.
 */
public interface Animation {

    /**
     * The default interval for sprite cycling that will be used if none is set. This value is in ms.
     */
    long DEFAULT_AUTOCYCLE_INTERVAL = 100;

    /**
     * The row-index of the first sprite to be shown. Note that this value is 0-based.
     *
     * @return the 0-based row-index of the first sprite to be shown
     */
    int rowStart();

    /**
     * The column-index of the first sprite to be shown. Note that this value is 0-based.
     *
     * @return the 0-based column-index of the first sprite to be shown
     */
    int columnStart();

    /**
     * The row-index of the last sprite to be shown. Note that this value is 0-based.
     *
     * @return the 0-based row-index of the last sprite to be shown
     */
    int rowEnd();

    /**
     * The column-index of the last sprite to be shown. Note that this value is 0-based.
     *
     * @return the 0-based column-index of the last sprite to be shown
     */
    int columnEnd();

    /**
     * A value that states whether this {@code Animation} should loop after it has finished. Only a
     * {@link LoopingAnimation} will return {@code true} for this value.
     *
     * @return whether this {@code Animation} should loop after it has finished
     */
    default boolean loop() {
        return false;
    }

    /**
     * A value that returns the next {@code Animation} to be played, after this instance has finished. Only a
     * {@link LinkedAnimation} will support this value.
     *
     * @return the next {@code Animation} to be played, after this instance has finished
     */
    default Animation next() {
        return null;
    }

    /**
     * The {@link AnimationCallback} is a functional interface the method {@link AnimationCallback#call()}, which will
     * be called after the animation calls the last sprite.
     *
     * @return the {@link AnimationCallback}
     */
    default AnimationCallback callback() {
        return null;
    }

    /**
     * The interval for sprite cycling that will be used when playing this {@code Animation}.
     *
     * @return the interval for sprite cycling that will be used when playing this {@code Animation}
     */
    long cycleTimeInMs();
}
