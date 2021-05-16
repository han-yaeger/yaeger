package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.Bounded;

/**
 * A {@link Collider} represents an {@link YaegerEntity} that can be collided with
 * by a {@link Collided}
 * <p>
 * In case of a collision, only the {@link Collided} will be notified.
 */
public interface Collider extends Bounded {
}
