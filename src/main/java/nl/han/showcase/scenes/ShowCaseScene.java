package nl.han.showcase.scenes;

import javafx.scene.input.KeyCode;
import nl.han.showcase.YaegerShowCase;
import nl.han.yaeger.engine.scenes.DynamicScene;

import java.util.Set;

public abstract class ShowCaseScene extends DynamicScene {

    /**
     * Return the YaegerShowCase Game
     *
     * @return An instance of {@link YaegerShowCase}
     */
    public abstract YaegerShowCase getShowCase();

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.DIGIT0)) {
            getShowCase().setActiveScene(YaegerShowCase.SCENE_SELECTION);
        }
    }
}
