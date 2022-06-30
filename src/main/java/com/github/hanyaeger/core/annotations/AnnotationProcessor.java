package com.github.hanyaeger.core.annotations;

import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.core.UpdateDelegator;
import com.github.hanyaeger.core.exceptions.YaegerEngineException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@link AnnotationProcessor} is responsible for processing Yaeger specific annotations. Currently,
 * only the following annotations are supported and will be processed:
 *
 * <ul>
 *     <li>{@link OnActivation}</li>
 *     <li>{@link OnPostActivation}</li>
 *     <li>{@link UpdatableProvider}</li>
 * </ul>
 */
public class AnnotationProcessor {

    /**
     * Invoke all methods annotated with the annotation {@link OnActivation} on the given {@link Object}.
     *
     * @param gameObject the {@link Object} that should be scanned for the {@link OnActivation} annotation
     */
    public void invokeActivators(final Object gameObject) {
        invoke(gameObject, OnActivation.class);
    }

    /**
     * Invoke all methods annotated with the annotation {@link OnPostActivation} on the given {@link Object}.
     *
     * @param gameObject the {@link Object} that should be scanned for the {@link OnPostActivation} annotation
     */
    public void invokePostActivators(final Object gameObject) {
        invoke(gameObject, OnPostActivation.class);
    }

    private <T extends Annotation> void invoke(final Object gameObject, final Class<T> annotation) {
        for (final var method : gameObject.getClass().getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.invoke(gameObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new YaegerEngineException(e);
                }
            }
        }
    }

    /**
     * When calling this method, the {@link Object} provided als its parameter will be scanned for
     * the annotation {@link UpdatableProvider}, if the {@link Object} is an {@link UpdateDelegator}.
     *
     * @param gameObject the object that will be scanned for the {@link UpdatableProvider} annotation
     */
    public void configureUpdateDelegators(final Object gameObject) {
        if (gameObject instanceof UpdateDelegator updateDelegator) {
            for (final var method : gameObject.getClass().getMethods()) {
                if (method.isAnnotationPresent(UpdatableProvider.class)) {
                    UpdatableProvider annotation = method.getAnnotation(UpdatableProvider.class);
                    try {
                        final var providedUpdatable = method.invoke(updateDelegator);
                        if (providedUpdatable instanceof Updatable delegatedUpdatable) {
                            updateDelegator.getUpdater().addUpdatable(delegatedUpdatable, annotation.asFirst());
                        }
                    } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                        throw new YaegerEngineException(e);
                    }
                }
            }
        }
    }
}
