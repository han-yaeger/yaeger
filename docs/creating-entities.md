# Creating entities

All available entities, expect the `TextEntity`, are abstract classes, meaning
they should be extended to create an instance. After creating a class, an
instance should be created, which can then be added to a `YaegerScene`, or a
`CompositeEntity` through the `addEntity(YaegerEntity)` method.

In general, the `setupEntities()` is the appropriate place to do such a
thing, but it is possible to call `addEntity(YaegerEntity)` from methods such
as `setupTimers()` (see [Timing things](timing-things.md)) or `setupSpawners()`
(see below).

## Spawning entities at a regular time-interval

Sometimes many entities should spawn within a scene. For instance, when enemies
spawn to life, or when it snows.

For such cases, Yaeger provides an `EntitySpawner`, which was designed for
specifically this case.
