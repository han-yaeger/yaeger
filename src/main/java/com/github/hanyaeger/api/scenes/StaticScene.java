package com.github.hanyaeger.api.scenes;

import com.github.hanyaeger.core.DependencyInjector;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.entities.Debugger;
import com.github.hanyaeger.core.repositories.DragNDropRepository;
import com.github.hanyaeger.core.scenes.EntityCollectionSupplier;
import com.github.hanyaeger.core.scenes.SupplierProvider;
import com.github.hanyaeger.core.scenes.TileMapListProvider;
import com.github.hanyaeger.core.factories.EntityCollectionFactory;
import com.github.hanyaeger.core.factories.SceneFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.github.hanyaeger.api.entities.EntitySpawner;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.github.hanyaeger.core.entities.EntitySupplier;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.userinput.KeyListener;
import com.github.hanyaeger.core.scenes.delegates.BackgroundDelegate;
import com.github.hanyaeger.core.scenes.delegates.KeyListenerDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link StaticScene} is the abstract superclass of all scenes that do not require a Game Loop. If a Game
 * Loop is required, extend a {@link DynamicScene}.
 */
public abstract class StaticScene implements YaegerScene, SupplierProvider, TileMapListProvider, EntityCollectionSupplier, DependencyInjector {

    private EntityCollectionFactory entityCollectionFactory;
    private SceneFactory sceneFactory;

    Injector injector;
    EntityCollection entityCollection;

    private EntitySupplier entitySupplier;
    private KeyListenerDelegate keyListenerDelegate;
    private BackgroundDelegate backgroundDelegate;

    private final List<TileMap> tileMaps = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Pane pane;
    private ColorAdjust colorAdjust;
    private YaegerConfig config;
    private Debugger debugger;

    private DragNDropRepository dragNDropRepository;

    private boolean activationComplete;

    @Override
    public void init(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public void activate() {
        pane.setEffect(colorAdjust);

        scene = sceneFactory.create(pane);

        entityCollection = entityCollectionFactory.create(pane, config);
        injector.injectMembers(entityCollection);
        entityCollection.init(injector);


        if (config.showDebug()) {
            entityCollection.addStatisticsObserver(debugger);
            debugger.setup(pane);
        }

        keyListenerDelegate.setup(scene, this::onInputChanged);

        if (this instanceof KeyListener keyListener) {
            entityCollection.registerKeyListener(keyListener);
        }
        backgroundDelegate.setup(pane);

        setupScene();
        setupEntities();
    }

    @Override
    public void postActivate() {
        entityCollection.registerSupplier(entitySupplier);
        entityCollection.initialUpdate();
        // Fixes #208
        stage.hide();
        stage.show();
        //
        if (config.showDebug()) {
            debugger.postActivation();
        }
        activationComplete = true;
    }

    /**
     * Add an {@link YaegerEntity} to this {@link YaegerScene}.
     * <p>
     * This method can only be used to add an instance of {@link YaegerEntity} during initialisation.If
     * one should be added during the game, a {@link EntitySpawner} should be used.
     * </p>
     *
     * @param yaegerEntity the {@link YaegerEntity} to be added
     */
    protected void addEntity(final YaegerEntity yaegerEntity) {
        entitySupplier.add(yaegerEntity);
    }

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
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setConfig(final YaegerConfig yaegerConfig) {
        this.config = yaegerConfig;
    }

    @Override
    public void setBackgroundColor(final Color color) {
        backgroundDelegate.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundImage(final String url) {
        setBackgroundImage(url, true);
    }

    @Override
    public void setBackgroundImage(final String url, final boolean fullscreen) {
        backgroundDelegate.setBackgroundImage(url, fullscreen);
    }

    @Override
    public void setBackgroundAudio(final String url) {
        backgroundDelegate.setBackgroundAudio(url);
    }

    @Override
    public void setBackgroundAudioVolume(double volume) {
        backgroundDelegate.setVolume(volume);
    }

    @Override
    public double getBackgroundAudioVolume() {
        return backgroundDelegate.getVolume();
    }

    @Override
    public void stopBackgroundAudio() {
        backgroundDelegate.stopBackgroundAudio();
    }

    @Override
    public void setBrightness(final double brightness) {
        colorAdjust.setBrightness(brightness);
    }

    @Override
    public void setContrast(final double contrast) {
        colorAdjust.setContrast(contrast);
    }

    @Override
    public void setHue(final double hue) {
        colorAdjust.setHue(hue);
    }

    @Override
    public void setSaturation(final double saturation) {
        colorAdjust.setSaturation(saturation);
    }

    @Override
    public double getBrightness() {
        return colorAdjust.getBrightness();
    }

    @Override
    public double getContrast() {
        return colorAdjust.getContrast();
    }

    @Override
    public double getHue() {
        return colorAdjust.getHue();
    }

    @Override
    public double getSaturation() {
        return colorAdjust.getSaturation();
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
    public void clear() {
        pane.getChildren().clear();
        pane = null;
        scene = null;
    }

    @Override
    public DragNDropRepository getDragNDropRepository() {
        return dragNDropRepository;
    }

    @Override
    public Optional<? extends Node> getNode() {
        return Optional.of(getScene().getRoot());
    }

    @Override
    public boolean isActivationComplete() {
        return activationComplete;
    }

    /**
     * Set the {@link Pane} to be used. The {@link Pane} will be the root node of the graph that
     * will be constructed for this {@link Scene}.
     *
     * @param pane the {@link Pane} to be used
     */
    @Inject
    public void setPane(final Pane pane) {
        this.pane = pane;
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

    /**
     * Set the {@link BackgroundDelegate} that should be used.
     *
     * @param backgroundDelegate the {@link BackgroundDelegate} to be used
     */
    @Inject
    public void setBackgroundDelegate(final BackgroundDelegate backgroundDelegate) {
        this.backgroundDelegate = backgroundDelegate;
    }

    /**
     * Set the {@link EntityCollectionFactory} that should be used.
     *
     * @param entityCollectionFactory the {@link EntityCollectionFactory} to be used
     */
    @Inject
    public void setEntityCollectionFactory(final EntityCollectionFactory entityCollectionFactory) {
        this.entityCollectionFactory = entityCollectionFactory;
    }

    /**
     * Set the {@link EntitySupplier} that should be used.
     *
     * @param entitySupplier the {@link EntitySupplier} to be used
     */
    @Inject
    public void setEntitySupplier(final EntitySupplier entitySupplier) {
        this.entitySupplier = entitySupplier;
    }

    /**
     * Set the {@link ColorAdjust} that should be used.
     *
     * @param colorAdjust the {@link ColorAdjust} to be used
     */
    @Inject
    public void setColorAdjust(final ColorAdjust colorAdjust) {
        this.colorAdjust = colorAdjust;
    }

    /**
     * Set the {@link DragNDropRepository} that should be used.
     *
     * @param dragNDropRepository the {@link DragNDropRepository} to be used
     */
    @Inject
    public void setDragNDropRepository(DragNDropRepository dragNDropRepository) {
        this.dragNDropRepository = dragNDropRepository;
    }

    private void onInputChanged(final Set<KeyCode> input) {
        entityCollection.notifyGameObjectsOfPressedKeys(input);
    }


}
