package com.github.hanyaeger.api.entities;

/**
 * An {@code Animation} encapsulate an animation that uses only a selection of the frames from a sprite sheet.
 */
public record Animation(int rowStart, int columnStart, int rowEnd, int columnEnd, boolean loop, long cycleTimeInMs) {

    public static long DEFAULT_AUTOCYCLE_INTERVAL = 100;

    // TODO document
    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd) {
        this(rowStart, columnStart, rowEnd, columnEnd, false, -1);
    }

    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, final boolean loop) {
        this(rowStart, columnStart, rowEnd, columnEnd, loop, -1);
    }

    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, final long cycleTimeInMs) {
        this(rowStart, columnStart, rowEnd, columnEnd, false, cycleTimeInMs);
    }
}
