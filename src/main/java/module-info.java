module nl.meron.yaeger {
    requires javafx.controls;
    requires javafx.media;
    requires com.google.guice;
    requires java.base;

    exports nl.meron.pong;
    exports nl.meron.waterworld;
    exports nl.meron.yaeger.engine.entities;
    exports nl.meron.yaeger.engine.scenes.impl;
    exports nl.meron.yaeger.engine.scenes.delegates;
    exports nl.meron.yaeger.engine.media.repositories;
    exports nl.meron.yaeger.javafx.image;
    exports nl.meron.yaeger.engine.debug;
    exports nl.meron.yaeger.javafx.debug;
    exports nl.meron.yaeger.guice.factories;
    exports nl.meron.yaeger.javafx.animationtimer;
    exports nl.meron.yaeger.engine.entities.entity.sprites;
    exports nl.meron.yaeger.engine.entities.entity;
    exports nl.meron.yaeger.engine.entities.entity.text;

    opens pong;
    opens waterworld;
    opens waterworld.audio;
    opens waterworld.images;
}
