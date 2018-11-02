package nl.han.ica.yaeger.engine.entities.entity.text;

import javafx.scene.text.Text;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.events.RemoveEntityEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TextEntityTest {

    private static final String YAEGER = "Yaeger";
    private static final Position POSITION = new Position(0, 0);
    private Text text;

    @BeforeEach
    void setup() {
        text = mock(Text.class);
    }

    @Test
    void settingTheTextSetsPositionOnText() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setDelgate(text);

        // Verify
        verify(text).setX(0);
        verify(text).setY(0);
    }

    @Test
    void settingTheTextWiresAMousePressedEvent() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setDelgate(text);

        // Verify
        verify(text).setOnMousePressed(any());
    }

    @Test
    void settingTheTextWithContentDelegatesContent() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);

        // Test
        textEntity.setDelgate(text);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);
        textEntity.setDelgate(text);

        // Test
        textEntity.remove();

        // Verify
        verify(text).setVisible(false);
        verify(text).setText(null);
        verify(text).fireEvent(any(RemoveEntityEvent.class));
    }

    @Test
    void getGameNodeReturnsTheTextDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);

        // Test
        textEntity.setDelgate(text);

        // Verify
        Assertions.assertEquals(text, textEntity.getGameNode());
    }
}
