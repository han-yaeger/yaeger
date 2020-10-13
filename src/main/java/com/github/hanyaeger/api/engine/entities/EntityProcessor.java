package com.github.hanyaeger.api.engine.entities;

import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;

/**
 * When a {@link YaegerEntity} needs to be processed, in general meaning it should be passed as a parameter
 * to a specific method, this functional interface can be used as the basis for a Lambda expression.
 */
@FunctionalInterface
public interface EntityProcessor {

    /**
     * Process the given {@link YaegerEntity}.
     *
     * @param yaegerEntity The {@link YaegerEntity} that should be processed.
     */
    void process(final YaegerEntity yaegerEntity);
}
