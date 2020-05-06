package nl.meron.yaeger.engine.entities.entity.shape.circle;

import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CircleEntityTest {
    public static final double MIN_Y = 6d;
    private static final Location LOCATION = new Location(37, 37);
    private static final Color COLOR_FILL = Color.DARKBLUE;
    private static final Color COLOR_STROKE = Color.LIGHTBLUE;
    public static final double RADIUS = 3d;
    public static final double STROKE_WIDTH = 4d;
    public static final double MIN_X = 5d;
    public static final double MAX_X = 15d;
    public static final double MAX_Y = 16d;

    private Circle circle;
    private Injector injector;

    @BeforeEach
    void setup() {
        circle = mock(Circle.class);
        injector = mock(Injector.class);
    }

    @Test
    void getNodeReturnsEmptyNodeIfTextNotSet() {
        // Arrange
        var sut = new CircleEntityImpl(new Location(0, 0));

        // Act
        Optional<Node> gameNode = sut.getGameNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void settingPositionWithoutDelegateStoresPositionAsInitialPosition() {
        // Arrange
        var sut = new CircleEntityImpl(new Location(0, 0));

        // Act
        sut.setOriginX(LOCATION.getX());
        sut.setOriginY(LOCATION.getY());

        // Verify
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getX(), LOCATION.getX()));
        Assertions.assertEquals(0, Double.compare(sut.getInitialLocation().getY(), LOCATION.getY()));
    }

    @Test
    void getGameNodeReturnsTheRCircle() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);

        // Act
        sut.setCircle(circle);
        sut.init(injector);

        // Verify
        Assertions.assertEquals(circle, sut.getGameNode().get());
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Arrange
        var circleEntity = new CircleEntityImpl(LOCATION);
        circleEntity.setCircle(circle);
        circleEntity.init(injector);

        // Act
        circleEntity.setRadius(RADIUS);
        circleEntity.setStrokeWidth(STROKE_WIDTH);
        circleEntity.setFill(COLOR_FILL);
        circleEntity.setStrokeColor(COLOR_STROKE);

        // Verify
        verify(circle).setVisible(true);
        verify(circle).setRadius(RADIUS);
        verify(circle).setStrokeWidth(STROKE_WIDTH);
        verify(circle).setStroke(COLOR_STROKE);
        verify(circle).setFill(COLOR_FILL);
    }

    @Test
    void setOriginXStoresCorrectlyAfterInit() {
        var sut = new CircleEntityImpl(new Location(0, 0));
        sut.setCircle(circle);
        sut.init(injector);
        sut.setOriginX(LOCATION.getX());

        verify(circle).setCenterX(LOCATION.getX());
    }

    @Test
    void setOriginYStoresCorrectlyAfterInit() {
        var sut = new CircleEntityImpl(new Location(0, 0));
        sut.setCircle(circle);
        sut.init(injector);
        sut.setOriginY(LOCATION.getY());

        verify(circle).setCenterY(LOCATION.getY());
    }

    @Test
    void getLeftXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(circle.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(circle.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setCircle(circle);
        sut.init(injector);

        // Act
        double leftX = sut.getLeftX();

        // Verify
        Assertions.assertEquals(MIN_X + (STROKE_WIDTH / 2), leftX);
    }

    @Test
    void getTopXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(circle.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(circle.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setCircle(circle);
        sut.init(injector);

        // Act
        double topY = sut.getTopY();

        // Verify
        Assertions.assertEquals(MIN_Y + (STROKE_WIDTH / 2), topY);
    }

    @Test
    void ifCircleNotYetSetStrokeRadiusIsStoredAndSetAtInit() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);

        // Act
        sut.setRadius(RADIUS);
        sut.setCircle(circle);
        sut.init(injector);

        // Verify
        verify(circle).setRadius(RADIUS);
    }

    @Test
    void ifRectangleNotYetSetStrokeColorIsStoredAndSetAtInit() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);

        // Act
        sut.setStrokeColor(COLOR_STROKE);
        sut.setCircle(circle);
        sut.init(injector);

        // Verify
        verify(circle).setStroke(COLOR_STROKE);
    }

    @Test
    void ifRectangleNotYetSetStrokeWidthIsStoredAndSetAtInit() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);

        // Act
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setCircle(circle);
        sut.init(injector);

        // Verify
        verify(circle).setStrokeWidth(STROKE_WIDTH);
    }

    @Test
    void ifRectangleNotYetSetFillIsStoredAndSetAtInit() {
        // Arrange
        var sut = new CircleEntityImpl(LOCATION);

        // Act
        sut.setFill(COLOR_FILL);
        sut.setCircle(circle);
        sut.init(injector);

        // Verify
        verify(circle).setFill(COLOR_FILL);
    }

    private class CircleEntityImpl extends CircleEntity {

        /**
         * Create a new {@link CircleEntity} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link CircleEntity} should be placed
         */
        public CircleEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        public Point2D getInitialLocation() {
            return new Point2D(initialX, initialY);
        }
    }
}
