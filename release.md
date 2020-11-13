# Changes in this release

## Configuration

- Updated Guice to 1.0.19.13

## Refactor

- Made the `update()` method of a Dynamic Scene and Dynamic Entities final, so it cannot be overwritten by a user. If the update() is required, the UpdateExposer should be implemented.
- Renamed the methods with the semi-fluent API from the `MotionModifier` to their more classic form.
- Renamed the interfaces `AABBCollided`, `AABBCollider` and `AABBSideAwareCollided` to `Collided`, `Collider` and `SsideAwareCollided`. (#134).

## Features

- Added a `CompositeEntity` and a `DynamicCompositeEntity`, with which it becomes possible to apply composition to entities (#101). This also enables the option to create an Entity that uses hit boxes for collision detection.

## Bugfixes

- Resolved the problem that caused a performance hit when using TextEntities (#129).
- Resolved the issue where a colliding, but non-moving Entity stayed in place, although `setAnchorLocation()` was called (#122)
- Resolved an issue that prevented a CompositeEntity to be part of collision detection (#133).
