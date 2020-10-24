package com.github.hanyaeger.api.engine.entities.entity.sprite;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import com.github.hanyaeger.api.engine.Size;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.sprite.delegates.SpriteAnimationDelegate;
import com.github.hanyaeger.api.engine.media.repositories.ImageRepository;
import com.github.hanyaeger.api.engine.media.ResourceConsumer;
import com.github.hanyaeger.api.javafx.image.ImageViewFactory;
import com.github.hanyaeger.api.guice.factories.SpriteAnimationDelegateFactory;

import java.util.Optional;

/**
 * A {@link SpriteEntity} is a {@link YaegerEntity} that is represented by an Image. When creating
 * a {@link SpriteEntity}, the {@link Size} of the image must be passed through the constructor. After
 * construction, this {@link Size} can not be changed.
 */
public abstract class SpriteEntity extends YaegerEntity implements ResourceConsumer {

    private final String resource;
    private final Size size;
    private boolean preserveAspectRatio = true;
    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageRepository imageRepository;
    private ImageViewFactory imageViewFactory;

    private final int frames;
    private Optional<Integer> spriteIndex = Optional.empty();
    protected Optional<ImageView> imageView = Optional.empty();
    protected Optional<SpriteAnimationDelegate> spriteAnimationDelegate = Optional.empty();

    /**
     * Instantiate a new {@link SpriteEntity} for a given image.
     *
     * @param resource the url of the image file. Relative to the resources folder
     * @param location the initial {@link Coordinate2D} of this {@link SpriteEntity}
     * @param size     The bounding box of this {@link SpriteEntity}
     */
    protected SpriteEntity(final String resource, final Coordinate2D location, final Size size) {
        this(resource, location, size, 1);
    }

    /**
     * Instantiate a new {@link SpriteEntity} for a given image.
     *
     * @param resource the url of the image file. Relative to the resources folder
     * @param location the initial {@link Coordinate2D} of this {@link SpriteEntity}
     * @param size     The bounding box of this {@link SpriteEntity}
     * @param frames   The number of frames the image contains. By default the first frame is loaded
     */
    protected SpriteEntity(final String resource, final Coordinate2D location, final Size size, final int frames) {
        super(location);
        this.frames = frames;
        this.resource = resource;
        this.size = size;
    }

    @Override
    public void init(final Injector injector) {
        var requestedWidth = size.getWidth() * frames;
        imageView = Optional.of(createImageView(resource, requestedWidth, size.getHeight(), preserveAspectRatio));

        if (frames > 1) {
            spriteAnimationDelegate = Optional.of(spriteAnimationDelegateFactory.create(imageView.get(), frames));
        }

        spriteIndex.ifPresent(index -> spriteAnimationDelegate.ifPresent(sad -> sad.setSpriteIndex(index)));

        super.init(injector);
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index the index that should be shown. The index is zero based and the frame modulo index will be shown
     */
    public void setCurrentFrameIndex(final int index) {
        spriteAnimationDelegate.ifPresentOrElse(delegate -> delegate.setSpriteIndex(index),
                () -> spriteIndex = Optional.of(index));
    }

    /**
     * Preserve the aspect ration of the width and height of this {@link SpriteEntity}.
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
        return frames;
    }

    private ImageView createImageView(final String resource, final double requestedWidth, final double requestedHeight, final boolean preserveAspectRatio) {
        var image = imageRepository.get(resource, requestedWidth, requestedHeight, preserveAspectRatio);

        return imageViewFactory.create(image);
    }

    @Override
    public Optional<? extends Node> getNode() {
        return imageView;
    }

    @Override
    public final void remove() {
        imageView.ifPresent(imageView -> imageView.setImage(null));
        super.remove();
    }

    @Override
    public final void setAnchorLocation(Coordinate2D anchorLocation) {
        super.setAnchorLocation(anchorLocation);
        imageView.ifPresent(imageView -> {
            imageView.setX(anchorLocation.getX());
            imageView.setY(anchorLocation.getY());
        });
    }

    @Inject
    public void setSpriteAnimationDelegateFactory(final SpriteAnimationDelegateFactory spriteAnimationDelegateFactory) {
        this.spriteAnimationDelegateFactory = spriteAnimationDelegateFactory;
    }

    @Inject
    public void setImageRepository(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Inject
    public void setImageViewFactory(final ImageViewFactory imageViewFactory) {
        this.imageViewFactory = imageViewFactory;
    }
}
