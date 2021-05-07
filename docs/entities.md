# Entities aka Game Objects

Entities are what actually make a game. Anything that can be placed on a scene will
be an Entity and will have to (indirectly) implement the abstract superclass `YaegerEntity`.

## Static or Dynamic Entities

Just as with scenes, entities are available in static and dynamic version.
The main difference is that a dynamic Entity receives a Game World Update,
where a static Entity does not.

In general static Entities will be typically used for menu-items or non-moving things.
Dynamic Entities are typically used for anything that should move around the Scene, or
should have time-based behaviour in general.

## Different types of entities

There are several Entities available, which can be divided over four different types:

| Static Entity     | Dynamic Entity            | Type              |
| :---------------- | :------------------------ | :---------------- |
| `SpriteEntity`    | `DynamicSpriteEntity`     | Sprite Entity     |
| `CircleEntity`    | `DynamicCircleEntity`     | Shape Entity      |
| `EllipseEntity`   | `DynamicEllipseEntity`    | Shape Entity      |
| `RectangleEntity` | `DynamicRectangleEntity`  | Shape Entity      |
| `TextEntity`      | `DynamicTextEntity`       | TextEntity        |
| `CompositeEntity` | `DynamicCompositeEntity`  | Composite Entity  |

### Sprite Entities

### Shape Entities

### Text Entities

### Composite Entities
