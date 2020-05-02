package nl.han.yaeger.engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link OnActivation} will be automatically called by the
 * {@link AnnotationProcessor} after activation of either an {@link nl.han.yaeger.engine.entities.entity.YaegerEntity}
 * or a {@link nl.han.yaeger.engine.scenes.YaegerScene}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnPostActivation {
}
