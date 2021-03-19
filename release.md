# Changes in this release

## Configuration

## Refactor

- Removed `getLeftX()`, `getRightX()`, `getTopY()` and `getBottomY()` from the `Bounded` interface. These properties can
  be accessed through the `getBoundingBox()` method.
- Removed the undoUpdate behaviour that undid the last update if the speed of a `YaegerEntity` was set to 0. It led to
  unpredictable behaviour for corner cases.
- Removed the `SideAwareCollided`, which became absolute with the introduction of `CompositeEntity`.

## Features

- Added extra methods for using the `Direction` enumeration directly instead of callen `getValue()` (#135).
- Added the method `getCurrentFrameIndex()` to a `SpriteEntity` (#141).
- Added a new interface `Newtonian`, which add gravitational pull and friction to Dynamic Entities (#89).
- Added the run argument `--showBB` that makes Yaeger show the bounding box of each `YaegerEntity` that implements the
  interface `Collided` or `Collided`. This should ease debugging collisions (#20).
- All children of YaegerEntity can now be used for a TileMap. Previously, this was only possible with children of
  SpriteEntity (#150).
- It is now possible to add a third parameter while adding Entities to a TileMap. This parameter, of type
  `EntityConfiguration` will then be passed to the constructor of the Entity (#159).
- SpriteEntities now accepts sprites that contain a 2D map of images. For such sprites, it is now also possible the
  use `setAutoCycle(final long interval, final int row)`, which enables auto cycling through a specific row (#62).
- A new constructor has been added to SpriteEntities, which does not require a `Size`. For such entities the original
  dimensions are used (#158).
- Added several methods to dynamic entities that can be used to alter their motion:
    - `getSpeedInDirection(Direction)` and `getSpeedInDirection(double)` #171
    - `invertSpeedInDirection(Direction)` and `invertSpeedInDirection(double)` #173
    - `nullifySpeedInDirection(Direction)` and `nullifySpeedInDirection(double)` #163
    - `maximizeMotionForDirection(Direction, double)` and `maximizeMotionForDirection(double, double)` #161

## Bugfixes

- It is now possible to first set the direction of an Entity and then its speed (#41).
- Extra interface haven been removed from the `Collided` and `Collider`. To gain access to their properties (e.g. speed,
  direction), an explicit cast is now required (#148).
- Calculating the angle between an Entity and a CompositeEntity now calculates the correct angle (#160).
