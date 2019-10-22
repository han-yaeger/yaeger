package nl.meron.yaeger.engine.entities.entity.updatetasks;

import nl.meron.yaeger.engine.entities.entity.Entity;

@FunctionalInterface
public interface EntityUpdateTask {

    void executeTask();
}
