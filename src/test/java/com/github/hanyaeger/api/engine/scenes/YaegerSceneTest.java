package com.github.hanyaeger.api.engine.scenes;

import com.github.hanyaeger.api.engine.YaegerConfig;
import com.google.inject.Injector;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class YaegerSceneTest {

    private YaegerSceneImpl sut;
    private Scene scene;
    private Stage stage;

    @BeforeEach
    void setup() {
        sut = new YaegerSceneImpl();
        scene = mock(Scene.class);
        stage = mock(Stage.class);
        sut.setScene(scene);
        sut.setStage(stage);
    }

    @Test
    void setCursorDelegatesToScene() {
        // Arrange

        // Act
        sut.setCursor(Cursor.DEFAULT);

        // Verify
        verify(scene).setCursor(Cursor.DEFAULT);
    }

    @Test
    void getWidthReturnValueFromStage() {
        // Arrange
        var width = 37d;
        when(stage.getWidth()).thenReturn(width);

        // Act
        var returnedWidth = sut.getWidth();

        // Verify
        Assertions.assertEquals(width, returnedWidth);
    }

    @Test
    void getHeightReturnValueFromStage() {
        // Arrange
        var height = 0.42;
        when(stage.getHeight()).thenReturn(height);

        // Act
        double returnedHeight = sut.getHeight();

        // Verify
        Assertions.assertEquals(height, returnedHeight);
    }

    private class YaegerSceneImpl implements YaegerScene {

        private Scene scene;
        private Stage stage;

        public void setScene(Scene scene) {
            this.scene = scene;
        }

        @Override
        public void setupScene() {
            // Not required here
        }

        @Override
        public void setupEntities() {
            // Not required here
        }

        @Override
        public void postActivate() {
            // Not required here
        }

        @Override
        public void setBackgroundColor(Color color) {
            // Not required here
        }

        @Override
        public void setBackgroundImage(String url) {
            // Not required here
        }

        @Override
        public void setBackgroundAudio(String url) {
            // Not required here
        }

        @Override
        public void setBrightness(double brightness) {
            // Not required here
        }

        @Override
        public double getBrightness() {
            return 0;
        }

        @Override
        public Scene getScene() {
            return scene;
        }

        @Override
        public Stage getStage() {
            return stage;
        }

        @Override
        public void setStage(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void setConfig(YaegerConfig yaegerConfig) {
            // Not required here
        }

        @Override
        public void clear() {

        }

        @Override
        public void activate() {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void init(Injector injector) {

        }
    }
}
