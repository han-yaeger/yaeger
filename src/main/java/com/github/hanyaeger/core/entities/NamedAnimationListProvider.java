package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.Animation;

import java.util.List;

/**
 * Implementing the {@link NamedAnimationListProvider} interface guarantees that a {@link List} of
 * instances of {@link Animation} is available.
 */
public interface NamedAnimationListProvider {

    /**
     * Return the {@link List} of {@link Animation} instances.
     *
     * @return the {@link List} of {@link Animation} instances
     */
    List<Animation> getNamedAnimations();
}
