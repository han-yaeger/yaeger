package nl.han.ica.yaeger;

import nl.han.ica.yaeger.gameobjects.GameObject;
import nl.han.ica.yaeger.testobjects.TestEngine;
import nl.han.ica.yaeger.testobjects.gameobjects.TestGameObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class YaegerEngineTest {

    private YaegerEngine engine;

    @BeforeEach
    public void setup() {
        engine = new TestEngine();
    }


    @Test
    void addedGameObjectGetsAdded() {
        GameObject testObject = new TestGameObject();
        engine.addGameObject(testObject);

//        Assertions.assertEquals(1, engine.getGameObjects().size());
    }

    @Test
    void gameObjectsCannotBeAddedMultipleTimes() {
        GameObject testObject = new TestGameObject();
        engine.addGameObject(testObject);
        engine.addGameObject(testObject);
        engine.addGameObject(testObject);

//        Assertions.assertEquals(1, engine.getGameObjects().size());
    }
}





