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
* Changed the event handler for the `MouseButtonPressedListener` and `MouseButtonReleasedListener` to use
  a `Coordinate2D` as a second parameter, instead of `int x, int y`.
* Changed the package structure for better encapsulation of the internal api. This is a major refactorings and breaks
  all current implementations (#181).
* Renamed the module from `hanyaeger.api` to `hanyaeger` (#181).

## Features

* Added a `MouseMovedListener` interface, which gets called whenever the mouse has been moved. As a parameter, the
  handler receives a `Coordinate2D` that contains the current coordinates of the mouse pointer.
* Exposed a `getCursor()` method for all Entities.
* Generalized the mouse event handlers to also be applicable to a `YaegerScene`. This way, both instances
  of `YaegerEntity`and `YawegerScene` can implement them and receive Mouse events.
* Added a `CustomFont` class, which can be used for using a custom font, based on a font file (#184).
* Added several listeners that together provide drag-and-drop (#186). All these listeners are applicable to both a
  `YaegerScenc` and a `YaegerEntity`, except the `MouseDraggedListener`, which makes little sense on a `YaegerScene`
  The following listeners have been added:
    * `MouseDraggedListener`
    * `MouseDragEnterListener`
    * `MouseDragExitListener`
    * `MouseDropListener`
    * `MouseMovedWhileDraggingListener`
* Added methods to both `YaegerEntity` and `YaegerScene` to set the *contrast*, *hue* and *saturation* (#92).
* Added the option to set the `viewOrder` of a `YaegerEntity`. This enables the option to decide the rendering order of
  all instances of `YaegerEntity` within a `YaegerScene`. By default, a new instance of `YaegerEntity` will receive
  a `viewOrder` of 37. If the instance was created through a `TileMap` it receives a `viewOrder` of 100, placing it
  behind other entities (#178).
* Added the option to remove a `Timer` from a `TimerContainer` (either a `YaegerEntity` or `YaegerScene`). Previously
  a `Timer` could only be paused; now it is possible to completely remove it (#197).
* Added a method `isActive()` which states whether the `Timer` is currently active (e.g. not paused). Since
  a `EntitySpawner` extends `Timer`, this method is also available for `EntitySpawner`.
* Added the option to remove a `EntitySpawner` from an `EntitySpawnerContainer`. Previously an `EntitySpawner` could
  only be paused; now it is possible to completely remove it (#195).
* Added the option to access the instances of `YaegerEntity`, created by a `TileMap`. If a reference to the
  `TileMap` is still available, it can be used to call `getInstanceMap()`, which returns a two dimensional array of
  all instances created by the `TileMap` (#202).

## Bugfixes

* When a new background audio is set, the previous background audio is first stopped (#192).
* Through the exposure of the `setViewOrder(double)` method tile-maps no longer appear in front of regular entities
  (#190).
* Both `EntitySpawner` and `Timer` can now be added outside, respectively, the `setupEntitySpawners()` and
  `setupTimers()` method.
