# Changes in this release

## Configuration

* Updated Java to 16
* Updated JavaFX to 16
* Updated Mockito to 3.8.0
* Updated Jacoco to 0.8.6
* Updated jUnit to 5.7.1
* Updated Guice to 5.0.1

## Refactor

* The Mouse and Key related event handlers have been moved from the `entities.entity.userinput` package to `userinput`,
  since they now also apply to an instance of `YaegerScene`.

## Features

* Added a `MouseMovedListener` interface, which gets called whenever the mouse has been moved. As a parameter, the
  handler receives a `Coordinate2D` that contains the current coordinates of the mouse pointer.
* Exposed a `getCursor()` method for all Entities.
* Generalized the mouse event handlers to also be applicable to a `YaegerScene`. This way, both instances
  of `YaegerEntity`
  and `YawegerScene` can implement them and receive Mouse events.

## Bugfixes
