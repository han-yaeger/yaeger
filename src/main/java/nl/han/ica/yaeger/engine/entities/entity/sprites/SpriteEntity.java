package nl.han.ica.yaeger.engine.entities.entity.sprites;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SpriteAnimationDelegate;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.engine.media.ResourceConsumer;
import nl.han.ica.yaeger.module.YaegerModule;

/**
 * A {@code SpriteEntity} is a {@code Entity} that is represented by an Image.
 */
public abstract class SpriteEntity implements Entity, ResourceConsumer {

    ImageView imageView;
    Point2D positionVector = new Point2D(0, 0);

    private SpriteAnimationDelegate spriteAnimationDelegate;

    /**
     * Create a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     */
    public SpriteEntity(final String resource, final Position position, final Size size) {
        this(resource, position, size, 1);
    }

    /**
     * Create a new {@code SpriteEntity} for a given Image.
     *
     * @param resource The url of the image file. Relative to the resources folder.
     * @param position the initial {@link Position} of this Entity
     * @param size     The bounding box of this SpriteEntity.
     * @param frames   The number of frames this Image contains. By default the first frame is loaded.
     */
    public SpriteEntity(final String resource, final Position position, final Size size, final int frames) {
        this.positionVector = position;

        ImageRepository imageRepository = ImageRepository.getInstance();
        Injector injector = Guice.createInjector(new YaegerModule());
        injector.injectMembers(imageRepository);

        var image = imageRepository.get(resource, size.getWidth(), size.getHeight(), true);
        imageView = new ImageView(image);
        imageView.setManaged(false);

        if (frames > 1) {
            spriteAnimationDelegate = new SpriteAnimationDelegate(imageView, frames);
        }
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
     * Set the positionVector of this {@code SpriteEntity}
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public void setLocation(double x, double y) {
        positionVector = new Point2D(x, y);
    }

    /**
     * Set the current frame index of the Sprite image.
     *
     * @param index The index that should be shown. The index is zero based and the frame modulo index will be shown.
     */
    public void setCurrentFrameIndex(int index) {
        spriteAnimationDelegate.setSpriteIndex(imageView, index);
    }

    /**
     * Return the x-coordinate of this {@code SpriteEntity}.
     *
     * @return the x-coordinate
     */
    protected double getX() {
        return positionVector.getX();
    }

    /**
     * Return the y-coordinate of this {@code SpriteEntity}.
     *
     * @return the y-coordinate
     */
    protected double getY() {
        return positionVector.getY();
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
        return (Position) positionVector;
    }
}
