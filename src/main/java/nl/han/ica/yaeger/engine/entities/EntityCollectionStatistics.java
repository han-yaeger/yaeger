package nl.han.ica.yaeger.engine.entities;

/**
 * An {@code EntityCollectionStatistics} is a DTO (Data Transfer Object) that contains
 * all current values from an {@link EntityCollection}.
 */
public class EntityCollectionStatistics {

    private int spawners;
    private int updatables;
    private int keyListeners;
    private int garbage;
    private int statics;

    public void setSuppliers(int spawners) {
        this.spawners = spawners;
    }

    public void setStatics(int statics) {
        this.statics = statics;
    }

    public void setUpdatables(int updatables) {
        this.updatables = updatables;
    }

    public void setKeyListeners(int keyListeners) {
        this.keyListeners = keyListeners;
    }

    public void setGarbage(int garbage) {
        this.garbage = garbage;
    }

    public int getSuppliers() {
        return spawners;
    }

    public int getStatics() {
        return statics;
    }

    public int getUpdatables() {
        return updatables;
    }

    public int getKeyListeners() {
        return keyListeners;
    }

    public int getGarbage() {
        return garbage;
    }
}
