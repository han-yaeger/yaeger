package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class NodeProviderTest {

    @Test
    void toFrontIsDelegatedToTheNode() {
        // Setup
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var sut = new NodeProviderImpl();
        sut.setNode(node);

        // Test
        sut.toFront();

        // Verify
        verify(node).toFront();
    }
}

class NodeProviderImpl implements NodeProvider {
    private Node node;

    @Override
    public Node getGameNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
