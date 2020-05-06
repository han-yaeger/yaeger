package nl.meron.showcase.scenes.selection;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Button;
import nl.meron.showcase.buttons.Quit;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.StaticScene;

import java.util.Set;

public class SelectionScene extends StaticScene {

    public static final Color TEXT_COLOR = Color.YELLOW;
    public static final Color TEXT_COLOR_HIGHLIGHT = Color.LIGHTYELLOW;
    private static final Font TEXT_FONT = Font.font("American Typewriter", FontWeight.BOLD, 30);
    private YaegerShowCase showCase;

    public SelectionScene(final YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/nature.jpg");
    }

    @Override
    public void setupEntities() {
        var quitButton = new Quit(showCase);
        addEntity(quitButton);

        var select = new TextEntity(new Location(20, 30), "Please select a scene...");
        styleText(select);
        addEntity(select);

        var textEntities = new Button(new Location(20, 70), "1) Text Entities", showCase, YaegerShowCase.SCENE_TEXT_ENTITIES);
        styleText(textEntities);
        addEntity(textEntities);

        var spriteEntities = new Button(new Location(20, 100), "2) Sprite Entities", showCase, YaegerShowCase.SCENE_SPRITE_ENTITIES);
        styleText(spriteEntities);
        addEntity(spriteEntities);

        var shapeEntities = new Button(new Location(20, 130), "3) Shape Entities", showCase, YaegerShowCase.SCENE_SHAPE_ENTITIES);
        styleText(shapeEntities);
        addEntity(shapeEntities);

        var dynamicSceneWithTimers = new Button(new Location(20, 160), "4) Dynamic Scene with a Timer", showCase, YaegerShowCase.SCENE_WITH_TIMERS);
        styleText(dynamicSceneWithTimers);
        addEntity(dynamicSceneWithTimers);

        var entityMapsScene = new Button(new Location(20, 190), "5) Dynamic Scene with an EntityMap", showCase, YaegerShowCase.SCENE_WITH_ENTITYMAPS);
        styleText(entityMapsScene);
        addEntity(entityMapsScene);

        var mouseEventsScene = new Button(new Location(20, 220), "6) Mouse Events Showcase", showCase, YaegerShowCase.MOUSE_EVENTS_SCENE);
        styleText(mouseEventsScene);
        addEntity(mouseEventsScene);

        var brightnessScene = new Button(new Location(20, 250), "7) Brightness & Contrast Demo", showCase, YaegerShowCase.BRIGHTNESS_DEMO);
        styleText(brightnessScene);
        addEntity(brightnessScene);

        var MouseListenersScene = new Button(new Location(20, 280), "8) Mouse Listeners Showcase", showCase, YaegerShowCase.MOUSE_LISTENERS_SCENE);
        styleText(MouseListenersScene);
        addEntity(MouseListenersScene);
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.DIGIT1)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_TEXT_ENTITIES);
        } else if (input.contains(KeyCode.DIGIT2)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_SPRITE_ENTITIES);
        } else if (input.contains(KeyCode.DIGIT3)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_SHAPE_ENTITIES);
        } else if (input.contains(KeyCode.DIGIT4)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_WITH_TIMERS);
        } else if (input.contains(KeyCode.DIGIT5)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_WITH_ENTITYMAPS);
        } else if (input.contains(KeyCode.DIGIT6)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_WITH_SPAWNERS);
        }
    }

    void styleText(TextEntity entity) {
        entity.setFill(TEXT_COLOR);
        entity.setFont(TEXT_FONT);
    }
}
