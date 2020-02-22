package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneChildTest {

    private SceneChildImpl sut;
    private Node node;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new SceneChildImpl();
        node = mock(Node.class, withSettings().withoutAnnotations());
        scene = mock(Scene.class);
        sut.setNode(Optional.of(node));

        when(node.getScene()).thenReturn(scene);

    }

    @Test
    void getWidthDelegatesToNodeIfPresent() {
        // Setup

        // Test
        sut.getSceneWidth();

        // Verify
        verify(scene).getWidth();
    }

    @Test
    void getHeightDelegatesToNodeIfPresent() {
        // Setup

        // Test
        sut.getSceneHeight();

        // Verify
        verify(scene).getHeight();
    }

    @Test
    void getWidthReturnsZeroIfNodeNotPresent() {
        // Setup

        // Test
        double sceneWidth = sut.getSceneWidth();

        // Verify
        Assertions.assertEquals(0, sceneWidth);
    }


    @Test
    void getHeightReturnsZeroIfNodeNotPresent() {
        // Setup

        // Test
        double sceneHeight = sut.getSceneHeight();

        // Verify
        Assertions.assertEquals(0, sceneHeight);
    }

    private class SceneChildImpl implements SceneChild {

        private Optional<Node> node;

        @Override
        public Optional<Node> getGameNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }
    }

}
