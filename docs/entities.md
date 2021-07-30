# Entities aka Game Objects

Entities are what actually make a game. Anything that can be placed on a scene will be an Entity and will have to (
indirectly) implement the abstract superclass `YaegerEntity`.

## Static or Dynamic Entities

Just as with scenes, entities are available in static and dynamic version. The main difference is that a dynamic Entity
receives a Game World Update, where a static Entity does not.

In general static Entities will be typically used for menu-items or non-moving things. Dynamic Entities are typically
used for anything that should move around the Scene, or should have time-based behaviour in general.

## Properties for all entities

Although there a different entities, they all share a basic set of properties. These
include the *hue*, *saturation*, *brightness*, *rotation* and many more. One of the more interesting
properties is the *viewOrder*, which can be used to influence the order in which the entities are
rendered on the scene. The lower the value, the closer the entity is placed to the front of the screen.

## Different types of entities

There are several Entities available, which can be divided into four different types:

| Static Entity     | Dynamic Entity            | Type              |
| :---------------- | :------------------------ | :---------------- |
| `SpriteEntity`    | `DynamicSpriteEntity`     | Sprite entity     |
| `CircleEntity`    | `DynamicCircleEntity`     | Shape entity      |
| `EllipseEntity`   | `DynamicEllipseEntity`    | Shape entity      |
| `RectangleEntity` | `DynamicRectangleEntity`  | Shape entity      |
| `TextEntity`      | `DynamicTextEntity`       | Text entity        |
| `CompositeEntity` | `DynamicCompositeEntity`  | Composite entity  |

The sprite, shape and text-entity are basic entities. The composite entity
is of a different type. It should be used whenever the entity should
actually consist of several smaller entities.

