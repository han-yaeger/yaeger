# Changes in this release

## Configuration

* Updated Java to 16
* Updated JavaFX to 16
* Updated Mockito to 3.8.0
* Updated Jacoco to 0.8.6
* Updated jUnit to 5.7.1
* Updated Guice to 5.0.1

## Refactor

## Features

* Added a `MouseMovedListener` interface, which gets called whenever the mouse has been moved. As a parameter, the
  handler receives a `Coordinate2D` that contains the current coordinates of the mouse pointer.
* Exposed a `getCursor()` method for all Entities.

## Bugfixes
