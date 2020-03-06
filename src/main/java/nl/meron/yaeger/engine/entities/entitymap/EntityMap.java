package nl.meron.yaeger.engine.entities.entitymap;

import nl.meron.yaeger.engine.Configurable;
import nl.meron.yaeger.engine.Size;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;
import nl.meron.yaeger.engine.scenes.DimensionsProvider;
import nl.meron.yaeger.engine.scenes.YaegerScene;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link EntityMap} encapsulate a two-dimensional map of Entities; a structure sometimes known as
 * a SpriteMap.
 * <p>
 * By default an {@link EntityMap} will assume
 * the full width of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} must be used for placing the sprites. For this, it
 * will require both sprites to be added, as a map to be defined. Based on those, it will automatically calculate the width,
 * height and placement of all sprites.
 */
public abstract class EntityMap extends EntitySupplier implements Configurable {

    private Map<Integer, Class<? extends Entity>> entities = new HashMap<>();
    private DimensionsProvider dimensionsProvider;

    private int[][] map;
    private double sceneWidth;
    private double sceneHeight;
    private double x;
    private double y;

    /**
     * Create a new {@link EntityMap} that takes up the full width and height of the
     * {@link nl.meron.yaeger.engine.scenes.YaegerScene}.
     */
    public EntityMap() {
        this(0, 0, -1, -1);
    }

    /**
     * Create a new {@link EntityMap} with the given width and height, placed on the given x and y.
     *
     * @param x          The x-coordinate of the top-left corner of the {@link EntityMap}.
     * @param y          The y-coordinate of the top-left corner of the {@link EntityMap}.
     * @param sceneWidth The width of the {@link EntityMap}.
     * @param height     The height of the {@link EntityMap}.
     */
    public EntityMap(final int x, final int y, final int sceneWidth, final int height) {
        this.x = x;
        this.y = y;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = height;
    }

    /**
     * The lifecycle method {@link #setupEntities()} should be used to add the instances of {@link Entity}
     * that should be used in this {@link EntityMap}.
     */
    public abstract void setupEntities();

    /**
     * The lifecycle method {@link #defineMap()} should be used to define the map that should be used. It is
     * represented by a two dimensional array of type {@code int}, where each cell represents an {@link Entity}
     * on the map. The first array ({@code int[]}) defines the rows. Each entry in this array is itself an array
     * containing the columns of the given row.
     * <p>
     * This way, the following array
     * <pre>
     *      <@code
     *          int[][] map = {
     *              {0, 0, 0, 0, 0, 0, 0 },
     *              {0, 0, 2, 0, 3, 3, 3},
     *              {2, 3, 0, 0, 0, 0, 1}}
     *      >
     * </pre>
     * will place three rows of seven entities. In this case there should be four Entities added through the
     * {@link #addEntity(int, Class<? extends Entity>)} method.
     */
    public abstract int[][] defineMap();

    /**
     * Add the {@link Class} of an {@link Entity} that can be used in this {@link EntityMap}. Each added
     * {@link Entity} {@link Class}must have an identifier for reference from the map. This method should only be called
     * from the lifecycle method {@link EntityMap#setupEntities()}.
     *
     * @param identifier  The identifier as an {@code int} to be used from the map.
     * @param entityClass The {@link Class} of a subclass of {@link Entity} to be
     *                    used for the given identifier.
     */
    public void addEntity(int identifier, Class<? extends Entity> entityClass) {
        entities.put(identifier, entityClass);
    }

    private void calculateEntities() {
        if (sceneWidth == -1 || sceneHeight == -1) {
            sceneWidth = dimensionsProvider.getWidth();
            sceneHeight = dimensionsProvider.getHeight();
        }

        System.out.println("Placing an EntityMap with width and height of: " + sceneWidth + "px and " + sceneHeight + "px");

        for (int i = 0; i < map.length; i++) {
            double entityWidth = sceneWidth / map.length;
            double x = i * entityWidth;
            for (int j = 0; j < map[i].length; j++) {
                int key = map[i][j];
                if (key != 0) {
                    double entityHeight = sceneHeight / map[i].length;
                    double y = j * entityHeight;

                    Class<? extends Entity> entityClass = entities.get(key);
                    Entity entity;
                    try {
                        entity = entityClass.getDeclaredConstructor(Size.class).newInstance(new Size(entityWidth, entityHeight));
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                        throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new YaegerEngineException("Unable to instantiate an Entity for the entitymap");
                    }

                    entity.setOriginX(x);
                    entity.setOriginY(y);

                    add(entity);
                }
            }
        }
    }

    @Override
    public void configure() {
        setupEntities();
        map = defineMap();
        calculateEntities();
    }

    /**
     * A {@link DimensionsProvider} is required because the {@link EntityMap} will need info on the width
     * and height of the {@link nl.meron.yaeger.engine.scenes.YaegerScene} to calculate the placement
     * of the individual instances of {@link Entity}.
     *
     * @param dimensionsProvider The {@link DimensionsProvider} that provides a {@link DimensionsProvider#getWidth()} and
     *                           {@link DimensionsProvider#getHeight()} method; most likely an {@link YaegerScene}.
     */
    void setDimensionsProvider(DimensionsProvider dimensionsProvider) {
        this.dimensionsProvider = dimensionsProvider;
    }
}
