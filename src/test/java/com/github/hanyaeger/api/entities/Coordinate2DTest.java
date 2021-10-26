package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Coordinate2DTest {

    @Test
    void addingPointWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Coordinate2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }

    @Test
    void addingPoint2DWorksAsExpected() {
        // Arrange
        var position = new Coordinate2D(0d, 0d);
        var positionToAdd = new Point2D(1d, 1d);

        // Act
        var addedPosition = position.add(positionToAdd);

        // Assert
        assertEquals(1d, addedPosition.getX());
        assertEquals(1d, addedPosition.getY());
    }
    
    @Test
    void subtractingPointWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(0d, 0d);
    	var positionToSubtract = new Coordinate2D(1d, 1d);
    	
    	// Act
    	var subtractedPosition = position.subtract(positionToSubtract);
    	
    	// Assert
    	assertEquals(-1d, subtractedPosition.getX());
    	assertEquals(-1d, subtractedPosition.getY());
    }
    
    @Test
    void subtractingPoint2DWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(0d, 0d);
    	var positionToSubtract = new Point2D(1d, 1d);
    	
    	// Act
    	var subtractedPosition = position.subtract(positionToSubtract);
    	
    	// Assert
    	assertEquals(-1d, subtractedPosition.getX());
    	assertEquals(-1d, subtractedPosition.getY());
    }
    
    @Test
    void multiplyPointWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(1d, 1d);
    	var positionToMultiply = new Coordinate2D(2d, 2d);
    	
    	// Act
    	var multipliedPosition = position.multiply(positionToMultiply);
    	
    	// Assert
    	assertEquals(2d, multipliedPosition.getX());
    	assertEquals(2d, multipliedPosition.getY());
    }
    
    @Test
    void dividePointWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(1d, 1d);
    	var positionToDivide = new Coordinate2D(2d, 2d);
    	
    	// Act
    	var dividedPosition = position.divide(positionToDivide);
    	
    	// Assert
    	assertEquals(0.5d, dividedPosition.getX());
    	assertEquals(0.5d, dividedPosition.getY());
    }
    
    @Test
    void invertPointWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(1d, 1d);
    	
    	// Act
    	var invertedPosition = position.invert();
    	
    	// Assert
    	assertEquals(-1d, invertedPosition.getX());
    	assertEquals(-1d, invertedPosition.getY());
    }
    
    @Test
    void normalizePointWorksAsExpected() {
    	// Arrange
    	var position = new Coordinate2D(2d, 2d);
    	
    	// Act
    	var normalizedPosition = position.normalize();
    	
    	// Assert
    	assertEquals(1d, normalizedPosition.getX());
    	assertEquals(1d, normalizedPosition.getY());
    }
}
