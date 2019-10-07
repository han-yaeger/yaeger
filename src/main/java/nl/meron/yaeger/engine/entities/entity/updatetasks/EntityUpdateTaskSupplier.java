package nl.meron.yaeger.engine.entities.entity.updatetasks;

@FunctionalInterface
public interface EntityUpdateTaskSupplier {

    EntityUpdateTask getTask();
}
