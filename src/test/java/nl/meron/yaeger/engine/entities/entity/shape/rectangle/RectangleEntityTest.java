package nl.meron.yaeger.engine.entities.entity.shape.rectangle;

import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RectangleEntityTest {
    public static final double MIN_Y = 6d;
    private static final Location LOCATION = new Location(37, 37);
    private static final Color COLOR_FILL = Color.DARKBLUE;
    private static final Color COLOR_STROKE = Color.LIGHTBLUE;
    public static final double ARC_HEIGHT = 1d;
    public static final double ARC_WIDTH = 2d;
    public static final double WIDTH = 3d;
    public static final double STROKE_WIDTH = 4d;
    public static final double HEIGHT = 5d;
    public static final double MIN_X = 5d;
    public static final double MAX_X = 15d;
    public static final double MAX_Y = 16d;

    private Rectangle rectangle;
    private Injector injector;

    @BeforeEach
    void setup() {
        rectangle = mock(Rectangle.class);
        injector = mock(Injector.class);
    }

    @Test
    void getNodeReturnsEmptyNodeIfTextNotSet() {
        // Arrange
        var sut = new RectangleEntityImpl(new Location(0, 0));

        // Act
        Optional<Node> gameNode = sut.getGameNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void settingPositionWithoutDelegateStoresPositionAsInitialPosition() {
        // Arrange
        var sut = new RectangleEntityImpl(new Location(0, 0));

        // Act
        sut.setOriginX(LOCATION.getX());
        sut.setOriginY(LOCATION.getY());

        // Verify
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getX(), LOCATION.getX()));
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getY(), LOCATION.getY()));
    }

    @Test
    void getGameNodeReturnsTheRectangle() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        Assertions.assertEquals(rectangle, sut.getGameNode().get());
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Arrange
        var rectangleEntity = new RectangleEntityImpl(LOCATION);
        rectangleEntity.setRectangle(rectangle);
        rectangleEntity.init(injector);

        // Act
        rectangleEntity.setArcHeight(ARC_HEIGHT);
        rectangleEntity.setArcWidth(ARC_WIDTH);
        rectangleEntity.setWidth(WIDTH);
        rectangleEntity.setHeight(HEIGHT);
        rectangleEntity.setStrokeWidth(STROKE_WIDTH);
        rectangleEntity.setFill(COLOR_FILL);
        rectangleEntity.setStrokeColor(COLOR_STROKE);

        // Verify
        verify(rectangle).setVisible(true);
        verify(rectangle).setArcWidth(ARC_WIDTH);
        verify(rectangle).setArcHeight(ARC_HEIGHT);
        verify(rectangle).setWidth(WIDTH);
        verify(rectangle).setHeight(HEIGHT);
        verify(rectangle).setStrokeWidth(STROKE_WIDTH);
        verify(rectangle).setStroke(COLOR_STROKE);
        verify(rectangle).setFill(COLOR_FILL);
    }

    @Test
    void getLeftXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(rectangle.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(rectangle.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setRectangle(rectangle);
        sut.init(injector);

        // Act
        double leftX = sut.getLeftX();

        // Verify
        Assertions.assertEquals(MIN_X + (STROKE_WIDTH / 2), leftX);
    }

    @Test
    void getTopXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(rectangle.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(rectangle.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setRectangle(rectangle);
        sut.init(injector);

        // Act
        double topY = sut.getTopY();

        // Verify
        Assertions.assertEquals(MIN_Y + (STROKE_WIDTH / 2), topY);
    }

    @Test
    void ifRectangleNotYetSetStrokeHeightIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setHeight(HEIGHT);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setHeight(HEIGHT);
    }

    @Test
    void ifRectangleNotYetSetArcWidthIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setArcWidth(ARC_WIDTH);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setArcWidth(ARC_WIDTH);
    }

    @Test
    void ifRectangleNotYetSetArcHeightIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setArcHeight(ARC_HEIGHT);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setArcHeight(ARC_HEIGHT);
    }

    @Test
    void ifRectangleNotYetSetStrokeColorIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setStrokeColor(COLOR_STROKE);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setStroke(COLOR_STROKE);
    }

    @Test
    void ifRectangleNotYetSetStrokeWidthIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setStrokeWidth(STROKE_WIDTH);
    }

    @Test
    void ifRectangleNotYetSetFillIsStoredAndSetAtInit() {
        // Arrange
        var sut = new RectangleEntityImpl(LOCATION);

        // Act
        sut.setFill(COLOR_FILL);
        sut.setRectangle(rectangle);
        sut.init(injector);

        // Verify
        verify(rectangle).setFill(COLOR_FILL);
    }

    private class RectangleEntityImpl extends RectangleEntity {

        /**
         * Create a new {@link RectangleEntity} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link RectangleEntity} should be placed
         */
        public RectangleEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        public Point2D getInitialLocation() {
            return new Point2D(initialX, initialY);
        }
    }
}
