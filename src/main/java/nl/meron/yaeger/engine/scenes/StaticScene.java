package nl.meron.yaeger.engine.scenes;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.yaeger.engine.RequiresInjection;
import nl.meron.yaeger.engine.DependencyInjector;
import nl.meron.yaeger.engine.YaegerApplication;
import nl.meron.yaeger.engine.entities.EntityCollection;
import nl.meron.yaeger.engine.entities.EntitySupplier;
import nl.meron.yaeger.engine.entities.entity.YaegerEntity;
import nl.meron.yaeger.engine.entities.entity.events.userinput.KeyListener;
import nl.meron.yaeger.engine.debug.Debugger;
import nl.meron.yaeger.engine.entities.tilemap.TileMap;
import nl.meron.yaeger.engine.entities.tilemap.TileMapListProvider;
import nl.meron.yaeger.engine.scenes.delegates.BackgroundDelegate;
import nl.meron.yaeger.engine.scenes.delegates.KeyListenerDelegate;
import nl.meron.yaeger.guice.factories.EntityCollectionFactory;
import nl.meron.yaeger.guice.factories.SceneFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A {@link StaticScene} is the abstract superclass of all scenes that do not require a Game Loop. If a Game
 * Loop is required, extend a {@link DynamicScene}.
 */
public abstract class StaticScene implements YaegerScene, KeyListener, SupplierProvider, TileMapListProvider, EntityCollectionSupplier, DependencyInjector {

    private EntityCollectionFactory entityCollectionFactory;
    private SceneFactory sceneFactory;

    protected Injector injector;

    protected EntityCollection entityCollection;

    private EntitySupplier entitySupplier;
    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    private List<TileMap> tileMaps = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Group root;
    Debugger debugger;

    @Override
    public void init(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public void activate() {
        scene = sceneFactory.create(root);
        entityCollection = entityCollectionFactory.create(root);
        injector.injectMembers(entityCollection);
        entityCollection.init(injector);
        entityCollection.addStatisticsObserver(debugger);

        debugger.setup(root);
        keyListenerDelegate.setup(scene, this);
        backgroundDelegate.setup(scene);

        setupScene();
        setupEntities();
    }

    @Override
    public void postActivate() {
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.initialUpdate();
        debugger.toFront();
    }

    /**
     * Add an {@link YaegerEntity} to this {@link YaegerScene}. An {@link YaegerEntity} can only be added once.
     * <p>
     * This method can only be used to add an instance of {@link YaegerEntity} during initialisation.If
     * one should be added during the game, a {@link nl.meron.yaeger.engine.entities.EntitySpawner} should be used.
     * </p>
     *
     * @param entity the {@link YaegerEntity} to be added
     */
    protected void addEntity(final YaegerEntity entity) {
        entitySupplier.add(entity);
    }

    /**
     * Implement this method to be informed when a key has been pressed or released.
     *
     * @param input A {@link Set} containing all keys currently pressed.
     */
    protected abstract void onInputChanged(final Set<KeyCode> input);

    @Override
    public EntityCollection getEntityCollection() {
        return entityCollection;
    }

    @Override
    public List<TileMap> getTileMaps() {
        return tileMaps;
    }

    @Override
    public EntitySupplier getEntitySupplier() {
        return entitySupplier;
    }

    @Override
    public Injector getInjector() {
        return this.injector;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setBackgroundColor(Color color) {
        backgroundDelegate.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundImage(final String url) {
        backgroundDelegate.setBackgroundImage(url);
    }

    @Override
    public void setBackgroundAudio(final String url) {
        backgroundDelegate.setBackgroundAudio(url);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void destroy() {
        keyListenerDelegate.tearDown(scene);
        backgroundDelegate.destroy();
        clear();
    }

    @Override
    public void onPressedKeysChange(final Set<KeyCode> input) {
        if (input.contains(YaegerApplication.TOGGLE_DEBUGGER_KEY)) {
            debugger.toggle();
        }

        onInputChanged(input);
    }


    @Override
    public void clear() {
        root.getChildren().clear();
        root = null;
        scene = null;
    }

    /**
     * Set the {@link Group} to be used. The {@link Group} will be the root node of the graph that
     * will be constructed for this {@link Scene}.
     *
     * @param root the {@link Group} to be used
     */
    @Inject
    public void setRoot(final Group root) {
        this.root = root;
    }

    /**
     * Set the {@link KeyListener} that should be used. In general, this will be the {@link YaegerScene}
     * itself.
     *
     * @param keyListenerDelegate the {@link KeyListener} to be used
     */
    @Inject
    public void setKeyListenerDelegate(final KeyListenerDelegate keyListenerDelegate) {
        this.keyListenerDelegate = keyListenerDelegate;
    }

    /**
     * Set the {@link SceneFactory} that should be used to create a {@link Scene}.
     *
     * @param sceneFactory the {@link SceneFactory} to be used
     */
    @Inject
    public void setSceneFactory(final SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
    }

    /**
     * Set the {@link Debugger} that should be used.
     *
     * @param debugger the {@link Debugger} to be used
     */
    @Inject
    public void setDebugger(final Debugger debugger) {
        this.debugger = debugger;
    }

    @Inject
    public void setBackgroundDelegate(final BackgroundDelegate backgroundDelegate) {
        this.backgroundDelegate = backgroundDelegate;
    }

    @Inject
    public void setEntityCollectionFactory(final EntityCollectionFactory entityCollectionFactory) {
        this.entityCollectionFactory = entityCollectionFactory;
    }

    @Inject
    public void setEntitySupplier(final EntitySupplier entitySupplier) {
        this.entitySupplier = entitySupplier;
    }
}
