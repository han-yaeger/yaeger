package nl.meron.yaeger.engine.entities.entity.events.userinput;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ButtonPressedListenerTest {

    @Test
    void attachMousePressedListenerAttachesMouseListener() {
        // Arrange
        var node = mock(Node.class, withSettings().withoutAnnotations());
        var buttonListeningEntity = new ButtonPressedListenerTest.ButtonPressedListeningInstance();
        buttonListeningEntity.setNode(node);

        // Act
        buttonListeningEntity.attachButtonPressedListener();

        // Assert
        verify(node).setOnMousePressed(any());
    }

    private class ButtonPressedListeningInstance implements ButtonPressedListener {

        private Node node;

        @Override
        public void onButtonPressed(MouseButton button) {
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);
        }

        void setNode(Node node) {
            this.node = node;
        }
    }
}
