package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.Animation;
import com.github.hanyaeger.api.entities.FiniteAnimation;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import com.github.hanyaeger.core.Updatable;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code SpriteAnimationDelegate} holds all responsibility related to Sprites that contain multiple images.
 */
public class SpriteAnimationDelegate implements Updatable {

    private static final int MILLI_TO_NANO_FACTOR = 1000000;

    private final int rows;
    private final int columns;

    private long previousCycleTime = 0;
    private long autoCycleInterval = 0;
    private final ImageView imageView;
    private final List<Rectangle2D> viewports = new ArrayList<>();
    private int currentIndex = 0;

    private Animation currentAnimation;
    private Animation queuedAnimation;
    private int animationEndIndex = -1;

    private int cyclingRow = -1;

    private static final String INVALID_ROW_EXCEPTION = "Cannot auto-cycle through row %d because" +
            " this sprite only has %d rows. Rows and columns are zero-indexed.";

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of rows and columns.
     * After construction, the spriteIndex will be set to the first frame (top-left).
     *
     * @param imageView the {@link ImageView} for which the different frames should be created
     * @param rows      the number of rows available
     * @param columns   the number of columns available
     */
    public SpriteAnimationDelegate(final ImageView imageView, final int rows, final int columns) {
        this.imageView = imageView;
        this.rows = rows;
        this.columns = columns;

        createViewPorts();
        setFrameIndex(0);
    }

    /**
     * Set the index of the sprite. Since the modulus (mod frames) is used, this can be an unbounded integer.
     *
     * @param index the index to select. This index will be applied modulo the total number
     *              of frames
     */
    public void setFrameIndex(final int index) {
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
            updateFrameIndex();
            next();
            previousCycleTime = timestamp;
        }
    }

    /**
     * Set the interval at which the sprite should be automatically cycled
     * and which row to cycle through.
     *
     * @param interval the interval in milliseconds
     * @param row      the row to cycle through (zero-indexed)
     */
    public void setAutoCycle(final long interval, final int row) {
        setAutoCycleInterval(interval);
        setAutoCycleRow(row);
    }

    /**
     * set the interval at which the sprite should be automatically cycled.
     * The row will remain the same. To set the row that needs to be cycled,
     * use the method {@link #setAutoCycleRow(int)}, or {@link #setAutoCycle(long, int)}
     * to set them both at once.
     *
     * @param interval the interval in milliseconds
     */
    public void setAutoCycleInterval(final long interval) {
        this.autoCycleInterval = interval * MILLI_TO_NANO_FACTOR;
    }

    /**
     * Return the current auto-cycle interval
     *
     * @return the current auto-cycle interval in milliseconds
     */
    public long getAutoCycleInterval() {
        return autoCycleInterval == 0 ? 0 : autoCycleInterval / MILLI_TO_NANO_FACTOR;
    }

    /**
     * Set the row through which the sprite should be automatically cycled.
     *
     * @param row the row to cycle through (zero-indexed)
     */
    public void setAutoCycleRow(final int row) {
        if (row >= rows || row < -1) {
            final var message = String.format(INVALID_ROW_EXCEPTION, row, rows);
            throw new IllegalArgumentException(message);
        }

        applyNewCurrentIndex(row);
    }

    /**
     * Return the row that is currently set as the only row to cycle through. If
     * a value of -1 is returned, all rows are cycled through.
     *
     * @return the cycling row
     */
    public int getCyclingRow() {
        return cyclingRow;
    }

    private void applyNewCurrentIndex(final int row) {
        if (cyclingRow == -1 && row == 0) {
            this.cyclingRow = row;
        } else if (row != -1 && row != cyclingRow) {
            this.cyclingRow = row;
            currentIndex = cyclingRow * columns;
        }
    }

    private void next() {
        if (currentAnimation != null && currentIndex == animationEndIndex) {
            // If a callback is present call it
            if (currentAnimation.callback() != null) {
                currentAnimation.callback().call();
            }
            if (queuedAnimation != null) {
                playAnimation(queuedAnimation, false);
            } else if (currentAnimation.loop()) {
                currentIndex = getIndexForFrameOn(currentAnimation.rowStart(), currentAnimation.columnStart());
            } else if (currentAnimation.next() != null) {
                playAnimation(currentAnimation.next(), false);
            } else {
                currentAnimation = null;
                setAutoCycleInterval(0);
            }
        } else {
            final var lastIndexOfTheRow = cyclingRow * columns + columns - 1;
            if (cyclingRow == -1 || currentIndex < lastIndexOfTheRow) {
                currentIndex++;
            } else {
                currentIndex = cyclingRow * columns;
            }
        }
    }

    private void updateFrameIndex() {
        setFrameIndex(currentIndex);
    }

    /**
     * Play the given {@link FiniteAnimation}. The actual settings of the {@link FiniteAnimation} can be set through the
     * constructor of {@link FiniteAnimation}.
     *
     * @param animation     the {@link FiniteAnimation} to be played
     * @param restartIfSame if the same {@link Animation} is currently being played, this {@code boolean}
     *                      states if the {@link Animation} should be restarted or should continue with the
     *                      current frame.
     */
    public void playAnimation(final Animation animation, final boolean restartIfSame) {
        if (!restartIfSame && animation.equals(currentAnimation)) {
            return;
        }

        currentAnimation = animation;

        // Disable the auto cycle row
        cyclingRow = -1;

        // Set the correct interval for auto cycling
        if (animation.cycleTimeInMs() == -1 && autoCycleInterval == 0) {
            setAutoCycleInterval(FiniteAnimation.DEFAULT_AUTOCYCLE_INTERVAL);
        }

        // Calculate the new value for currentIndex
        currentIndex = getIndexForFrameOn(animation.rowStart(), animation.columnStart());

        // Calculate at which index this animation should end
        animationEndIndex = getIndexForFrameOn(animation.rowEnd(), animation.columnEnd());
    }

    /**
     * Return the {@link Animation} that is currently being played. If the {@link Animation} has ended, the
     * behaviour is based on the type of {@link Animation}. If the current animation is a {@link FiniteAnimation},
     * this method will return {@code null} when it has finished.
     *
     * @return the {@link Animation} that is currently being played or {@code null if none are played}
     */
    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Queue the given {@link Animation} ensures it is played after the current {@link Animation}, regardless
     * of the type of the current {@link Animation}. the queued {@link Animation} will overwrite both the
     * {@code next} {@link Animation} as defined in a {@link com.github.hanyaeger.api.entities.LinkedAnimation}
     * and overwrite the looping behaviour of a {@link com.github.hanyaeger.api.entities.LoopingAnimation}.
     * <p>
     * To un-queue the queued {@link Animation}, call {@link #unQueueAnimation()}.
     *
     * @param animation the {@link Animation} to be queued
     * @throws YaegerEngineException thrown if no {@link Animation} is currently being played
     */
    public void queueAnimation(final Animation animation) {
        if (currentAnimation == null) {
            throw new YaegerEngineException("Unable to queue animation: " + animation + "if no animation is being played.");
        }
        this.queuedAnimation = animation;
    }

    /**
     * Call this method to un-queue the {@link Animation} that is currently queued. This method must be called
     * before the current {@link Animation} has finished.
     *
     * @return the {@link Animation} that is un-queued
     * @throws YaegerEngineException thrown if no animation is currently queued
     */
    public Animation unQueueAnimation() {
        if (queuedAnimation == null) {
            throw new YaegerEngineException("The animation can not be un-queued, because no animation is currently queued.");
        }

        var animation = queuedAnimation;
        this.queuedAnimation = null;

        return animation;
    }

    /**
     * Call this method to un-queue the specific {@link Animation}, if it is currently queued.
     * This method must be called before the current {@link Animation} has finished and will only
     * un-queue the queued {@link Animation} if it is the same as the {@link Animation} passed as the
     * argument.
     *
     * @param animation the {@link Animation} that should be un-queued
     */
    public void unQueueAnimation(final Animation animation) {
        if (animation.equals(queuedAnimation)) {
            this.queuedAnimation = null;
        }
    }

    private int getIndexForFrameOn(final int row, final int column) {
        return column + (row * columns);
    }

    private void createViewPorts() {
        final var frameWidth = getFrameWidth();
        final var frameHeight = getFrameHeight();

        for (var row = 0; row < rows; row++) {
            for (var column = 0; column < columns; column++) {
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
