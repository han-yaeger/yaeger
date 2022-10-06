package com.github.hanyaeger.api.entities;


/**
 * A {@code LinkedAnimationWithCallBack} implements the interface {@link Animation} with the ability to set the next
 * {@link Animation} to be played, after this one has finished.  This way it is possible to create a linked
 * list of animations that are played in order.
 * <p>
 * Contrary to the {@link LinkedAnimation}, this version also calls the provided callback-method.
 *
 * @param rowStart      the 0-based row-index of the first sprite to be shown
 * @param columnStart   the 0-based column-index of the first sprite to be shown
 * @param rowEnd        the 0-based row-index of the last sprite to be shown
 * @param columnEnd     the 0-based column-index of the last sprite to be shown
 * @param cycleTimeInMs the interval for sprite cycling that will be used when playing this {@link Animation}
 * @param next          the {@link Animation} that should be played when this {@link Animation} has finished
 * @param callback      the {@link AnimationCallback} that muts be called when this {@link Animation} has finished
 */
public record LinkedAnimationWithCallBack(int rowStart, int columnStart, int rowEnd, int columnEnd,
                                          long cycleTimeInMs, Animation next,
                                          AnimationCallback callback) implements Animation {

    /**
     * Create a new instance of {@code LinkedAnimationWithCallBack}
     *
     * @param rowStart    the 0-based row-index of the first sprite to be shown
     * @param columnStart the 0-based column-index of the first sprite to be shown
     * @param rowEnd      the 0-based row-index of the last sprite to be shown
     * @param columnEnd   the 0-based column-index of the last sprite to be shown
     * @param next        the {@link Animation} that should be played when this {@link Animation} has finished
     * @param callback    the {@link AnimationCallback} that muts be called when this {@link Animation} has finished
     */
    public LinkedAnimationWithCallBack(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, final Animation next, final AnimationCallback callback) {
        this(rowStart, columnStart, rowEnd, columnEnd, -1, next, callback);
    }
}
