package nl.meron.yaeger.engine.scenes;

import com.google.inject.Injector;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class YaegerSceneTest {

    private YaegerScene sut;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new YaegerSceneImpl();
        scene = mock(Scene.class);
        ((YaegerSceneImpl) sut).setScene(scene);
    }

    @Test
    void setCursorDelegatesToScene() {
        // Setup

        // Test
        sut.setCursor(Cursor.DEFAULT);

        // Verify
        verify(scene).setCursor(Cursor.DEFAULT);
    }

    @Test
    void getWidthReturnValueFromScene() {
        // Setup
        var width = 37d;
        when(scene.getWidth()).thenReturn(width);

        // Test
        double returnedWidth = sut.getWidth();

        // Verify
        Assertions.assertEquals(width, returnedWidth);
    }

    @Test
    void getHeightReturnValueFromScene() {
        // Setup
        var height = 0.42;
        when(scene.getHeight()).thenReturn(height);

        // Test
        double returnedHeight = sut.getHeight();

        // Verify
        Assertions.assertEquals(height, returnedHeight);
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
