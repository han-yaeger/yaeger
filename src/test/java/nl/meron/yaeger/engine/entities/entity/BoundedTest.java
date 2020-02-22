package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BoundedTest {

    private Node node;
    private Bounds bounds;
    private BoundedImpl sut;

    @BeforeEach
    void setup() {
        node = mock(Node.class, withSettings().withoutAnnotations());
        bounds = mock(Bounds.class);

        sut = new BoundedImpl();
        sut.setNode(node);
        when(node.getBoundsInLocal()).thenReturn(bounds);
    }

    @Test
    void geBoundsReturnsZeroBoundingBoxIfGameNodeIsNotPresent() {
        // Setup
        var sut = new EmptyNodeBoundedImpl();


        // Test
        var boundingBox = sut.getBounds();

        // Verify
        Assertions.assertEquals(0, boundingBox.getWidth());
        Assertions.assertEquals(0, boundingBox.getHeight());
    }

    @Test
    void getLeftXReturnValueFromBounds() {
        // Setup
        var minX = 0.37;
        when(bounds.getMinX()).thenReturn(minX);

        // Test
        double leftSideX = sut.getLeftX();

        // Verify
        Assertions.assertEquals(minX, leftSideX);
    }

    @Test
    void getRightXReturnValueFromBounds() {
        // Setup
        var maxX = 0.42;
        when(bounds.getMaxX()).thenReturn(maxX);

        // Test
        double rightSideX = sut.getRightX();

        // Verify
        Assertions.assertEquals(maxX, rightSideX);
    }

    @Test
    void getCenterXReturnValueFromBounds() {
        // Setup
        var centerX = 0.24;
        when(bounds.getCenterX()).thenReturn(centerX);

        // Test
        double returnedCenterX = sut.getCenterX();

        // Verify
        Assertions.assertEquals(centerX, returnedCenterX);
    }

    @Test
    void getTopYReturnValueFromBounds() {
        // Setup
        var minY = 0.37;
        when(bounds.getMinY()).thenReturn(minY);

        // Test
        double topSideY = sut.getTopY();

        // Verify
        Assertions.assertEquals(minY, topSideY);
    }

    @Test
    void getBottomYReturnValueFromBounds() {
        // Setup
        var minY = 0.42;
        when(bounds.getMaxY()).thenReturn(minY);

        // Test
        double bottomY = sut.getBottomY();

        // Verify
        Assertions.assertEquals(minY, bottomY);
    }

    @Test
    void getCenterYReturnValueFromBounds() {
        // Setup
        var centerY = 0.23;
        when(bounds.getCenterY()).thenReturn(centerY);

        // Test
        double returnedCenterY = sut.getCenterY();

        // Verify
        Assertions.assertEquals(centerY, returnedCenterY);
    }

    @Test
    void getWidthReturnValueFromBounds() {
        // Setup
        var width = 37d;
        when(bounds.getWidth()).thenReturn(width);

        // Test
        double returnedWidth = sut.getWidth();

        // Verify
        Assertions.assertEquals(width, returnedWidth);
    }

    @Test
    void getHeightReturnValueFromBounds() {
        // Setup
        var height = 0.42;
        when(bounds.getHeight()).thenReturn(height);

        // Test
        double returnedHeight = sut.getHeight();

        // Verify
        Assertions.assertEquals(height, returnedHeight);
    }
}

class BoundedImpl implements Bounded {

    private Node node;

    @Override
    public Optional<Node> getGameNode() {
        return Optional.of(node);
    }

    public void setNode(Node node) {
        this.node = node;
    }
}

class EmptyNodeBoundedImpl implements Bounded {

    @Override
    public Optional<Node> getGameNode() {
        return Optional.empty();
    }
}
