package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.YaegerEntity;

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
