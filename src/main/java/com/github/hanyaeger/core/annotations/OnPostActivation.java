package com.github.hanyaeger.core.annotations;

import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link OnActivation} will be automatically called by the
 * {@link AnnotationProcessor} after activation of either an {@link YaegerEntity}
 * or a {@link YaegerScene}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnPostActivation {
}
