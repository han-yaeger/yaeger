package nl.han.ica.yaeger.debug;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import nl.han.ica.yaeger.entities.EntityCollectionStatistics;
import nl.han.ica.yaeger.scene.SceneStatistics;

public class Debugger extends Node {

    GridPane gridpane;
    private DebugValue dynamics;
    private DebugValue statics;
    private DebugValue spawners;
    private DebugValue keyListeners;
    private DebugValue garbage;

    private DebugValue usedMemory;
    private DebugValue allocatedMemory;

    public Debugger(Group group, SceneStatistics sceneStatistics) {
        createGridPane(group);
    }

    private void createGridPane(Group group) {
        gridpane = new DebuggerGridPane();

        addHeader();
        addSystemStatistics();

        addDynamics();
        addStatics();
        addSpawners();
        addKeyListeners();
        addGarbage();

        group.getChildren().add(gridpane);
    }

    private void addHeader() {
        gridpane.add(new DebugLabel("YAEGER DEBUGGER"), 0, 0, 2, 1);
    }

    private void addSystemStatistics() {
        gridpane.add(new DebugLabel("Available processors:"), 0, 1);
        gridpane.add(new DebugValue(String.valueOf(Runtime.getRuntime().availableProcessors())), 1, 1);

        gridpane.add(new DebugLabel("Total allocated memory:"), 0, 2);
        allocatedMemory = new DebugValue();
        gridpane.add(allocatedMemory, 1, 2);


        gridpane.add(new DebugLabel("Total used memory:"), 0, 3);
        usedMemory = new DebugValue();
        gridpane.add(usedMemory, 1, 3);

        ;
    }

    private void addDynamics() {
        dynamics = new DebugValue();

        gridpane.add(new DebugLabel("Dynamic Entities:"), 0, 4);
        gridpane.add(dynamics, 1, 4);
    }

    private void addStatics() {
        statics = new DebugValue();

        gridpane.add(new DebugLabel("Static Entities:"), 0, 5);
        gridpane.add(statics, 1, 5);
    }

    private void addSpawners() {
        spawners = new DebugValue();

        gridpane.add(new DebugLabel("Spawners:"), 0, 6);
        gridpane.add(spawners, 1, 6);
    }

    private void addGarbage() {
        garbage = new DebugValue();

        gridpane.add(new DebugLabel("Garbage:"), 0, 7);
        gridpane.add(garbage, 1, 7);
    }

    private void addKeyListeners() {
        keyListeners = new DebugValue();

        gridpane.add(new DebugLabel("Dynamic Entities:"), 0, 8);
        gridpane.add(keyListeners, 1, 8);
    }

    public void toFront() {
        gridpane.toFront();
    }

    public void toggle() {
        gridpane.setVisible(!gridpane.isVisible());
    }

    public void update(EntityCollectionStatistics entityCollectionStatistics) {
        if (!gridpane.isVisible()) {
            return;
        }

        dynamics.setText(String.valueOf(entityCollectionStatistics.getUpdatables()));
        statics.setText(String.valueOf(entityCollectionStatistics.getStatics()));
        keyListeners.setText(String.valueOf(entityCollectionStatistics.getKeyListeners()));
        spawners.setText(String.valueOf(entityCollectionStatistics.getSpawners()));
        garbage.setText(String.valueOf(entityCollectionStatistics.getGarbage()));

        allocatedMemory.setText(String.valueOf(Runtime.getRuntime().totalMemory()));
        usedMemory.setText(String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

    }
}