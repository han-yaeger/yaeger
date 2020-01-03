package nl.meron.showcase.scenes.selection;

import javafx.scene.input.KeyCode;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.spriteentities.entities.Ball;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.scenes.YaegerScene;
import nl.meron.yaeger.engine.scenes.impl.DynamicScene;
import nl.meron.yaeger.engine.scenes.impl.StaticScene;

import java.util.Set;

public class SelectionScene extends StaticScene {

    private YaegerShowCase showCase;

    public SelectionScene(final YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/images/nature.jpg");
    }

    @Override
    public void setupEntities() {

    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.DIGIT1)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_TEXT_ENTITIES);
        } else if (input.contains(KeyCode.DIGIT2)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_SPRITE_ENTITIES);
        }
    }
}
