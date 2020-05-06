package nl.meron.yaeger.engine.entities.entity.shape.ellipse;

import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import nl.meron.yaeger.engine.entities.entity.Location;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class EllipseEntityTest {
    public static final double MIN_Y = 6d;
    private static final Location LOCATION = new Location(37, 37);
    private static final Color COLOR_FILL = Color.DARKBLUE;
    private static final Color COLOR_STROKE = Color.LIGHTBLUE;
    public static final double RADIUS_X = 3d;
    public static final double RADIUS_Y = 3d;
    public static final double STROKE_WIDTH = 4d;
    public static final double MIN_X = 5d;
    public static final double MAX_X = 15d;
    public static final double MAX_Y = 16d;

    private Ellipse ellipse;
    private Injector injector;

    @BeforeEach
    void setup() {
        ellipse = mock(Ellipse.class);
        injector = mock(Injector.class);
    }

    @Test
    void getNodeReturnsEmptyNodeIfTextNotSet() {
        // Arrange
        var sut = new EllipseEntityImpl(new Location(0, 0));

        // Act
        Optional<Node> gameNode = sut.getGameNode();

        // Assert
        Assertions.assertTrue(gameNode.isEmpty());
    }

    @Test
    void settingPositionWithoutDelegateStoresPositionAsInitialPosition() {
        // Arrange
        var sut = new EllipseEntityImpl(new Location(0, 0));

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
        var sut = new EllipseEntityImpl(LOCATION);

        // Act
        sut.setEllipse(ellipse);
        sut.init(injector);

        // Verify
        Assertions.assertEquals(ellipse, sut.getGameNode().get());
    }

    @Test
    void settingValuesAfterDelegateIsSetDelegatesTheValues() {
        // Arrange
        var circleEntity = new EllipseEntityImpl(LOCATION);
        circleEntity.setEllipse(ellipse);
        circleEntity.init(injector);

        // Act
        circleEntity.setRadiusX(RADIUS_X);
        circleEntity.setRadiusY(RADIUS_Y);
        circleEntity.setStrokeWidth(STROKE_WIDTH);
        circleEntity.setFill(COLOR_FILL);
        circleEntity.setStrokeColor(COLOR_STROKE);

        // Verify
        verify(ellipse).setVisible(true);
        verify(ellipse).setRadiusX(RADIUS_X);
        verify(ellipse).setRadiusY(RADIUS_Y);
        verify(ellipse).setStrokeWidth(STROKE_WIDTH);
        verify(ellipse).setStroke(COLOR_STROKE);
        verify(ellipse).setFill(COLOR_FILL);
    }

    @Test
    void setOriginXStoresCorrectlyAfterInit() {
        var sut = new EllipseEntityImpl(new Location(0, 0));
        sut.setEllipse(ellipse);
        sut.init(injector);
        sut.setOriginX(LOCATION.getX());

        verify(ellipse).setCenterX(LOCATION.getX());
    }

    @Test
    void setOriginYStoresCorrectlyAfterInit() {
        var sut = new EllipseEntityImpl(new Location(0, 0));
        sut.setEllipse(ellipse);
        sut.init(injector);
        sut.setOriginY(LOCATION.getY());

        verify(ellipse).setCenterY(LOCATION.getY());
    }

    @Test
    void getLeftXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(ellipse.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(ellipse.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setEllipse(ellipse);
        sut.init(injector);

        // Act
        double leftX = sut.getLeftX();

        // Verify
        Assertions.assertEquals(MIN_X + (STROKE_WIDTH / 2), leftX);
    }

    @Test
    void getTopXTakesTheStrokeWidthIntoAccount() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);
        var bounds = mock(BoundingBox.class);
        when(ellipse.getBoundsInLocal()).thenReturn(bounds);

        when(bounds.getMinX()).thenReturn(MIN_X);
        when(bounds.getMinY()).thenReturn(MIN_Y);
        when(bounds.getMaxX()).thenReturn(MAX_X);
        when(bounds.getMaxY()).thenReturn(MAX_Y);

        when(ellipse.getStrokeWidth()).thenReturn(STROKE_WIDTH);

        sut.setEllipse(ellipse);
        sut.init(injector);

        // Act
        double topY = sut.getTopY();

        // Verify
        Assertions.assertEquals(MIN_Y + (STROKE_WIDTH / 2), topY);
    }

    @Test
    void ifEllipseNotYetSetStrokeRadiusIsStoredAndSetAtInit() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);

        // Act
        sut.setRadiusX(RADIUS_X);
        sut.setRadiusY(RADIUS_Y);
        sut.setEllipse(ellipse);
        sut.init(injector);

        // Verify
        verify(ellipse).setRadiusX(RADIUS_X);
        verify(ellipse).setRadiusY(RADIUS_Y);
    }

    @Test
    void ifEllipseNotYetSetStrokeColorIsStoredAndSetAtInit() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);

        // Act
        sut.setStrokeColor(COLOR_STROKE);
        sut.setEllipse(ellipse);
        sut.init(injector);

        // Verify
        verify(ellipse).setStroke(COLOR_STROKE);
    }

    @Test
    void ifRectangleNotYetSetStrokeWidthIsStoredAndSetAtInit() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);

        // Act
        sut.setStrokeWidth(STROKE_WIDTH);
        sut.setEllipse(ellipse);
        sut.init(injector);

        // Verify
        verify(ellipse).setStrokeWidth(STROKE_WIDTH);
    }

    @Test
    void ifEllipseNotYetSetFillIsStoredAndSetAtInit() {
        // Arrange
        var sut = new EllipseEntityImpl(LOCATION);

        // Act
        sut.setFill(COLOR_FILL);
        sut.setEllipse(ellipse);
        sut.init(injector);

        // Verify
        verify(ellipse).setFill(COLOR_FILL);
    }

    private class EllipseEntityImpl extends EllipseEntity {

        /**
         * Create a new {@link EllipseEntity} on the given {@code initialPosition}.
         *
         * @param initialPosition The initial position at which this {@link EllipseEntity} should be placed
         */
        public EllipseEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        public Point2D getInitialLocation() {
            return new Point2D(initialX, initialY);
        }
    }
}
