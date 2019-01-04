package nl.han.ica.yaeger.engine.entities.entity.sprites;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SpriteAnimationDelegate;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import nl.han.ica.yaeger.javafx.factories.ImageViewFactory;
import nl.han.ica.yaeger.module.factories.SpriteAnimationDelegateFactory;

/**
 * A {@code SpriteEntity} is a {@code Entity} that is represented by an Image.
 */
public abstract class SpriteEntity implements Entity, ResourceConsumer {

    private final String resource;
    private final Size size;
    private SpriteAnimationDelegateFactory spriteAnimationDelegateFactory;
    private ImageRepository imageRepository;
    private ImageViewFactory imageViewFactory;


    private int frames;
    ImageView imageView;

    Point2D position;
    SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     */
    protected SpriteEntity(final String resource, final Position position, final Size size) {
        this(resource, position, size, 1);
    }

    /**
     * Instantiate a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     */
    protected SpriteEntity(final String resource, final Position position, final Size size, final int frames) {
        this.position = position;
        this.frames = frames;
        this.resource = resource;
        this.size = size;
    }

    @Override
    public void init(Injector injector) {
        var requestedWidth = size.getWidth() * frames;
        imageView = createImageView(resource, requestedWidth, size.getHeight());

        if (frames > 1) {
            spriteAnimationDelegate = spriteAnimationDelegateFactory.create(imageView, frames);
        }
    }

    private ImageView createImageView(final String resource, final int requestedWidth, final int requestedHeight) {
        var image = imageRepository.get(resource, requestedWidth, requestedHeight, true);
        return imageViewFactory.create(image);
    }

    /**
     * Rotate this {@code SpriteEntity} by the given angles.
     *
     * @param angle the rotation angle as a {@code double}
     */
    public void rotate(double angle) {
        imageView.setRotate(angle);
    }

    /**
     * Set the position of this {@code SpriteEntity}
     *
     * @param position The new {@link Position}
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index The index that should be shown. The index is zero based and the frame modulo index will be shown.
     */
    public void setCurrentFrameIndex(int index) {
        spriteAnimationDelegate.setSpriteIndex(index);
    }

    /**
     * Return the x-coordinate of this {@code SpriteEntity}.
     *
     * @return the x-coordinate
     */
    protected double getX() {
        return position.getX();
    }

    /**
     * Return the y-coordinate of this {@code SpriteEntity}.
     *
     * @return the y-coordinate
     */
    protected double getY() {
        return position.getY();
    }

    /**
     * Return the number of frames comprising this {@code SpriteEntity}.
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
        imageView.setVisible(false);
        notifyRemove();
    }

    @Override
    public Position getPosition() {
        return (Position) position;
    }

    @Inject
    public void setSpriteAnimationDelegateFactory(SpriteAnimationDelegateFactory spriteAnimationDelegateFactory) {
        this.spriteAnimationDelegateFactory = spriteAnimationDelegateFactory;
    }

    @Inject
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Inject
    public void setImageViewFactory(ImageViewFactory imageViewFactory) {
        this.imageViewFactory = imageViewFactory;
    }
}
