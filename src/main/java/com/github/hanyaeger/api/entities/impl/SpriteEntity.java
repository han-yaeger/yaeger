package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.entities.SpriteAnimationDelegate;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.core.repositories.ImageRepository;
import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.factories.image.ImageViewFactory;
import com.github.hanyaeger.core.factories.image.SpriteAnimationDelegateFactory;

import java.util.Optional;

/**
 * A {@link SpriteEntity} is a {@link YaegerEntity} that is represented by an Image. When creating
 * a {@link SpriteEntity}, the {@link Size} of the image must be passed through the constructor. After
 * construction, this {@link Size} can not be changed.
 */
public abstract class SpriteEntity extends YaegerEntity implements ResourceConsumer {

    private final String resource;
    private Size size;
    private boolean preserveAspectRatio = true;
    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageRepository imageRepository;
    private ImageViewFactory imageViewFactory;
    private final int rows;
    private final int columns;
    private Optional<Integer> spriteIndex = Optional.empty();
    Optional<ImageView> imageView = Optional.empty();
    Optional<SpriteAnimationDelegate> spriteAnimationDelegate = Optional.empty();

    /**
     * Instantiate a new {@link SpriteEntity} for an image with the given {@code resource}.
     * This {@link SpriteEntity} will use the original dimensions of the image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this {@link SpriteEntity}
     */
    protected SpriteEntity(final String resource, final Coordinate2D initialLocation) {
        this(resource, initialLocation, null);
    }

    /**
     * Instantiate a new {@link SpriteEntity} for a given image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this {@link SpriteEntity}
     * @param rows            the number of rows the image contains
     * @param columns         the number of columns the image contains
     */
    protected SpriteEntity(final String resource, final Coordinate2D initialLocation, final int rows, final int columns) {
        this(resource, initialLocation, null, rows, columns);
    }

    /**
     * Instantiate a new {@link SpriteEntity} for a given image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this {@link SpriteEntity}
     * @param size            the bounding box of this {@link SpriteEntity}
     */
    protected SpriteEntity(final String resource, final Coordinate2D initialLocation, final Size size) {
        this(resource, initialLocation, size, 1, 1);
    }

    /**
     * Instantiate a new {@link SpriteEntity} for a given image.
     *
     * @param resource        the url of the image file. Relative to the resources folder
     * @param initialLocation the initial {@link Coordinate2D} of this {@link SpriteEntity}
     * @param size            the bounding box of this {@link SpriteEntity}
     * @param rows            the number of rows the image contains
     * @param columns         the number of columns the image contains
     */
    protected SpriteEntity(final String resource, final Coordinate2D initialLocation, final Size size, final int rows, final int columns) {
        super(initialLocation);
        this.rows = rows;
        this.columns = columns;
        this.resource = resource;
        this.size = size;
    }

    @Override
    public void init(final Injector injector) {
        if (size != null) {
            final var requestedWidth = size.width() * columns;
            final var requestedHeight = size.height() * rows;
            imageView = Optional.of(createImageView(resource, requestedWidth, requestedHeight, preserveAspectRatio));
        } else {
            imageView = Optional.of(createImageView(resource));
        }

        if (rows > 1 || columns > 1) {
            spriteAnimationDelegate = Optional.of(spriteAnimationDelegateFactory.create(imageView.get(), rows, columns));
        }

        spriteIndex.ifPresent(index -> spriteAnimationDelegate.ifPresent(sad -> sad.setFrameIndex(index)));

        super.init(injector);
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index the index that should be shown.
     *              The index is zero based and increases from left to right.
     *              When the last index is reached, the index will wrap to the next row.
     *              The frame modulo index will be shown
     */
    public void setCurrentFrameIndex(final int index) {
        spriteAnimationDelegate.ifPresentOrElse(delegate -> delegate.setFrameIndex(index),
                () -> spriteIndex = Optional.of(index));
    }

    /**
     * Return the current index of the sprite.
     *
     * @return the index of the sprite as an {@code int}
     */
    public int getCurrentFrameIndex() {
        return spriteAnimationDelegate.map(SpriteAnimationDelegate::getFrameIndex).orElseGet(() -> spriteIndex.orElse(0));
    }

    /**
     * Preserve the aspect ratio of the width and height of this {@link SpriteEntity}.
     *
     * @param preserveAspectRatio {@code true} if the ratio should be preserved, {@code false} otherwise
     */
    public void setPreserveAspectRatio(boolean preserveAspectRatio) {
        this.preserveAspectRatio = preserveAspectRatio;
    }

    /**
     * Return the number of frames comprising this {@link SpriteEntity}.
     *
     * @return the number of frames as an {@code int}
     */
    protected int getFrames() {
        return rows * columns;
    }

    private ImageView createImageView(final String resource) {
        var image = imageRepository.get(resource);
        size = new Size(image.getWidth(), image.getHeight());

        return imageViewFactory.create(image);
    }

    private ImageView createImageView(final String resource, final double requestedWidth, final double requestedHeight, final boolean preserveAspectRatio) {
        final var image = imageRepository.get(resource, requestedWidth, requestedHeight, preserveAspectRatio);

        return imageViewFactory.create(image);
    }

    @Override
    public Optional<? extends Node> getNode() {
        return imageView;
    }

    @Override
    public final void remove() {
        imageView.ifPresent(iV -> iV.setImage(null));
        super.remove();
    }

    @Override
    public final void setAnchorLocation(Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        imageView.ifPresent(iV -> {
            iV.setX(anchorLocation.getX());
            iV.setY(anchorLocation.getY());
        });
    }

    /**
     * Set the {@link SpriteAnimationDelegateFactory} to be used.
     *
     * @param spriteAnimationDelegateFactory an instance of {@link SpriteAnimationDelegateFactory}
     */
    @Inject
    public void setSpriteAnimationDelegateFactory(final SpriteAnimationDelegateFactory spriteAnimationDelegateFactory) {
        this.spriteAnimationDelegateFactory = spriteAnimationDelegateFactory;
    }

    /**
     * Set the {@link ImageRepository} to be used.
     *
     * @param imageRepository an instance of {@link ImageRepository}
     */
    @Inject
    public void setImageRepository(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Set the {@link ImageViewFactory} to be used.
     *
     * @param imageViewFactory an instance of {@link ImageViewFactory}
     */
    @Inject
    public void setImageViewFactory(final ImageViewFactory imageViewFactory) {
        this.imageViewFactory = imageViewFactory;
    }
}
