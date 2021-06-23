package com.github.hanyaeger.core.annotations;

import com.github.hanyaeger.core.Updatable;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.YaegerScene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link UpdatableProvider} should return an {@link Updatable}
 * and will automatically be called during activation of an {@link YaegerEntity}
 * or {@link YaegerScene} to gathers all objects that contain an {@link Updatable#update(long)}
 * method that should be called at each Game world update.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdatableProvider {
    /**
     * State whether the {@link Updatable} returned by the method annotated with this annotation should be added
     * to the front of the list of instances of {@link Updatable}. By default it is placed at the end of the list,
     * which also means it is called last.
     *
     * @return a {@code boolean} that states whether the {@link Updatable} should be added at the front of the list
     */
    boolean asFirst() default false;
}
