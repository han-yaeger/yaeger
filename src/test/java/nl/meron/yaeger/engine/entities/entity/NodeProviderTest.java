package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class NodeProviderTest {

    @Test
    void toFrontIsDelegatedToTheNode() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var sut = new NodeProviderImpl();
        sut.setNode(Optional.of(node));

        // Test
        sut.toFront();

        // Verify
        verify(node).toFront();
    }

    @Test
    void toBackIsDelegatedToTheNode() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var sut = new NodeProviderImpl();
        sut.setNode(Optional.of(node));

        // Test
        sut.toBack();

        // Verify
        verify(node).toBack();
    }
}

class NodeProviderImpl implements NodeProvider {
    private Optional<Node> node;

    @Override
    public Optional<Node> getGameNode() {
        return node;
    }

    public void setNode(Optional<Node> node) {
        this.node = node;
    }
}
