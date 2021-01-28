# Entities aka Game Objects

Entities are what actually make a game. Anything that can be placed on a scene will
be an entity and will have to implement the abstract superclass `YaegerEntity`.

## Static or Dynamic Entities

Just as with scenes, also all entities are available in static and dynamic version.
The main difference is that a dynamic entity receives a Game World Update,
where a static does not.

In general static entities will be typically used for menu-items or non-moving things.
Dynamic entities are typically used for anything that should move around the scene, or
should have time-based behaviour in general.

## Different types of entities

### Shape Entities

### Sprite Entities

### Text Entities

### Composite Entities
