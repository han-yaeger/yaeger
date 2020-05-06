module nl.meron.yaeger {
    requires javafx.controls;
    requires javafx.media;
    requires com.google.guice;
    requires java.base;
    requires java.desktop;

    exports nl.meron.pong;
    exports nl.meron.showcase;
    exports nl.meron.waterworld;
    exports nl.meron.yaeger.engine;
    exports nl.meron.yaeger.engine.annotations;
    exports nl.meron.yaeger.engine.entities;
    exports nl.meron.yaeger.engine.scenes;
    exports nl.meron.yaeger.engine.scenes.delegates;
    exports nl.meron.yaeger.engine.media.repositories;
    exports nl.meron.yaeger.javafx.image;
    exports nl.meron.yaeger.engine.debug;
    exports nl.meron.yaeger.javafx.debug;
    exports nl.meron.yaeger.guice.factories;
    exports nl.meron.yaeger.javafx.animationtimer;
    exports nl.meron.yaeger.engine.entities.entity.sprite;
    exports nl.meron.yaeger.engine.entities.tilemap;
    exports nl.meron.yaeger.engine.entities.entity;
    exports nl.meron.yaeger.engine.entities.entity.shape.text;
    exports nl.meron.yaeger.engine.entities.entity.shape.rectangle;
    exports nl.meron.yaeger.engine.entities.entity.shape.circle;
    exports nl.meron.yaeger.engine.entities.entity.shape.ellipse;
    exports nl.meron.yaeger.engine.entities.entity.motion;
    exports nl.meron.yaeger.engine.scenes.splash;

    opens pong;
    opens showcase.backgrounds;
    opens showcase.entities;
    opens waterworld;
    opens waterworld.audio;
    opens waterworld.images;
}
