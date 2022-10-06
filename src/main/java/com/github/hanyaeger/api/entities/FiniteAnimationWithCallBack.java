package com.github.hanyaeger.api.entities;

/**
 * A {@code FiniteAnimation} implements the interface {@link Animation} and should be used when the {@link Animation}
 * should be played ony once. For different behaviour, look at the other classes that implement {@link Animation}.
 * <p>
 * Contrary to the {@link LinkedAnimation}, this version also calls the provided callback-method.
 *
 * @param rowStart      the 0-based row-index of the first sprite to be shown
 * @param columnStart   the 0-based column-index of the first sprite to be shown
 * @param rowEnd        the 0-based row-index of the last sprite to be shown
 * @param columnEnd     the 0-based column-index of the last sprite to be shown
 * @param cycleTimeInMs the interval for sprite cycling that will be used when playing this {@link Animation}
 * @param callback      the {@link AnimationCallback} that muts be called when this {@link Animation} has finished
 */
public record FiniteAnimationWithCallBack(int rowStart, int columnStart, int rowEnd, int columnEnd,
                                          long cycleTimeInMs,
                                          AnimationCallback callback) implements Animation {

    /**
     * Create a new instance of {@code FiniteAnimationWithCallBack}
     *
     * @param rowStart    the 0-based row-index of the first sprite to be shown
     * @param columnStart the 0-based column-index of the first sprite to be shown
     * @param rowEnd      the 0-based row-index of the last sprite to be shown
     * @param columnEnd   the 0-based column-index of the last sprite to be shown
     * @param callback    the {@link AnimationCallback} that muts be called when this {@link Animation} has finished
     */
    public FiniteAnimationWithCallBack(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, AnimationCallback callback) {
        this(rowStart, columnStart, rowEnd, columnEnd, -1, callback);
    }
}
