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
    private static final Position POSITION = new Position(37, 37);
    private static final Font FONT = Font.font(Waterworld.FONT, FontWeight.BOLD, 240);
    private static final Color COLOR = Color.DARKBLUE;
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
        verify(text).setX(POSITION.getX());
        verify(text).setY(POSITION.getY());
    }

    @Test
    void getPositionReturnsTheSetPosition() {
        // Setup
        var textEntity = new TextEntity();

        // Test
        textEntity.setPosition(POSITION);
        textEntity.setDelegate(text);

        // Verify
        Assertions.assertEquals(POSITION, textEntity.getPosition());
    }

    @Test
    void settingTheDelegateSetsPositionOnDelegateForEmptyConstructor() {
        // Setup
        var textEntity = new TextEntity();

        // Test
        textEntity.setPosition(POSITION);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setX(POSITION.getX());
        verify(text).setY(POSITION.getY());
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

        // Test
        textEntity.setFill(COLOR);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setFill(COLOR);
    }

    @Test
    void settingTheDelegateSetsFontOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);


        // Test
        textEntity.setFont(FONT);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setFont(FONT);
    }

    @Test
    void settingTheDelegateSetsVisibleOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POSITION);

        // Test
        textEntity.setVisible(false);
        textEntity.setDelegate(text);

        // Verify
        verify(text).setVisible(false);
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

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Setup
        var textEntity = new TextEntity();
        textEntity.setDelegate(text);

        // Test
        textEntity.setPosition(POSITION);
        textEntity.setText(YAEGER);
        textEntity.setVisible(false);
        textEntity.setFont(FONT);
        textEntity.setFill(COLOR);

        // Verify
        verify(text).setVisible(false);
        verify(text).setFill(COLOR);
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
        verify(text).setX(POSITION.getX());
        verify(text).setY(POSITION.getY());

    }
}
