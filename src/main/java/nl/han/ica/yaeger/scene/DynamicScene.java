package nl.han.ica.yaeger.scene;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.entities.Entity;
import nl.han.ica.yaeger.entities.EntityCollection;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.exceptions.YaegerLifecycleException;

import java.util.HashSet;
import java.util.Set;

/**
 * Een {@code DynamicScene} is een {@link StaticScene} met een eigen {@code GameLoop}. Deze {@code GameLoop}
 * roept ieder frame (waarbij wordt uitgegaan van 60fps) op alle geregistreerde {@link nl.han.ica.yaeger.entities.Entity}s
 * die de interface {@link nl.han.ica.yaeger.entities.interfaces.Updatable} implementeren, de {@code update()} methode aan.
 */
public abstract class DynamicScene extends StaticScene {

    protected Set<Entity> initialEntities = new HashSet<>();
    private EntityCollection entityCollection;
    private AnimationTimer animator;

    private boolean gameLoopIsRunning = false;

    @Override
    public void setupScene() {
        super.setupScene();
        setupDynamicEntities();

        createGameLoop();
        
        setupSpawners();
        startGameLoop();
    }

    protected abstract void setupSpawners();

    public abstract void setupDynamicEntities();

    @Override
    public void tearDownScene() {
        super.tearDownScene();

        stopGameLoop();
    }

    /**
     * Start de {@code Gameloop}.
     */
    public void startGameLoop() {
        gameLoopIsRunning = true;
        animator.start();
    }

    /**
     * Stop de {@code Gameloop}.
     */
    public void stopGameLoop() {
        animator.stop();
        gameLoopIsRunning = false;
    }


    /**
     * Voeg een {@link Entity} toe aan de {@code Scene}. Iedere {@link Entity} kunnen maar één keer worden toegevoegd.
     * Deze methode kan enkel gebruikt worden voor een {@link Entity} die bij initialisatie aan het spel moeten worden
     * toegevoegd. Indien er tijdens het spel een extra {@link Entity} moet worden toegevoegd, gebruik dan een
     * {@link EntitySpawner}.
     *
     * @param entity Het {@link Entity} dat moet worden toegevoegd.
     */
    protected void addEntity(Entity entity) {
        if (gameLoopIsRunning) {
            throw new YaegerLifecycleException("De GameLoop is al bezig. Het is niet langer toegestaan een Entity toe " +
                    "te voegen. Gebruik hiervoor een EntitySpawner");
        }
        initialEntities.add(entity);
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        entityCollection.notifyGameObjectsOfPressedKeys(input);
    }

    /**
     * Registreer een {@link EntitySpawner}. Na registratie zal de {@link EntitySpawner} verantwoordelijk worden voor
     * het aanmaken (spawnen) van nieuwe {@link Entity}s.
     *
     * @param spawner De {@link EntitySpawner} die geregistreerd moet worden.
     */
    protected void registerSpawner(EntitySpawner spawner) {
        entityCollection.registerSpawner(spawner);
    }

    private void createGameLoop() {
        entityCollection = new EntityCollection(getRoot());
        entityCollection.init(initialEntities);

        animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {

                entityCollection.update();
            }
        };
    }
}
