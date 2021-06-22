package com.github.hanyaeger.core.entities;

/**
 * An {@code EntityCollectionStatistics} is a DTO (Data Transfer Object) that contains
 * all current values from an {@link EntityCollection}.
 */
class EntityCollectionStatistics {

    private int spawners;
    private int updatables;
    private int keyListeners;
    private int garbage;
    private int statics;

    void setSuppliers(final int spawners) {
        this.spawners = spawners;
    }

    void setStatics(final int statics) {
        this.statics = statics;
    }

    void setUpdatables(final int updatables) {
        this.updatables = updatables;
    }

    void setKeyListeners(final int keyListeners) {
        this.keyListeners = keyListeners;
    }

    void setGarbage(final int garbage) {
        this.garbage = garbage;
    }

    int getSuppliers() {
        return spawners;
    }

    int getStatics() {
        return statics;
    }

    int getUpdatables() {
        return updatables;
    }

    int getKeyListeners() {
        return keyListeners;
    }

    int getGarbage() {
        return garbage;
    }
}
