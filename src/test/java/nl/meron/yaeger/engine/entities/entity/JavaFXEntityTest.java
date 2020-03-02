package nl.meron.yaeger.engine.entities.entity;

import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import nl.meron.yaeger.engine.Timer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JavaFXEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private static final double ENTITY_WIDTH = 200d;
    private static final double ENTITY_HEIGHT = 100d;
    private JavaFXEntityImpl sut;
    private Node node;
    private Injector injector;
    private BoundingBox boundingBox;

    @BeforeEach
    void setup() {
        sut = new JavaFXEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(Optional.of(node));
        boundingBox = mock(BoundingBox.class);
        when(node.getBoundsInLocal()).thenReturn(boundingBox);
        when(boundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(boundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Setup

        // Test
        List<Timer> timers = sut.getTimers();

        // Verify
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    void initCallsSetVisible() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        verify(node).setVisible(true);
    }

    @Test
    void placeOnSceneCallsSetXWithInitialLocation() {
        // Setup
        sut.init(injector);

        // Test
        sut.placeOnScene();

        // Verify
        Assertions.assertEquals(LOCATION.getX(), sut.getOriginX());
    }

    @Test
    void placeOnSceneCallsSetYWithInitialLocation() {
        // Setup
        sut.init(injector);

        // Test
        sut.placeOnScene();

        // Verify
        Assertions.assertEquals(LOCATION.getY(), sut.getOriginY());
    }

    @Test
    void setVisibleDelegatesToNode() {
        // Setup

        // Test
        sut.setVisible(false);

        // Verify
        verify(node).setVisible(false);
    }

    @Test
    void removeCallsSetVisibleFalseOnGameNode() {
        // Setup

        // Test
        sut.remove();

        // Verify
        verify(node).setVisible(false);
    }

    @Test
    void setAnchorPointBedoreNodeIsSetStoresAnchorPoint() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        sut.setAnchorPoint(AnchorPoint.RIGHT_CENTER);

        // Assert
        Assertions.assertEquals(AnchorPoint.RIGHT_CENTER, sut.getAnchorPoint());
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        // Assert
        verify(node).setTranslateX(0);
        verify(node).setTranslateY(0);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForLEFT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.LEFT_CENTER);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForCENTER_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForRIGHT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.RIGHT_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    private class JavaFXEntityImpl extends JavaFXEntity {

        private Optional<Node> node;
        private double x;
        private double y;

        public JavaFXEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public Optional<Node> getGameNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }

        public double getOriginX() {
            return x;
        }

        public double getOriginY() {
            return y;
        }

        @Override
        public void setOriginX(double x) {
            this.x = x;
        }

        @Override
        public void setOriginY(double y) {
            this.y = y;
        }
    }
}
