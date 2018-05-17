package nl.han.ica.yaeger;

import javafx.stage.Stage;
import nl.han.ica.yaeger.gameobjects.GameObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;
import java.util.concurrent.TimeoutException;

@ExtendWith(ApplicationExtension.class)
public class YaegerEngineTest {

    private YaegerEngine engine;
    private GameObject gameObject;


    @BeforeEach
    public void setup() throws TimeoutException {

        gameObject = new GameObject() {
            @Override
            public void update() {

            }
        };


        engine = (YaegerEngine) FxToolkit.setupApplication(TestEngine.class);
        FxToolkit.registerPrimaryStage();
    }

    @Start
    @Test
    public void gameObjectIsAdded(Stage stage) {

        engine.addGameObject(gameObject);

        List<GameObject> gameObjects = engine.getGameObjects();

        int numberOfGameObjects = gameObjects.size();
        Assertions.assertEquals(1, numberOfGameObjects);

        GameObject addedGameObject = gameObjects.get(0);
        Assertions.assertEquals(gameObject, addedGameObject);
    }

    @Test
    public void gameObjectIsAddedOnlyOnce() {
        List<GameObject> gameObjects = engine.getGameObjects();

        int numberOfGameObjects = gameObjects.size();
        Assertions.assertEquals(1, numberOfGameObjects);
    }
}





