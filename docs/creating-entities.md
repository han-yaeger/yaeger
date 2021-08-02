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

## Creating many entities at once, using a `TileMap`

When many entities populate the scene, a `TileMap` can be used to easily
create them and to let Yaeger calculate the exact location where they should
be placed.

Imagine the image below should be used multiple times on the scene:

![A tile](images/tile.png),

and should be placed in such a way that we get the following scene:

![A scene with tiles](images/tile-scene.png).

A `TileMap` facilitates this, by defining a two-dimensional array that
represents the scene and stating which entities should be used. The
`TileMap` will then calculate the location and size of each entity,
instantiate them and add them to the scene.

To be able to use a `TileMap`, the scene should implement the interface
`TileMapContainer`.
