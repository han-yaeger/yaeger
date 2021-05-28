# User Input

A Player can interact with both Entities and Scenes by using the keyboard or mouse. To enable this interaction the Scene
or Entity should implement the appropriate interface, after which an event handler should be implemented.

## Available interactions

The table below gives the full list of interfaces that are available. They can be applied to all children of both
`YaegerEntity` and `YaegerScene`.

| Interface                     | EventHandler                                        |
| :---------------------------- | :------------------------------------------------- |
| `KeyListener`                 | `void onPressedKeysChange(Set<KeyCode>)`          |                                                    |
| `MouseButtonPressedListener`  | `void onMouseButtonPressed(MouseButton, Coordinate2D)` |
| `MouseButtonReleasedListener` | `void onMouseButtonReleased(MouseButton, Coordinate2D)`|
| `MouseEnterListener`          | `void onMouseEntered()` |
| `MouseExitListener`           | `void onMouseExited()`|
| `MouseMovedListener`          | `void onMouseMoved(Coordinate2D)` |
| `MouseDraggedListener`        | `void onMouseDragged(Coordinate2D)` |
