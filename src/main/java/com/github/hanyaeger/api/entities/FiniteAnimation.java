package com.github.hanyaeger.api.entities;

/**
 * A {@code FiniteAnimation} implements the interface {@link Animation} and should be used when the {@link Animation}
 * should be played ony once. For different behaviour, look at the other classes that implement {@link Animation}.
 */
public record FiniteAnimation(int rowStart, int columnStart, int rowEnd, int columnEnd,
                              long cycleTimeInMs) implements Animation {

    /**
     * Create a new instance of {@code LinkedAnimation}
     *
     * @param rowStart    the 0-based row-index of the first sprite to be shown
     * @param columnStart the 0-based column-index of the first sprite to be shown
     * @param rowEnd      the 0-based row-index of the last sprite to be shown
     * @param columnEnd   the 0-based column-index of the last sprite to be shown
     */
    public FiniteAnimation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd) {
        this(rowStart, columnStart, rowEnd, columnEnd, -1);
    }
}
