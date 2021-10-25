package com.github.hanyaeger.api;

import javafx.geometry.Point2D;

/**
 * A {@link Coordinate2D} is a 2D geometric point that represents a pair of coordinates.
 */
public class Coordinate2D extends Point2D {

    /**
     * Creates a new instance of {@link Coordinate2D} at (0,0).
     */
    public Coordinate2D() {
        super(0, 0);
    }

    /**
     * Creates a new instance of {@link Coordinate2D}.
     *
     * @param x the x coordinate of the {@link Coordinate2D}
     * @param y the y coordinate of the {@link Coordinate2D}
     */
    public Coordinate2D(final double x, final double y) {
        super(x, y);
    }

    /**
     * Creates a new instance of a {@link Coordinate2D}.
     *
     * @param point a {@link Point2D} representation of this {@link Coordinate2D}
     */
    public Coordinate2D(final Point2D point) {
        super(point.getX(), point.getY());
    }

    @Override
    public Coordinate2D add(final Point2D point2D) {
        return new Coordinate2D(super.add(point2D));
    }

    /**
     * Returns a {@code Coordinate2D} with the coordinates of the specified point added to the coordinates of this
     * {@code Coordinate2D}.
     *
     * @param location the {@code Coordinate2D} those coordinates are to be added
     * @return the {@code Coordinate2D} with the added coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D add(final Coordinate2D location) {
        return new Coordinate2D(super.add(location));
    }
    
    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point subtracted from the coordinates of this
     * {@code Coordinate2D}.
     * 
     * @param location the {@code Coordinate2D} with the subtracted coordinates
     * @return the {@code Coordinate2D} with the subtracted coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D subtract(final Coordinate2D location) {
    	return new Coordinate2D(super.subtract(location));
    }
    
    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point multiplied by the coordinates of this
     * {@code Coordinate2D}.
     * 
     * @param location the {@code Coordinate2D} with the multiplied coordinates
     * @return the {@code Coordinate2D} with the multiplied coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D multiply(final Coordinate2D location) throws NullPointerException {
    	if(location == null) {
    		throw new NullPointerException("'location' was null");
    	}
    	
    	return new Coordinate2D(this.getX() * location.getX(), this.getY() * location.getY());
    }
    
    /**
     * Return a {@code Coordinate2D} with the coordinates of the specified point divided by the coordinates of this
     * {@code Coordinate2D}.
     * 
     * @param location the {@code Coordinate2D} with the divided coordinates
     * @return the {@code Coordinate2D} with the divided coordinates
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     * throws {@link IllegalArgumentException} if an X,Y component of either coordinate is 0
     */
    public Coordinate2D divide(final Coordinate2D location) throws NullPointerException, IllegalArgumentException {
    	if(location == null) {
    		throw new NullPointerException("'location' was null");
    	}
    	
    	if(this.getX() == 0.D || this.getY() == 0.D || location.getX() == 0.D || location.getY() == 0.D) {
    		throw new IllegalArgumentException("An X,Y component of either coordinate is 0");
    	}
    	
    	return new Coordinate2D(this.getX() / location.getX(), this.getY() / location.getY());
    }
    
    
    /***
     * Return a {@code Coordinate2D} with the coordinates of 'this' {@code Coordinate2D} transformed by the dot product of 'this' {@code Coordinate2D} + 'location'.
     * @param location is the {@code Coordinate2D} to use as the point of reference for the dot product calculation.
     * @return the {@code Coordinate2D} with the transformed coordinates.
     * throws {@link NullPointerException} if the specified {@code Coordinate2D} is null
     */
    public Coordinate2D dotProduct(final Coordinate2D location) {
    	if(location == null) {
    		throw new NullPointerException("'location' was null");
    	}
    	
    	double scalar = (this.getX() * location.getX()) + (this.getY() * location.getY());
    	Coordinate2D normalized = this.normalize();
    	return new Coordinate2D(normalized.getX() * scalar, normalized.getY() * scalar);
    }
    
    /**
     * Returns a {@code Coordinate2D} which lies in the middle between this {@code Coordinate2D} and the specified {@code Coordinate2D}.
     * @param location is the other endpoint
     * @return the coordinate in the middle.
     */
    public Coordinate2D midPoint(final Coordinate2D location) {
    	if(location == null) {
    		throw new NullPointerException("'location' was null");
    	}
    	
    	return new Coordinate2D(super.midpoint(location));
    }
    
    /***
     * Return a normalized {@code Coordinate2D}.
     * @return the variant of 'this' vector
     */
    public Coordinate2D normalize() {
    	return new Coordinate2D(super.normalize());
    }
    
    /**
     * Return a {@code Coordinate2D} which is the inverse of this coordinate
     * @return the {@code Coordinate2D} with the location of this coordinate, inverted
     */
    public Coordinate2D invert() {
    	return new Coordinate2D(this.getX() * -1, this.getY() * -1);
    }
}
