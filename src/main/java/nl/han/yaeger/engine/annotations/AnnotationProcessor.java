package nl.han.yaeger.engine.annotations;

import nl.han.yaeger.engine.Updatable;
import nl.han.yaeger.engine.UpdateDelegator;
import nl.han.yaeger.engine.exceptions.YaegerEngineException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The {@link AnnotationProcessor} is responsible for processing Yaeger specific annotations.
 */
public class AnnotationProcessor {

    /**
     * Invoke all methods annotated with the annotation {@link OnActivation} on the given {@link Object}.
     *
     * @param gameObject The {@link Object} that should be scanned for the {@link OnActivation} annotation.
     */
    public void invokeActivators(final Object gameObject) {
        invoke(gameObject, OnActivation.class);
    }

    /**
     * Invoke all methods annotated with the annotation {@link OnPostActivation} on the given {@link Object}.
     *
     * @param gameObject The {@link Object} that should be scanned for the {@link OnPostActivation} annotation.
     */
    public void invokePostActivators(final Object gameObject) {
        invoke(gameObject, OnPostActivation.class);
    }

    private void invoke(final Object gameObject, Class annotation) {
        for (Method method : gameObject.getClass().getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.invoke(gameObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new YaegerEngineException(e);
                }
            }
        }
    }

    public void configureUpdateDelegators(final Object gameObject) {
        if (gameObject instanceof UpdateDelegator) {
            var updateDelegator = (UpdateDelegator) gameObject;
            for (Method method : gameObject.getClass().getMethods()) {
                if (method.isAnnotationPresent(UpdatableProvider.class)) {
                    UpdatableProvider annotation = method.getAnnotation(UpdatableProvider.class);
                    try {
                        Updatable delegatedUpdatable = (Updatable) method.invoke(updateDelegator);
                        updateDelegator.getUpdater().addUpdatable(delegatedUpdatable, annotation.asFirst());
                    } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                        throw new YaegerEngineException(e);
                    }
                }
            }
        }
    }
}
