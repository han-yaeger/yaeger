package nl.han.ica.yaeger.engine.entities.entity.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.han.ica.waterworld.Waterworld;
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
    void settingTheDelegateSetsPositionOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setDelegate(text);

        // Verify
        verify(text).setX(0);
        verify(text).setY(0);
    }

    @Test
    void settingTheDelegateSetsTextOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setText(YAEGER);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void settingTheDelegateSetsFillOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);
        var color = Color.DARKBLUE;

        // Test
        textEntity.setFill(color);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setFill(color);
    }

    @Test
    void settingTheDelegateSetsFontOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);
        var font = Font.font(Waterworld.FONT, FontWeight.BOLD, 240);

        // Test
        textEntity.setFont(font);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setFont(font);
    }

    @Test
    void settingTheTextWiresAMousePressedEvent() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setDelegate(text);

        // Verify
        verify(text).setOnMousePressed(any());
    }

    @Test
    void settingTheTextWithContentDelegatesContent() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);

        // Test
        textEntity.setDelegate(text);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);
        textEntity.setDelegate(text);

        // Test
        textEntity.remove();

        // Verify
        verify(text, times(1)).setVisible(false);
        verify(text).setText(null);
        verify(text).fireEvent(any(RemoveEntityEvent.class));
    }

    @Test
    void getGameNodeReturnsTheTextDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION, YAEGER);

        // Test
        textEntity.setDelegate(text);

        // Verify
        Assertions.assertEquals(text, textEntity.getGameNode());
    }
}
