package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceableTest {

    @Test
    void nodeIsUsedForPosition() {
        // Setup
        var x = 37d;
        var y = 42d;
        var sut = new PlaceableImpl();
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var bounds = mock(Bounds.class);

        sut.setNode(node);
        when(node.getBoundsInLocal()).thenReturn(bounds);
        when(bounds.getMinX()).thenReturn(x);
        when(bounds.getMinY()).thenReturn(y);

        // Test
        Point2D position = sut.getLocation();

        // Verify
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
    }

}

class PlaceableImpl implements Placeable {

    private Node node;

    @Override
    public void placeOnLocation(double x, double y) {
        // Not required here.
    }

    @Override
    public Node getGameNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
