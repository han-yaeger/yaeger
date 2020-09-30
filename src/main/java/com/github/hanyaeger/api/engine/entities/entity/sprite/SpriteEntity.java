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
 * A {@link SpriteEntity} is a {@link YaegerEntity} that is represented by an Image.
 */
public abstract class SpriteEntity extends YaegerEntity implements ResourceConsumer {

    private final String resource;
    private final Size size;
    private boolean preserveAspectRatio = true;
    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageRepository imageRepository;
    private ImageViewFactory imageViewFactory;

    private int frames;

    protected Optional<ImageView> imageView;
    protected SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param location the initial {@link Coordinate2D} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     */
    protected SpriteEntity(final String resource, final Coordinate2D location, final Size size) {
        this(resource, location, size, 1);
    }

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param location the initial {@link Coordinate2D} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     */
    protected SpriteEntity(final String resource, final Coordinate2D location, final Size size, final int frames) {
        super(location);
        this.frames = frames;
        this.resource = resource;
        this.size = size;
        this.imageView = Optional.empty();
    }

    @Override
    public void init(final Injector injector) {
        var requestedWidth = size.getWidth() * frames;
        imageView = Optional.of(createImageView(resource, requestedWidth, size.getHeight(), preserveAspectRatio));

        if (frames > 1) {
            spriteAnimationDelegate = spriteAnimationDelegateFactory.create(imageView.get(), frames);
        }

        super.init(injector);
    }

    private ImageView createImageView(final String resource, final double requestedWidth, final double requestedHeight, final boolean preserveAspectRatio) {
        var image = imageRepository.get(resource, requestedWidth, requestedHeight, preserveAspectRatio);

        return imageViewFactory.create(image);
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index The index that should be shown. The index is zero based and the frame modulo index will be shown.
     */
    public void setCurrentFrameIndex(final int index) {
        spriteAnimationDelegate.setSpriteIndex(index);
    }

    /**
     * Preserve the aspect ration of the width and height of this {@link SpriteEntity}.
     *
     * @param preserveAspectRatio {@code true} if the ratio should be preserved. {@code false} otherwise.
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

    @Override
    public Optional<Node> getNode() {
        if (imageView.isPresent()) {
            return Optional.of(imageView.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void remove() {
        imageView.get().setImage(null);
        super.remove();
    }

    @Override
    public void setReferenceX(double x) {
        imageView.ifPresentOrElse(imageView -> imageView.setX(x), () -> this.x = x);
    }

    @Override
    public void setReferenceY(double y) {
        imageView.ifPresentOrElse(imageView -> imageView.setY(y), () -> this.y = y);
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
