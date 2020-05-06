package nl.meron.yaeger.engine.entities.entity.shape.text;

import com.google.inject.Injector;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    void getNodeReturnsEmptyNodeIfTextNotSet() {
        // Arrange
        var sut = new TextEntityImpl(new Location(0, 0));

        // Act
        Optional<Node> gameNode = sut.getGameNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void settingPositionWithoutDelegateStoresPositionAsInitialPosition() {
        // Setup
        var sut = new TextEntityImpl(new Location(0, 0));

        // Test
        sut.setOriginX(LOCATION.getX());
        sut.setOriginY(LOCATION.getY());

        // Assert
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getX(), LOCATION.getX()));
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getY(), LOCATION.getY()));
    }

    @Test
    void settingDelegateSetsTextOnDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION);

        // Test
        sut.setText(YAEGER);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Assert
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

        // Assert
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

        // Assert
        verify(text).setFont(FONT);
    }

    @Test
    void settingDelegateWithContentDelegatesContent() {
        // Setup
        TextEntity sut = new TextEntity(LOCATION, YAEGER);
        sut.setTextDelegate(text);
        sut.init(injector);

        // Assert
        verify(text).setText(YAEGER);
    }

    @Test
    void getGameNodeReturnsTheTextDelegate() {
        // Setup
        var sut = new TextEntity(LOCATION, YAEGER);

        // Test
        sut.setTextDelegate(text);
        sut.init(injector);

        // Assert
        Assertions.assertEquals(text, sut.getGameNode().get());
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

        // Assert
        verify(text).setVisible(false);
        verify(text).setFill(COLOR);
        verify(text).setText(YAEGER);
        verify(text).setFont(FONT);
    }

    @Test
    void setOriginXStoresCorrectlyAfterInit() {
        var sut = new TextEntity(LOCATION);
        sut.setTextDelegate(text);
        sut.init(injector);
        sut.setOriginX(LOCATION.getX());

        verify(text).setX(LOCATION.getX());
    }

    @Test
    void setOriginYStoresCorrectlyAfterInit() {
        var sut = new TextEntity(LOCATION);
        sut.setTextDelegate(text);
        sut.init(injector);
        sut.setOriginY(LOCATION.getY());

        verify(text).setY(LOCATION.getY());
    }

    private class TextEntityImpl extends TextEntity {

        /**
         * Create a new {@link TextEntityImpl} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link TextEntityImpl} should be placed
         */
        public TextEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        public Point2D getInitialLocation() {
            return new Point2D(initialX, initialY);
        }
    }
}
