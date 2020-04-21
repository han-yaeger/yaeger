module nl.han.yaeger {
    requires javafx.controls;
    requires javafx.media;
    requires com.google.guice;
    requires java.base;

    exports nl.han.pong;
    exports nl.han.showcase;
    exports nl.han.waterworld;
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

    opens pong;
    opens showcase.backgrounds;
    opens showcase.entities;
    opens waterworld;
    opens waterworld.audio;
    opens waterworld.images;
}
