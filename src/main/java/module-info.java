/**
 * The main (and only) module for Yaeger. Currently both the public and internal API are part of
 * this one module.
 * <p>
 * Only the subpackages of the public API are exported ({@code com.github.hanyaeger.api}) and directly
 * accessible through JavaDoc. Those classes that are only meant for internal use ({@code com.github.hanyaeger.core})
 * are not exported and can not be used from outside this module. Their JavaDoc is indirectly accessible, when viewing
 * the JavaDoc of the external API.
 */
module hanyaeger {
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;

    requires javafx.media;

    requires com.google.guice;

    exports com.github.hanyaeger.api;
    exports com.github.hanyaeger.api.scenes;
    exports com.github.hanyaeger.api.media;
    exports com.github.hanyaeger.api.userinput;
    exports com.github.hanyaeger.api.entities;
    exports com.github.hanyaeger.api.entities.impl;

    exports com.github.hanyaeger.core.factories.animationtimer to com.google.guice;
    exports com.github.hanyaeger.core.factories.debug to com.google.guice;
    exports com.github.hanyaeger.core.factories.image to com.google.guice;
    exports com.github.hanyaeger.core.repositories to com.google.guice;
    exports com.github.hanyaeger.core.annotations to com.google.guice;
    exports com.github.hanyaeger.core.scenes.splash to com.google.guice;
    exports com.github.hanyaeger.core.scenes.delegates to com.google.guice;
    exports com.github.hanyaeger.core.factories to com.google.guice;
    exports com.github.hanyaeger.core to com.google.guice;
    exports com.github.hanyaeger.core.entities to com.google.guice;
    exports com.github.hanyaeger.core.entities.motion to com.google.guice;
    exports com.github.hanyaeger.core.media to com.google.guice;

    opens yaegerimages;
}
