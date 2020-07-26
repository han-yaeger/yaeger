package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.google.inject.Injector;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EllipseEntityTest {
    private static final Location LOCATION = new Location(37, 37);
    public static final double RADIUS_X = 37;
    public static final double RADIUS_Y = 42;

    private Ellipse ellipse;
    private Injector injector;
    private EllipseEntity sut;

    @BeforeEach
    void setup() {
        ellipse = mock(Ellipse.class);
        injector = mock(Injector.class);

        sut = new EllipseEntityImpl(LOCATION);
    }

    @Test
    void settingRadiusXAfterShapeIsSetDelegatesTheRadiusX() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        // Act
        sut.setRadiusX(RADIUS_X);

        // Verify
        verify(ellipse).setVisible(true);
        verify(ellipse).setRadiusX(RADIUS_X);
    }

    @Test
    void settingRadiusYAfterShapeIsSetDelegatesTheRadiusY() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        // Act
        sut.setRadiusY(RADIUS_Y);

        // Verify
        verify(ellipse).setVisible(true);
        verify(ellipse).setRadiusY(RADIUS_Y);
    }

    @Test
    void ifShapeNotYetSetRadiusXIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadiusX(RADIUS_X);
        sut.setShape(ellipse);

        // Act
        sut.init(injector);

        // Verify
        verify(ellipse).setRadiusX(RADIUS_X);
    }

    @Test
    void ifShapeNotYetSetRadiusYIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadiusY(RADIUS_Y);
        sut.setShape(ellipse);

        // Act
        sut.init(injector);

        // Verify
        verify(ellipse).setRadiusY(RADIUS_Y);
    }

    private class EllipseEntityImpl extends EllipseEntity {

        public EllipseEntityImpl(Location initialPosition) {
            super(initialPosition);
        }
    }
}
