package nl.meron.yaeger.engine.entities.entity;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class NodeProviderTest {

    @Test
    void toFrontIsDelegatedToTheNode() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var sut = new NodeProviderImpl();
        sut.setNode(Optional.of(node));

        // Act
        sut.toFront();

        // Assert
        verify(node).toFront();
    }

    @Test
    void toBackIsDelegatedToTheNode() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var sut = new NodeProviderImpl();
        sut.setNode(Optional.of(node));

        // Act
        sut.toBack();

        // Assert
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
