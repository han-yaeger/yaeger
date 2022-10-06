package com.github.hanyaeger.core.entities;

import com.github.hanyaeger.api.entities.FiniteAnimation;

import java.util.List;

/**
 * Implementing the {@link NamedAnimationListProvider} interface guarantees that a {@link List} of
 * instances of {@link FiniteAnimation} is available.
 */
public interface NamedAnimationListProvider {

    /**
     * Return the {@link List} of {@link FiniteAnimation} instances.
     *
     * @return the {@link List} of {@link FiniteAnimation} instances
     */
    List<FiniteAnimation> getNamedAnimations();
}
