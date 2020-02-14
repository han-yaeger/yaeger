package nl.meron.yaeger.engine.annotations;

import nl.meron.yaeger.engine.entities.entity.*;
import nl.meron.yaeger.engine.exceptions.YaegerEngineException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationProcessor {

    public void invokeInitializers(final Object gameObject) {
        for (Method method : gameObject.getClass().getMethods()) {
            if (method.isAnnotationPresent(Initializer.class)) {
                try {
                    method.invoke(gameObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new YaegerEngineException(e);
                }
            }
        }
    }

    public void configureUpdateDelegators(final Updatable updatable) {
        if (updatable instanceof UpdateDelegator) {
            var updateDelegator = (UpdateDelegator) updatable;
            for (Method method : updatable.getClass().getMethods()) {
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
