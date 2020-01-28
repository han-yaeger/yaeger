package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class YaegerSceneTest {

    private YaegerScene testScene;
    private Scene scene;

    @BeforeEach
    void setup() {
        testScene = new YaegerSceneImpl();
        scene = mock(Scene.class);
        ((YaegerSceneImpl) testScene).setScene(scene);
    }

    @Test
    void setCursorDelegatesToScene() {
        // Setup

        // Test
        testScene.setCursor(Cursor.DEFAULT);

        // Verify
        verify(scene).setCursor(Cursor.DEFAULT);
    }

    private class YaegerSceneImpl implements YaegerScene {

        private Scene scene;

        public void setScene(Scene scene) {
            this.scene = scene;
        }

        @Override
        public void setupScene() {

        }

        @Override
        public void setupEntities() {

        }

        @Override
        public void postActivation() {

        }

        @Override
        public void setBackgroundImage(String url) {

        }

        @Override
        public void setBackgroundAudio(String url) {

        }

        @Override
        public Scene getScene() {
            return scene;
        }

        @Override
        public void clear() {

        }

        @Override
        public void configure() {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void init(Injector injector) {

        }
    }
}
