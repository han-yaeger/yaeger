package nl.han.ica.yaeger.engine.debug;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import nl.han.ica.yaeger.engine.repositories.AudioRepository;
import nl.han.ica.yaeger.engine.repositories.ImageRepository;
import nl.han.ica.yaeger.javafx.components.debugger.DebugLabel;
import nl.han.ica.yaeger.javafx.components.debugger.DebugValue;
import nl.han.ica.yaeger.javafx.components.debugger.DebuggerGridPane;
import nl.han.ica.yaeger.engine.entities.EntityCollectionStatistics;

/**
 * The {@code Debugger} is used to gather and show in game debug information.
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
    private static final String KEYLISTENERS = "Keylistening Entities:";
    private static final String AUDIO_FILES = "Audio files";
    private static final String IMAGE_FILES = "Image files";

    private AudioRepository audioRepository;
    private ImageRepository imageRepository;

    private GridPane gridpane;
    private DebugValue dynamicEntities;
    private DebugValue staticEntities;
    private DebugValue entitySpawners;
    private DebugValue keyListeningEntities;
    private DebugValue garbageEntities;
    private DebugValue usedMemory;
    private DebugValue allocatedMemory;

    private DebugValue audioFiles;
    private DebugValue imageFiles;

    public Debugger(Group group) {
        createGridPane(group);
        audioRepository = AudioRepository.getInstance();
        imageRepository = ImageRepository.getInstance();
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
     *                                   {@link nl.han.ica.yaeger.engine.entities.EntityCollection}.
     */
    @Override
    public void update(EntityCollectionStatistics entityCollectionStatistics) {
        if (!gridpane.isVisible()) {
            return;
        }

        dynamicEntities.setText(String.valueOf(entityCollectionStatistics.getUpdatables()));
        staticEntities.setText(String.valueOf(entityCollectionStatistics.getStatics()));
        keyListeningEntities.setText(String.valueOf(entityCollectionStatistics.getKeyListeners()));
        entitySpawners.setText(String.valueOf(entityCollectionStatistics.getSpawners()));
        garbageEntities.setText(String.valueOf(entityCollectionStatistics.getGarbage()));

        allocatedMemory.setText(getTotalMemory());
        usedMemory.setText(getUsedMemory());

        audioFiles.setText(String.valueOf(audioRepository.size()));
        imageFiles.setText(String.valueOf(imageRepository.size()));
    }

    private void createGridPane(Group group) {
        gridpane = new DebuggerGridPane();

        addHeader();
        addSystemStatistics();
        addEntityStatistics();
        addResourcesStatistics();

        group.getChildren().add(gridpane);
    }

    private void addHeader() {
        gridpane.add(new DebugLabel(YAEGER_DEBUGGER_TITLE), 0, 0, 2, 1);
    }

    private void addSystemStatistics() {
        addDebugLine(PROCESSORS_AMOUNT, String.valueOf(Runtime.getRuntime().availableProcessors()));

        allocatedMemory = addDebugLine(MEMORY_ALLOCATED, getTotalMemory());
        usedMemory = addDebugLine(MEMORY_USED, getUsedMemory());
    }

    private void addEntityStatistics() {
        dynamicEntities = addDebugLine(ENTITIES_DYNAMIC);
        staticEntities = addDebugLine(ENTITIES_STATIC);
        entitySpawners = addDebugLine(SPAWNERS);
        garbageEntities = addDebugLine(GARBAGE);
        keyListeningEntities = addDebugLine(KEYLISTENERS);
    }

    private void addResourcesStatistics() {
        audioFiles = addDebugLine(AUDIO_FILES);
        imageFiles = addDebugLine(IMAGE_FILES);
    }

    private DebugValue addDebugLine(String label) {
        return addDebugLine(label, "");
    }

    private DebugValue addDebugLine(String label, String value) {
        DebugValue debugValue = new DebugValue(value);

        int nextrow = gridpane.getRowCount() + 1;
        gridpane.add(new DebugLabel(label), 0, nextrow);
        gridpane.add(debugValue, 1, nextrow);

        return debugValue;
    }

    private String getTotalMemory() {
        return String.valueOf(Runtime.getRuntime().totalMemory());
    }

    private String getUsedMemory() {
        return String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
}