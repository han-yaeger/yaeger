# Changes in this release

## Configuration

* Updated Mockito to 4.0.0
* Updated jUnit to 5.8.1
* Updated JavaFX to 17.0.1

## Refactor

## Features

* Allowed `Collideds` to support multi collisions (#209). A `YaegerEntity`
  that implements the interface `Collided` will now receive a list of all
  Entities (implementing `Collider`) that it collides with during the last Game
  World Update. Note that this feature breaks the API.
* Enabled a  `Coordinate2D` to calculate the `angleto(Coordinate2D)` method
  (#215).
* Added a `ScrollableDynamicScene`(#57) and updated all the mouse listeners to
  use the coordinates from the full scrollable area.
* A `Coordinate2D` now exposes all vector-related methods.
* The debugger now also lists the frames per second. This value is
  calculated as the average number of frames (#224) during two seconds
  (divided by 2)

## Bugfixes

* Resolved a bug on Windows in which the size of the stage was larger that the
  viewable area. (#208)
* Restricted the Game World Update to a max of 60 fps. (#211). The Game
  World Update is triggered on each screen refresh, which defaults to 60fps.
  On systems with a higher refresh are, this value is no limited to a max of
  60fps to ensure games run at about the same speed.
