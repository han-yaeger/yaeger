package nl.meron.yaeger.engine.entities.tilemap;

import com.google.inject.Inject;
import nl.meron.yaeger.engine.Activatable;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Anchorable;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.sprite.SpriteEntity;
import nl.meron.yaeger.engine.exceptions.EntityNotAvailableException;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
import nl.meron.yaeger.engine.scenes.YaegerScene;

import java.util.*;

/**
 * A {@link TileMap} encapsulate a two-dimensional map of {@link SpriteEntity}.
 * <p>
 * By default a {@link TileMap} will assume
 * the full width of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} must be used for placing the tiles.
 * For this, it will require both tiles to be added, as a map to be defined. Based on those, it will automatically
 * calculate the width, height and placement of all tiles.
 */
public abstract class TileMap extends EntitySupplier implements Anchorable, Activatable {

    private Map<Integer, Class<? extends SpriteEntity>> entities = new HashMap<>();

    private int[][] map;
    private transient TileFactory tileFactory;
    protected transient Optional<Size> size = Optional.empty();
    protected final transient Optional<Location> location;
    private transient AnchorPoint anchorPoint = AnchorPoint.TOP_LEFT;

    /**
     * Create a new {@link TileMap} that takes up the full width and height of the
     * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
     */
    public TileMap() {
        this(new Location(0, 0), null);
    }

    /**
     * Create a new {@link TileMap} with the given width and height, placed on the given x and y.
     *
     * @param location The {@link Location} of the top-left corner of the {@link TileMap}.
     * @param size     The {@link Size} of the {@link TileMap}.
     */
    public TileMap(final Location location, final Size size) {
        this.location = Optional.of(location);
        if (size != null) {
            this.size = Optional.of(size);
        }
    }

    /**
     * The lifecycle method {@link #setupEntities()} should be used to add the instances of {@link Entity}
     * that should be used in this {@link TileMap}.
     */
    public abstract void setupEntities();

    /**
     * The lifecycle method {@link #defineMap()} should be used to define the map that should be used. It is
     * represented by a two dimensional array of type {@code int}, where each cell represents an {@link Entity}
     * on the map. The first array ({@code int[]}) defines the rows. Each entry in this array is itself an array
     * containing the columns of the given row.
     * <p>
     * This way, the following array:
     * <pre>
     *      {@code
     *          int[][] map = {
     *              {0, 0, 0, 0, 0, 0, 0 },
     *              {0, 0, 2, 0, 3, 3, 3},
     *              {2, 3, 0, 0, 0, 0, 1}}
     *      }
     * </pre>
     * will place three rows of seven entities. In this case there should be four Entities added through the
     * {@link #addEntity(int, Class)} method.
     *
     * @return The two dimensional array representing the map.
     */
    public abstract int[][] defineMap();

    /**
     * Add the {@link Class} of an {@link SpriteEntity} that can be used in this {@link TileMap}. Each added
     * {@link SpriteEntity} {@link Class}must have an identifier for reference from the map. This method should only be called
     * from the lifecycle method {@link TileMap#setupEntities()}.
     *
     * @param identifier  The identifier as an {@code int} to be used from the map.
     * @param entityClass The {@link Class} of a subclass of {@link SpriteEntity} to be
     *                    used for the given identifier.
     */
    public void addEntity(final int identifier, final Class<? extends SpriteEntity> entityClass) {
        entities.put(identifier, entityClass);
    }

    private void transformMapToEntities() {
        double x;
        double y;
        double width;
        double height;

        if (size.isPresent() && location.isPresent()) {
            width = size.get().getWidth();
            height = size.get().getHeight();

            var topLeftLocation = getTopLeftLocation(location.get(), size.get());
            x = topLeftLocation.getX();
            y = topLeftLocation.getY();
        } else {
            throw new YaegerEngineException("No Size or Location is set for this EntityMap. Has setDimensionProvider been called?");
        }

        for (int i = 0; i < map.length; i++) {
            var entityHeight = height / map.length;
            var entityY = i * entityHeight;
            for (int j = 0; j < map[i].length; j++) {
                int key = map[i][j];
                if (key != 0) {
                    var entityWidth = width / map[i].length;

                    var entityClass = entities.get(key);

                    if (entityClass == null) {
                        throw new EntityNotAvailableException("An Entity with key \"" + key + "\" has not been added to the EntityMap.");
                    }

                    var entity = tileFactory.create(entityClass,
                            new Location(x + (j * entityWidth), y + entityY),
                            new Size(entityWidth, entityHeight));

                    add(entity);
                }
            }
        }
    }

    private Location getTopLeftLocation(Location location, Size size) {
        switch (anchorPoint) {
            case TOP_CENTER:
                return new Location(location.getX() - (size.getWidth() / 2), location.getY());
            case TOP_RIGHT:
                return new Location(location.getX() - size.getWidth(), location.getY());
            case CENTER_LEFT:
                return new Location(location.getX(), location.getY() - (size.getHeight() / 2));
            case CENTER_CENTER:
                return new Location(location.getX() - (size.getWidth() / 2), location.getY() - (size.getHeight() / 2));
            case CENTER_RIGHT:
                return new Location(location.getX() - (size.getWidth()), location.getY() - (size.getHeight() / 2));
            case BOTTOM_LEFT:
                return new Location(location.getX(), location.getY() - size.getHeight());
            case BOTTOM_CENTER:
                return new Location(location.getX() - (size.getWidth() / 2), location.getY() - (size.getHeight()));
            case BOTTOM_RIGHT:
                return new Location(location.getX() - size.getWidth(), location.getY() - size.getHeight());
            default:
                return location;
        }
    }

    @Override
    public void activate() {
        setupEntities();
        map = defineMap();
        transformMapToEntities();
    }

    /**
     * A {@link DimensionsProvider} is required because the {@link TileMap} will need info on the width
     * and height of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} to calculate the placement
     * of the individual instances of {@link Entity}.
     *
     * @param dimensionsProvider The {@link DimensionsProvider} that provides a {@link DimensionsProvider#getWidth()} and
     *                           {@link DimensionsProvider#getHeight()} method; most likely an {@link YaegerScene}.
     */
    void setDimensionsProvider(final DimensionsProvider dimensionsProvider) {
        if (!size.isPresent()) {
            size = Optional.of(new Size(dimensionsProvider.getWidth(), dimensionsProvider.getHeight()));
        }
    }

    @Override
    public void setAnchorPoint(AnchorPoint anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    @Override
    public AnchorPoint getAnchorPoint() {
        return anchorPoint;
    }

    @Inject
    public void setTileFactory(TileFactory tileFactory) {
        this.tileFactory = tileFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TileMap entities1 = (TileMap) o;
        return entities.equals(entities1.entities) &&
                Arrays.equals(map, entities1.map) &&
                size.equals(entities1.size) &&
                location.equals(entities1.location);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), entities, size, location);
        result = 31 * result + Arrays.hashCode(map);
        return result;
    }
}
