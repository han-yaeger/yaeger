package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CircleEntityTest {
    private static final Location LOCATION = new Location(37, 37);
    public static final double RADIUS = 37;

    private Circle circle;
    private Injector injector;
    private CircleEntityImpl sut;

    @BeforeEach
    void setup() {
        circle = mock(Circle.class);
        injector = mock(Injector.class);

        sut = new CircleEntityImpl(LOCATION);
    }

    @Test
    void settingRadiusAfterShapeIsSetDelegatesTheRadius() {
        // Arrange
        sut.setShape(circle);
        sut.init(injector);

        // Act
        sut.setRadius(RADIUS);

        // Verify
        verify(circle).setVisible(true);
        verify(circle).setRadius(RADIUS);
    }

    @Test
    void ifShapeNotYetSetRadiusIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadius(RADIUS);
        sut.setShape(circle);

        // Act
        sut.init(injector);

        // Verify
        verify(circle).setRadius(RADIUS);
    }

    private class CircleEntityImpl extends CircleEntity {

        public CircleEntityImpl(Location initialPosition) {
            super(initialPosition);
        }
    }
}
