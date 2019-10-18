package nl.meron.yaeger.engine.entities.entity;

import nl.meron.yaeger.engine.entities.entity.updatetasks.EntityUpdateTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement this interface to be updated every cycle of the game loop.
 */
@FunctionalInterface
public interface Updatable {

    List<EntityUpdateTask> updateTasks = new ArrayList<>();

    /**
     * The update() method is called each frame.
     *
     * <p>
     * Use this method to init frame-based behaviour to the game-object.
     * </p>
     *
     * @param timestamp the timestamp of the update
     */
    void update(long timestamp);

    /**
     * Add an {@link EntityUpdateTask} to this {@link Entity} to be executed on a {@code update()}
     *
     * @param task the {@link EntityUpdateTask} to be executed
     */
    default void addTask(EntityUpdateTask task){
        updateTasks.add(task);
    }

    default void executeTasks(Entity entity){

        updateTasks.forEach(task -> task.executeTask(entity));
    }
}
