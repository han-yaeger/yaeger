package nl.han.ica.yaeger.engine.scenes.impl;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.EntityCollection;
import nl.han.ica.yaeger.engine.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.engine.exceptions.YaegerLifecycleException;

import java.util.HashSet;
import java.util.Set;

/**
 * Instantiate a new  {@code DynamicScene}. A {@code DynamicScene} extends a {@link StaticScene}, but adds its own {@code Gameloop}.
 */
public abstract class DynamicScene extends StaticScene {

    private Set<Entity> initialEntities = new HashSet<>();
    private AnimationTimer animator;

    private boolean gameLoopIsRunning = false;

    @Override
    public void setupScene() {
        super.setupScene();

        setupInitialEntities();

        createGameLoop();

        setupSpawners();

        startGameLoop();

        debugger.toFront();
    }


    protected abstract void setupSpawners();

    /**
     * Voeg {@link Entity}s toe, die initiëel al deel uitmaken van een {@link nl.han.ica.yaeger.engine.scenes.YaegerScene}. Hierbij kun je
     * denken aan elementen van een {@code Dashboard} of een Speler-{@link Entity}. Elementen van het scherm die tijdens het game
     * moeten worden toegevoegd, moeten gebruik maken van een {@link EntitySpawner}.
     */
    protected abstract void setupInitialEntities();

    /**
     * Voeg een {@link Entity} toe aan de {@code scenes}. Iedere {@link Entity} kunnen maar één keer worden toegevoegd.
     * Deze methode kan enkel gebruikt worden voor een {@link Entity} die bij initialisatie aan het game moeten worden
     * toegevoegd. Indien er tijdens het game een extra {@link Entity} moet worden toegevoegd, gebruik dan een
     * {@link EntitySpawner}.
     *
     * @param entity Het {@link Entity} dat moet worden toegevoegd.
     */
    @Override
    protected void addEntity(Entity entity) {
        if (gameLoopIsRunning) {
            throw new YaegerLifecycleException("The GameLoop is running. It is no longer allowed to add an Entity. " +
                    "Please register an EntitySpawner to add Entities.");
        }
        initialEntities.add(entity);
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        getEntityCollection().notifyGameObjectsOfPressedKeys(input);
    }

    /**
     * Register an {@link EntitySpawner}. After being registered, the {@link EntitySpawner} will be responsible for spawning
     * new instances of {@link Entity}.
     *
     * @param spawner the {@link EntitySpawner} to be registered
     */
    protected void registerSpawner(EntitySpawner spawner) {
        getEntityCollection().registerSpawner(spawner);
    }

    @Override
    public void destroy() {
        stopGameLoop();
        clearInitialEntities();
        clearEntityCollection();

        super.destroy();
    }

    private void clearInitialEntities() {
        initialEntities.clear();
    }

    private void clearEntityCollection() {
        getEntityCollection().clear();
        setEntityCollection(null);
    }

    private void startGameLoop() {
        gameLoopIsRunning = true;
        animator.start();
    }

    private void stopGameLoop() {
        animator.stop();
        animator = null;
        gameLoopIsRunning = false;
    }

    private void createGameLoop() {
        setEntityCollection(new EntityCollection(getRoot(), initialEntities));
        getEntityCollection().addStatisticsObserver(debugger);

        animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {

                getEntityCollection().update(arg0);
            }
        };
    }
}
