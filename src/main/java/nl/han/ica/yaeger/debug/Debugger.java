package nl.han.ica.yaeger.debug;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import nl.han.ica.yaeger.StatisticsObserver;
import nl.han.ica.yaeger.entities.EntityCollectionStatistics;

/**
 * The Debugger is used to gather and show in game debug information.
 */
public class Debugger implements StatisticsObserver {

    private static final String YAEGER_DEBUGGER_TITLE = "YAEGER DEBUGGER";
    private static final String PROCESSORS_AMOUNT = "Available processors:";
    private static final String MEMORY_ALLOCATED = "Total allocated memory:";
    private static final String MEMORY_USED = "Total used memory:";
    private static final String ENTITIES_DYNAMIC = "Dynamic Entities:";
    private static final String ENTITIES_STATIC = "Static Entities:";
    private static final String SPAWNERS = "Spawners:";
    private static final String GARBAGE = "Garbage:";
    private static final String KEYLISTENERS = "Dynamic Entities:";

    private GridPane gridpane;
    private DebugValue dynamics;
    private DebugValue statics;
    private DebugValue spawners;
    private DebugValue keyListeners;
    private DebugValue garbage;

    private DebugValue usedMemory;
    private DebugValue allocatedMemory;

    public Debugger(Group group) {
        createGridPane(group);
    }

    /**
     * Ensure that the {@link Debugger} is brought to the top of the viewstack.
     */
    public void toFront() {
        gridpane.toFront();
    }

    /**
     * Toggle the visibillity of the {@link Debugger}.
     */
    public void toggle() {
        gridpane.setVisible(!gridpane.isVisible());
    }

    /**
     * Update the content of the {@link Debugger}.
     *
     * @param entityCollectionStatistics An {@link EntityCollectionStatistics} that contains all
     *                                   statistics related to the current state of the
     *                                   {@link nl.han.ica.yaeger.entities.EntityCollection}.
     */
    @Override
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
        gridpane.add(new DebugLabel(YAEGER_DEBUGGER_TITLE), 0, 0, 2, 1);
    }

    private void addSystemStatistics() {
        gridpane.add(new DebugLabel(PROCESSORS_AMOUNT), 0, 1);
        gridpane.add(new DebugValue(String.valueOf(Runtime.getRuntime().availableProcessors())), 1, 1);

        gridpane.add(new DebugLabel(MEMORY_ALLOCATED), 0, 2);
        allocatedMemory = new DebugValue();
        gridpane.add(allocatedMemory, 1, 2);


        gridpane.add(new DebugLabel(MEMORY_USED), 0, 3);
        usedMemory = new DebugValue();
        gridpane.add(usedMemory, 1, 3);
    }

    private void addDynamics() {
        dynamics = new DebugValue();

        gridpane.add(new DebugLabel(ENTITIES_DYNAMIC), 0, 4);
        gridpane.add(dynamics, 1, 4);
    }

    private void addStatics() {
        statics = new DebugValue();

        gridpane.add(new DebugLabel(ENTITIES_STATIC), 0, 5);
        gridpane.add(statics, 1, 5);
    }

    private void addSpawners() {
        spawners = new DebugValue();

        gridpane.add(new DebugLabel(SPAWNERS), 0, 6);
        gridpane.add(spawners, 1, 6);
    }

    private void addGarbage() {
        garbage = new DebugValue();

        gridpane.add(new DebugLabel(GARBAGE), 0, 7);
        gridpane.add(garbage, 1, 7);
    }

    private void addKeyListeners() {
        keyListeners = new DebugValue();

        gridpane.add(new DebugLabel(KEYLISTENERS), 0, 8);
        gridpane.add(keyListeners, 1, 8);
    }
}