package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Injector;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TextEntityTest {

    private static final String YAEGER = "Yaeger";
    private static final Location LOCATION = new Location(37, 37);
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
    void settingDelegateSetsPositionOnDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION);

        // Test
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        verify(text).setTextOrigin(VPos.TOP);
        verify(text).setX(LOCATION.getX());
        verify(text).setY(LOCATION.getY());
    }

    @Test
    void settingDelegateSetsTextOnDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION);

        // Test
        sut.setText(YAEGER);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        verify(text).setTextOrigin(VPos.TOP);
        verify(text).setText(YAEGER);
    }

    @Test
    void settingDelegateSetsFillOnDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION);

        // Test
        sut.setFill(COLOR);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        verify(text).setFill(COLOR);
    }

    @Test
    void settingDelegateSetsFontOnDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION);

        // Test
        sut.setFont(FONT);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        verify(text).setFont(FONT);
    }

    @Test
    void settingDelegateWithContentDelegatesContent() {
        // Setup
        var sut = new TextEntity(LOCATION, YAEGER);

        // Test
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        verify(text).setText(YAEGER);
    }

    @Test
    void getGameNodeReturnsTheTextDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION, YAEGER);

        // Test
        sut.setTextDelegate(text);
        sut.init(injector);

        // Verify
        Assertions.assertEquals(text, sut.getGameNode());
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Setup
        var sut = new TextEntity(LOCATION);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Test
        sut.setText(YAEGER);
        sut.setVisible(false);
        sut.setFont(FONT);
        sut.setFill(COLOR);

        // Verify
        verify(text).setVisible(false);
        verify(text).setFill(COLOR);
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
        verify(text).setX(LOCATION.getX());
        verify(text).setY(LOCATION.getY());
    }
}
