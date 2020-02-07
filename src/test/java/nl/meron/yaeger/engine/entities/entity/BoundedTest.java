package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getLeftSideXReturnValueFromBounds() {
        // Setup
        var minX = 0.37;
        when(bounds.getMinX()).thenReturn(minX);

        // Test
        double leftSideX = sut.getLeftSideX();

        // Verify
        Assertions.assertEquals(minX, leftSideX);
    }

    @Test
    void getRightSideXReturnValueFromBounds() {
        // Setup
        var maxX = 0.42;
        when(bounds.getMaxX()).thenReturn(maxX);

        // Test
        double rightSideX = sut.getRightSideX();

        // Verify
        Assertions.assertEquals(maxX, rightSideX);
    }

    @Test
    void getTopSideYReturnValueFromBounds() {
        // Setup
        var minY = 0.37;
        when(bounds.getMinY()).thenReturn(minY);

        // Test
        double topSideY = sut.getTopY();

        // Verify
        Assertions.assertEquals(minY, topSideY);
    }

    @Test
    void getBottomSideYReturnValueFromBounds() {
        // Setup
        var minY = 0.42;
        when(bounds.getMaxY()).thenReturn(minY);

        // Test
        double bottomY = sut.getBottomY();

        // Verify
        Assertions.assertEquals(minY, bottomY);
    }
}

class BoundedImpl implements Bounded {

    private Node node;

    @Override
    public Node getGameNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
