package nl.han.yaeger.engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link UpdatableProvider} should return an {@link nl.han.yaeger.engine.Updatable}
 * and will automatically be called during activation of an {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}
 * or {@link nl.han.yaeger.engine.scenes.YaegerScene} to gathers all objects that contain an {@link nl.han.yaeger.engine.Updatable#update(long)}
 * method that should be called at each Game world update.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdatableProvider {
    boolean asFirst() default false;
}
