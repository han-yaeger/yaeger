package nl.meron.yaeger.engine.entities.entity;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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

        // Verify
        assertEquals(x, sut.getOriginX());
        assertEquals(y, sut.getOriginY());
    }
}

class PlaceableImpl implements Placeable {

    private Node node;

    @Override
    public Optional<Node> getGameNode() {
        return Optional.of(this.node);
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public AnchorPoint getAnchorPoint() {
        return null;
    }

    @Override
    public void setOriginX(double x) {
        // Not required here.
    }

    @Override
    public void setOriginY(double y) {
        // Not required here.
    }

    @Override
    public void placeOnScene() {

    }
}
