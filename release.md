# Changes in this release

## Configuration

## Refactor

- Removed `getLeftX()`, `getRightX()`, `getTopY()` and `getBottomY()` from the `Bounded` interface. These
properties can be accessed through the `getBoundingBox()` method.
- Removed the undoUpdate behaviour that undid the last update if the speed of a `YaegerEntity` was
set to 0. It led to unpredictable behaviour for corner cases.
- Removed the `SideAwareCollided`, which became absolute with the introduction of `CompositeEntity`.

## Features

- Added extra methods for using the `Direction` enumeration directly instead of callen `getValue()` (#135).
- Added the method `getCurrentFrameIndex()` to a `SpriteEntity` (#141).
- Added a new interface `Newtonian`, which add gravitational pull and friction to Dynamic Entities (#89).
- Added the run argument `--showBB` that makes Yaeger show the bounding box of each `YaegerEntity` that implements the interface `Collided` or `Collided`. This should ease debugging with collisions (#20).

## Bugfixes

- It is now possible to first set the direction of an Entity and then its speed (#41).
- Extra interface haven been removed from the `Collided` and `Collider`. The gain access to
their properties (e.g. speed, direction), an explicit cast is now required (#148).
