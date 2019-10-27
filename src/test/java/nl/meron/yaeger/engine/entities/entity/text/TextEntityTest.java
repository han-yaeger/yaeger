package nl.meron.yaeger.engine.entities.entity.text;

import com.google.inject.Injector;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.events.listeners.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.events.RemoveEntityEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TextEntityTest {

    private static final String YAEGER = "Yaeger";
    private static final Point POINT = new Point(37, 37);
    private static final Font FONT = Font.font(Waterworld.FONT, FontWeight.BOLD, 240);
    private static final Color COLOR = Color.DARKBLUE;
    private Text text;
    private Injector injector;

    @BeforeEach
    void setup() {

        text = mock(Text.class);
        injector = mock(Injector.class);
    }

    @Test
    void settingTheDelegateSetsPositionOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POINT);

        // Test
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setX(POINT.getX());
        verify(text).setY(POINT.getY());
    }

    @Test
    void getPositionReturnsTheSetPosition() {
        // Setup
        var textEntity = new TextEntity();

        // Test
        textEntity.setPosition(POINT);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        Assertions.assertEquals(POINT, textEntity.getAnchorPoint());
    }

    @Test
    void settingDelegateSetsPositionOnDelegateForEmptyConstructor() {
        // Setup
        var textEntity = new TextEntity();

        // Test
        textEntity.setPosition(POINT);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setX(POINT.getX());
        verify(text).setY(POINT.getY());
    }

    @Test
    void settingDelegateSetsTextOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POINT);

        // Test
        textEntity.setText(YAEGER);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void settingDelegateSetsFillOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POINT);

        // Test
        textEntity.setFill(COLOR);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setFill(COLOR);
    }

    @Test
    void settingDelegateSetsFontOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POINT);


        // Test
        textEntity.setFont(FONT);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setFont(FONT);
    }

    @Test
    void settingDelegateSetsVisibleOnDelegate() {
        // Setup
        var textEntity = new TextEntity(POINT);

        // Test
        textEntity.setVisible(false);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setVisible(false);
    }

    @Test
    void settingDelegateWithContentDelegatesContent() {
        // Setup
        var textEntity = new TextEntity(POINT, YAEGER);

        // Test
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void callingRemoveCleansUpTheEntity() {
        // Setup
        var textEntity = new TextEntity(POINT, YAEGER);
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

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
        var textEntity = new TextEntity(POINT, YAEGER);

        // Test
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Verify
        Assertions.assertEquals(text, textEntity.getGameNode());
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Setup
        var textEntity = new TextEntity();
        textEntity.setTextDelegate(text);
        textEntity.init(injector);

        // Test
        textEntity.setPosition(POINT);
        textEntity.setText(YAEGER);
        textEntity.setVisible(false);
        textEntity.setFont(FONT);
        textEntity.setFill(COLOR);

        // Verify
        verify(text).setVisible(false);
        verify(text).setFill(COLOR);
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
        verify(text).setX(POINT.getX());
        verify(text).setY(POINT.getY());
    }

    @Test
    void initializingAMouseButtonListeningTextEntityAttachesMouseListener() {
        // Setup
        var textEntity = new MousePressedListeningTextEntity();
        textEntity.setTextDelegate(text);

        // Text
        textEntity.init(injector);

        // Verify
        Assertions.assertTrue(textEntity.mouseListenerAttached);
    }

    private class MousePressedListeningTextEntity extends TextEntity implements MousePressedListener {

        private boolean mouseListenerAttached = false;

        @Override
        public void attachMousePressedListener() {
            mouseListenerAttached = true;
        }

        @Override
        public void onMousePressed(MouseButton button) {

        }
    }
}
