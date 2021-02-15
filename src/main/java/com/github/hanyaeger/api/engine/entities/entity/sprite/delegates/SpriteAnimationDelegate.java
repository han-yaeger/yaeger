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

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of frames.
     * After construction, the spriteIndex will be set to the first frame.
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param frames    the number of frames available
     */
    @Deprecated
    public SpriteAnimationDelegate(final ImageView imageView, final int frames) {
        this(imageView, 1, frames);
    }

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

        createViewPorts(rows, columns);
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
        this.autoCycleInterval = interval * 1000000;
        this.cyclingRow = row;
        currentIndex = cyclingRow * columns;
        // TODO: Throw exception when row doesn't exist
    }

    /**
     * Set the next index of the sprite.
     */
    public void next() {
        System.out.println(String.format("Cycling row: %d, current index: %d", cyclingRow, currentIndex));
        final int lastIndexOfTheRow = cyclingRow * columns + columns - 1;
        if (cyclingRow == -1 || currentIndex < lastIndexOfTheRow) {
            setSpriteIndex(++currentIndex);
        } else {
            setSpriteIndex(cyclingRow * columns);
        }
    }

    private void createViewPorts(final int rows, final int columns) {
        var frameWidth = getFrameWidth(columns);
        var frameHeight = getFrameHeight(rows);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                addViewPort(row, column, frameWidth, frameHeight);
            }
        }
    }

    private void addViewPort(final int row, final int column, final double frameWidth, final double frameHeight) {
        viewports.add(new Rectangle2D(column * frameWidth, row * frameHeight, frameWidth, frameHeight));
    }

    private double getFrameWidth(final int columns) {
        return imageView.getImage().getWidth() / columns;
    }

    private double getFrameHeight(final int rows) {
        return imageView.getImage().getHeight() / rows;
    }
}
