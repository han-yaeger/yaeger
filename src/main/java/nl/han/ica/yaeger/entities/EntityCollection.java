package nl.han.ica.yaeger.entities;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.delegates.CollisionDelegate;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.entities.events.EventTypes;
import nl.han.ica.yaeger.entities.interfaces.KeyListener;
import nl.han.ica.yaeger.entities.interfaces.Updatable;

import java.util.HashSet;
import java.util.Set;

/**
 * Een {@code EntityCollection} encapsuleert al het gedrag dat gerelateerd is aan de verzameling van alle
 * {@link Entity} die deel uit maken van een {@link nl.han.ica.yaeger.scene.YaegerScene}.
 */
public class EntityCollection {

    private Group group;
    private Set<EntitySpawner> spawners = new HashSet<>();
    private Set<Entity> statics = new HashSet<>();
    private Set<Updatable> updatables = new HashSet<>();
    private Set<KeyListener> keyListeners = new HashSet<>();
    private Set<Entity> garbage = new HashSet<>();

    private CollisionDelegate collisionDelegate;

    /**
     * Instantieer een {@code EntityCollection} voor een gegeven {@link Group} en een {@link Set} van {@link Entity}s.
     *
     * @param group           De {@link Group} waar alle {@link Entity}s aan moeten worden toegevoegd.
     * @param initialEntities Een {@link Set} met {@link Entity} die initieel aan deze {@code EntityCollection} moet worden
     *                        toegevoegd.
     */
    public EntityCollection(Group group, Set<Entity> initialEntities) {
        this.group = group;
        this.collisionDelegate = new CollisionDelegate();

        if (initialEntities != null && !initialEntities.isEmpty()) {
            initialEntities.forEach(this::addToGameLoop);
        }
    }

    /**
     * registreer een {@link EntitySpawner}.
     *
     * @param spawner De {@link EntitySpawner} die geregistreerd moet worden.
     */
    public void registerSpawner(EntitySpawner spawner) {
        this.spawners.add(spawner);
    }

    /**
     * Mark a Entity as garbage. After this is done, the Entity is set for Garbage Collection and will
     * be collected in the next Garbage Collection cycle.
     *
     * @param entity The Entity to be removed.
     */
    private void markAsGarbage(Entity entity) {

        this.garbage.add(entity);
    }

    /**
     * Notify all {@code Entity} that implement the interface {@code KeyListener} that keys are being pressed.
     *
     * @param input A {@code Set<KeyCode>} containing als keys currently pressed.
     */
    public void notifyGameObjectsOfPressedKeys(Set<KeyCode> input) {
        keyListeners.forEach(gameObject -> gameObject.onPressedKeysChange(input));
    }

    /**
     * Perform all operations required during one cycle of the GameLoop, being:
     *
     * <ul>
     * <li><b>Collect garbage</b> All EntityCollection that have been marked as Garbage will be removed.
     * <li><b>Notify EntityCollection</b> On all EntityCollection that implement the interface Updatable, update()
     * will be called.</li>
     * <li><b>Collect spawned objects</b> All EntityCollection created by the ObjectsSpawners will be collected
     * and added to the list of EntityCollection.</li>
     * </ul>
     */
    public void update() {
        collectGarbage();
        notifyUpdatables();
        addSpawnedObjects();
        collisionDelegate.checkCollisions();
    }

    /**
     * Leeg deze {@code EntityCollection} van alle {@link Entity}s.
     */
    public void clear() {
        spawners.clear();
        statics.clear();
        updatables.clear();
        garbage.clear();
    }

    /**
     * Retoruneerd het aantal {@link Entity}s, die niet {@link Updatable} zijn,  die zijn geregistreerd in deze {@code EntityCollection}
     *
     * @return Het aantal {@link Entity}s, die niet {@link Updatable} zijn
     */
    public int getNumberOfStaticEntities() {
        return statics.size();
    }

    /**
     * Retoruneerd het aantal {@link Updatable} {@link Entity}s die zijn geregistreerd in deze {@code EntityCollection}.
     *
     * @return Het aantal {@link Updatable} {@link Entity}s,
     */
    public int getNumberOfDynamicEntities() {
        return updatables.size();
    }


    private void collectGarbage() {
        if (garbage.isEmpty()) {
            return;
        }

        garbage.forEach(this::removeGameObject);
        statics.removeAll(garbage);
        updatables.removeAll(garbage);
        garbage.clear();
    }

    private void removeAllGameObjects() {
        this.group.getChildren().clear();
    }

    private void removeGameObject(Entity entity) {
        this.group.getChildren().remove(entity.getGameNode());
        this.collisionDelegate.removeGameObject(entity);
    }

    private void addSpawnedObjects() {
        if (!spawners.isEmpty()) {
            spawners.forEach(spawner -> spawner.getSpawnedEntities().forEach(this::addToGameLoop));
        }
    }

    private void addToGameLoop(Entity entity) {
        addToKeylisteners(entity);
        addToUpdatablesOrStatics(entity);

        collisionDelegate.registerForCollisionDetection(entity);
        attachEventListeners(entity);
        addToScene(entity);
    }

    private void addToUpdatablesOrStatics(Entity entity) {
        if (entity instanceof Updatable) {
            updatables.add((Updatable) entity);
        } else {
            statics.add(entity);
        }
    }

    private void addToKeylisteners(Entity entity) {
        if (entity instanceof KeyListener) {
            keyListeners.add((KeyListener) entity);
        }
    }

    private void addToScene(Entity entity) {
        this.group.getChildren().add(entity.getGameNode());
    }

    private void attachEventListeners(Entity entity) {
        entity.getGameNode().addEventHandler(EventTypes.REMOVE, event -> markAsGarbage(event.getSource()));
    }

    private void notifyUpdatables() {
        updatables.forEach(Updatable::update);
    }
}