module yaeger.core {
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires transitive javafx.graphics;
    requires transitive com.google.guice;

    exports nl.han.yaeger.engine;
    exports nl.han.yaeger.engine.annotations;
    exports nl.han.yaeger.engine.entities;
    exports nl.han.yaeger.engine.scenes;
    exports nl.han.yaeger.engine.scenes.delegates;
    exports nl.han.yaeger.engine.media.repositories;
    exports nl.han.yaeger.javafx.image;
    exports nl.han.yaeger.engine.debug;
    exports nl.han.yaeger.javafx.debug;
    exports nl.han.yaeger.guice.factories;
    exports nl.han.yaeger.javafx.animationtimer;
    exports nl.han.yaeger.engine.entities.entity.sprite;
    exports nl.han.yaeger.engine.entities.tilemap;
    exports nl.han.yaeger.engine.entities.entity;
    exports nl.han.yaeger.engine.entities.entity.shape.text;
    exports nl.han.yaeger.engine.entities.entity.shape.rectangle;
    exports nl.han.yaeger.engine.entities.entity.motion;
    exports nl.han.yaeger.engine.scenes.splash;
    exports nl.han.yaeger.engine.entities.entity.collisions;
    exports nl.han.yaeger.engine.entities.entity.events.userinput;
    exports nl.han.yaeger.engine.styles;

    opens fonts.avenirnext;
    opens fonts.avenirnextcondensed;
    opens images;
}
