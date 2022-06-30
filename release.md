# Changes in this release

## Configuration

* Updated Mockito to 4.5.1
* Updated jUnit to 5.8.1
* Updated JavaFX to 18.0.1
* Updated Jacoco to 0.8.8
* Updated Nexus Staging Maven Plugin to 1.6.13
* Updated the JavaDoc Maven Plugin to 3.4.0

## Refactor

* Switched all usages of the Stream-API for-each for the traditional version.
  Measurements have shown that this might improve speed (#233).

## Features

* Allowed a `Collided` to support multi collisions (#209). A `YaegerEntity`
  that implements the interface `Collided` will now receive a list of all
  Entities (implementing `Collider`) that it collides with during the last Game
  World Update. Note that this feature breaks the API.
* Enabled a  `Coordinate2D` to calculate the `angleto(Coordinate2D)` method
  (#215).
* Added a `ScrollableDynamicScene`(#57) and updated all the mouse listeners to
  use the coordinates from the full scrollable area.
* A `Coordinate2D` now exposes all vector-related methods.
* The debugger now also lists the frames per second. This value is calculated as
  the average number of frames (#224) during two seconds
  (divided by 2).
* Added constants for diagonal directions to `Directions.java`.

## Bugfixes

* Resolved a bug on Windows in which the size of the stage was larger that the
  viewable area. (#208)
* Restricted the Game World Update to a max of 60 fps. (#211). The Game World
  Update is triggered on each screen refresh, which defaults to 60fps. On
  systems with a higher refresh rate, this value is now limited to a max of
  60fps to ensure games run at about the same speed.
* Removed the input lag (#236) that occurred due to the key pressed delay.
* Resolved #234, which caused setting the autocycle of a `SpriteEntity` to reset
  the row to -1.
* Resolved #235, which caused Boundingbox Visualizers to block Mouse Events on
  the underlying Entities.
* Resolved #238, which caused some `setActiveScene(int)` calls to lead to a
  `ConcurrentModificationException`.
