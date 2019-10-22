package nl.meron.yaeger.engine.entities;

import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import nl.meron.yaeger.engine.entities.entity.Entity;
import nl.meron.yaeger.engine.entities.entity.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EntityTest {

    private static final double SCENE_WIDTH = 37d;
    public static final double SCENE_HEIGHT = 42d;

    private Entity testEntity;
    private Node node;
    private Scene scene;

    @BeforeEach
    void setup() {
        testEntity = new TestEntity();
        node = mock(Node.class);
        scene = mock(Scene.class);
        ((TestEntity) testEntity).setNode(node);
    }

    @Test
    void getSceneWidthReturnsSceneWidthFromNode() {
        // Setup
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        // Test
        double sceneWidth = testEntity.getSceneWidth();

        // Verify
        assertEquals(SCENE_WIDTH, sceneWidth);
    }

    @Test
    void getSceneHeightReturnsSceneHeightFromNode() {
        // Setup
        when(node.getScene()).thenReturn(scene);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        // Test
        double sceneHeight = testEntity.getSceneHeight();

        // Verify
        assertEquals(SCENE_HEIGHT, sceneHeight);
    }

    @Test
    void setVisibleDelegatesToNode() {
        // Setup

        // Test
        testEntity.setVisible(true);

        // Verify
        verify(node).setVisible(true);
    }

    @Test
    void getBoundsDelegatesToNode() {
        // Setup
        BoundingBox bounds = new BoundingBox(0, 0, 10, 10);
        when(node.getBoundsInParent()).thenReturn(bounds);

        // Test
        Bounds entityBounds = testEntity.getBounds();

        // Verify
        assertEquals(bounds, entityBounds);

    }


    private class TestEntity implements Entity {

        private Node node;

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Node getGameNode() {
            return node;
        }

        @Override
        public Position getPosition() {
            return null;
        }

        @Override
        public double getRightSideXCoordinate() {
            return 0;
        }

        @Override
        public double getLeftSideXCoordinate() {
            return 0;
        }

        @Override
        public double getBottomYCoordinate() {
            return 0;
        }

        @Override
        public double getTopYCoordinate() {
            return 0;
        }

        void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }
    }
}
