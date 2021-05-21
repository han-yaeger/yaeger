# Entities aka Game Objects

Entities are what actually make a game. Anything that can be placed on a scene will be an Entity and will have to (
indirectly) implement the abstract superclass `YaegerEntity`.

## Static or Dynamic Entities

Just as with scenes, entities are available in static and dynamic version. The main difference is that a dynamic Entity
receives a Game World Update, where a static Entity does not.

In general static Entities will be typically used for menu-items or non-moving things. Dynamic Entities are typically
used for anything that should move around the Scene, or should have time-based behaviour in general.

## Different types of entities

There are several Entities available, which can be divided into four different types:

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

As can be expected, `TextEntities` are all about displaying a text on the Scene.

#### Using a build in font

#### Using a custom font

For using custom fonts, Yaeger provides the Class `CustomFont`, which requires a font file, and a font size. This font
file would be a file with the extension *ttf*, which should be placed in the `resources/` folder of your project. If you
place this file in a sub-folder of `resource/` do not forget to open it up through the module descriptor.

### Composite Entities
