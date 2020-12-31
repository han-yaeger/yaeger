# Changes in this release

## Configuration

## Refactor

- Removed `getLeftX()`, `getRightX()`, `getTopY()` and `getBottomY()` from the `Bounded` interface. These
properties can be accessed through the `getBoundingBox()` method.

## Features

- Added extra methods for using the `Direction` enumeration directly instead of callen `getValue()` (#135).
- Added the method `getCurrentFrameIndex()` to a `SpriteEntity` (#141).
- Added a new interface `Newtonian`, which add gravitational pull and friction to Dynamic Entities (#89).
- Added the run argument `--showBB` that makes Yaeger show the bounding box of each `YaegerEntity` that implements the interface `Collided` or `Collided`. This should ease debugging with collisions (#20).

## Bugfixes

- It is now possible to first set the direction of an Entity and then its speed (#41).
