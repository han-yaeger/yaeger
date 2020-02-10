package nl.meron.yaeger.engine.entities.entity.sprite;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.JavaFXEntity;
import nl.meron.yaeger.engine.entities.entity.sprite.delegates.SpriteAnimationDelegate;
import nl.meron.yaeger.engine.media.repositories.ImageRepository;
import nl.meron.yaeger.engine.media.ResourceConsumer;
import nl.meron.yaeger.javafx.image.ImageViewFactory;
import nl.meron.yaeger.guice.factories.SpriteAnimationDelegateFactory;

/**
 * A {@link SpriteEntity} is a {@link Entity} that is represented by an Image.
 */
public abstract class SpriteEntity extends JavaFXEntity implements ResourceConsumer {

    private final String resource;
    private final Size size;
    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageRepository imageRepository;
    private ImageViewFactory imageViewFactory;

    private int frames;

    ImageView imageView;

    SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialLocation the initial {@link Location} of this Entity
     * @param size            The bounding box of this SpriteEntity.
     */
    protected SpriteEntity(final String resource, final Location initialLocation, final Size size) {
        this(resource, initialLocation, size, 1);
    }

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource        The url of the image file. Relative to the resources folder.
     * @param initialLocation the initial {@link Location} of this Entity
     * @param size            The bounding box of this SpriteEntity.
     * @param frames          The number of frames this Image contains. By default the first frame is loaded.
     */
    protected SpriteEntity(final String resource, final Location initialLocation, final Size size, final int frames) {
        super(initialLocation);
        this.frames = frames;
        this.resource = resource;
        this.size = size;
    }

    @Override
    public void init(final Injector injector) {
        var requestedWidth = size.getWidth() * frames;
        imageView = createImageView(resource, requestedWidth, size.getHeight());

        if (frames > 1) {
            spriteAnimationDelegate = spriteAnimationDelegateFactory.create(imageView, frames);
        }

        super.init(injector);
    }

    private ImageView createImageView(final String resource, final int requestedWidth, final int requestedHeight) {
        var image = imageRepository.get(resource, requestedWidth, requestedHeight, true);

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
     * Return the number of frames comprising this {@link SpriteEntity}.
     *
     * @return the number of frames as an {@code int}
     */
    protected int getFrames() {
        return frames;
    }

    @Override
    public Node getGameNode() {
        return imageView;
    }

    @Override
    public void remove() {
        imageView.setImage(null);
        super.remove();
    }

    @Override
    public void placeOnLocation(final double x, final double y) {
        if (imageView == null) {
            initialPosition = new Location(x, y);
        } else {
            imageView.setX(x);
            imageView.setY(y);
        }
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
