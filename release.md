# Changes in this release

## Configuration

* Updated Mockito to 3.11.2
* Updated jUnit to 5.7.2

## Refactor

* Moved the implementations of all entities from their own package to the
  package `com.github.hanyaeger.api.entities.impl`.

## Features

* Allowed `Collideds` to support multi collisions (#209). A `YaegerEntity`
  that implements the interface `Collided` will now receive a list of all
  Entities (implementing `Collider`) that it collides with during the last
  Game World Update. Note that this feature
* breaks the API.
* Enabled a  `Coordinate2D` to calculate the `angleto(Coordinate2D)` method
  (#215).

## Bugfixes
