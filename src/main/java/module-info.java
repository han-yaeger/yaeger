/**
 * The main (and only) module for Yaeger. Currently both the public and internal API are part of
 * this one module.
 */
module hanyaeger {
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires transitive javafx.graphics;
    requires com.google.guice;

    exports com.github.hanyaeger.api;
    exports com.github.hanyaeger.api.scenes;
    exports com.github.hanyaeger.api.media;
    exports com.github.hanyaeger.api.userinput;
    exports com.github.hanyaeger.api.entities;
    exports com.github.hanyaeger.api.entities.impl.sprite;
    exports com.github.hanyaeger.api.entities.impl.text;
    exports com.github.hanyaeger.api.entities.impl.rectangle;
    exports com.github.hanyaeger.api.entities.impl.circle;
    exports com.github.hanyaeger.api.entities.impl.ellipse;

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

    opens yaegerimages;
}
