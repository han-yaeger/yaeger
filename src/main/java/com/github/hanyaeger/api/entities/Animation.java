package com.github.hanyaeger.api.entities;

public class Animation {

    private int rowStart;
    private int columnStart;
    private int rowEnd;
    private int columnEnd;
    private boolean loop;
    private int cycleTimeInMs;

    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd) {
        this(rowStart, columnStart, rowEnd, columnEnd, false, 0);
    }

    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, final boolean loop) {
        this(rowStart, columnStart, rowEnd, columnEnd, loop, 0);
    }

    public Animation(final int rowStart, final int columnStart, final int rowEnd, final int columnEnd, final boolean loop, final int cycleTimeInMs) {
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.rowEnd = rowEnd;
        this.columnEnd = columnEnd;
        this.loop = loop;
        this.cycleTimeInMs = cycleTimeInMs;
    }
}
