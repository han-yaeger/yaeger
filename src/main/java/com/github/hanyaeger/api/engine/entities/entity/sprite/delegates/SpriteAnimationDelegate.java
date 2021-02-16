package com.github.hanyaeger.api.engine.entities.entity.sprite.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import com.github.hanyaeger.api.engine.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A {@code SpriteAnimationDelegate} holds all responsibility related to Sprites that contain multiple images.
 */
public class SpriteAnimationDelegate implements Updatable {

    private final int rows;
    private final int columns;

    private long previousCycleTime = 0;
    private long autoCycleInterval = 0;
    private final ImageView imageView;
    private final List<Rectangle2D> viewports = new ArrayList<>();
    private int currentIndex = 0;
    private int cyclingRow = -1;

    private static final String INVALID_ROW_EXCEPTION = "Cannot auto-cycle through row %d because" +
            " this sprite only has %d rows. Rows and columns are zero-indexed.";

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of rows and columns.
     * After construction, the spriteIndex will be set to the first frame (top-left).
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param rows    the number of rows available
     * @param columns the number of columns available
     */
    public SpriteAnimationDelegate(final ImageView imageView, final int rows, final int columns) {
        this.imageView = imageView;
        this.rows = rows;
        this.columns = columns;

        createViewPorts();
        setSpriteIndex(0);
    }

    /**
     * Set the index of the sprite. Since the modulus (mod frames) is used, this can be an unbounded integer.
     *
     * @param index the index to select. This index will be applied modulo the total number
     *              of frames
     */
    public void setSpriteIndex(final int index) {
        var modulus = index % viewports.size();
        imageView.setViewport(viewports.get(modulus));
        currentIndex = index;
    }

    /**
     * Return the current index of the sprite.
     *
     * @return the index of the sprite as an {@code int}
     */
    public int getFrameIndex() {
        return currentIndex;
    }

    @Override
    public void update(final long timestamp) {
        if (autoCycleInterval == 0) {
            return;
        }

        if (timestamp > previousCycleTime + autoCycleInterval) {
            next();
            previousCycleTime = timestamp;
        }
    }

    /**
     * Set the interval at which the sprite should be automatically cycled
     *
     * @param interval the interval milli-seconds
     */
    public void setAutoCycle(final long interval) {
        this.autoCycleInterval = interval * 1000000;
    }

    /**
     * Set the interval at which the sprite should be automatically cycled
     * and which row to cycle through.
     *
     * @param interval the interval milli-seconds
     * @param row the row to cycle through (zero-indexed)
     */
    public void setAutoCycle(final long interval, final int row) {
        if (row >= rows) {
            String message = String.format(INVALID_ROW_EXCEPTION, row, rows);
            throw new IllegalArgumentException(message);
        }

        this.autoCycleInterval = interval * 1000000;
        this.cyclingRow = row;
        currentIndex = cyclingRow * columns;
    }

    /**
     * Set the next index of the sprite.
     */
    public void next() {
        final int lastIndexOfTheRow = cyclingRow * columns + columns - 1;
        if (cyclingRow == -1 || currentIndex < lastIndexOfTheRow) {
            setSpriteIndex(++currentIndex);
        } else {
            setSpriteIndex(cyclingRow * columns);
        }
    }

    private void createViewPorts() {
        var frameWidth = getFrameWidth();
        var frameHeight = getFrameHeight();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                addViewPort(row, column, frameWidth, frameHeight);
            }
        }
    }

    private void addViewPort(final int row, final int column, final double frameWidth, final double frameHeight) {
        viewports.add(new Rectangle2D(column * frameWidth, row * frameHeight, frameWidth, frameHeight));
    }

    private double getFrameWidth() {
        return imageView.getImage().getWidth() / columns;
    }

    private double getFrameHeight() {
        return imageView.getImage().getHeight() / rows;
    }
}
