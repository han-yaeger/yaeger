# User Input

A Player can interact with both Entities and Scenes by using the keyboard or mouse. To enable this interaction the Scene
or Entity should implement the appropriate interface, after which an event handler should be implemented.

## Available interactions

The table below gives the full list of interfaces that are available. They can be found in package
`com.github.hanyaeger.api.userinput` and most can be applied to all children of both `YaegerEntity`
and `YaegerScene`, only the `MouseDraggedListener` can only be applied to children of `YaegerEntity`.

| Interface                         | EventHandler(s)                                            |
| :-------------------------------- | :-------------------------------------------------------- |
| `KeyListener`                     | `void onPressedKeysChange(Set<KeyCode>)`                  |                                                    |
| `MouseButtonPressedListener`      | `void onMouseButtonPressed(MouseButton, Coordinate2D)`    |
| `MouseButtonReleasedListener`     | `void onMouseButtonReleased(MouseButton, Coordinate2D)`   |
| `MouseEnterListener`              | `void onMouseEntered()`                                   |
| `MouseExitListener`               | `void onMouseExited()`                                    |
| `MouseMovedListener`              | `void onMouseMoved(Coordinate2D)`                         |
| `MouseMovedWhileDraggingListener` | `void onMouseMovedWhileDragging(Coordinate2D)`            |
| `MouseDraggedListener`            | `void onMouseDragged(Coordinate2D)`, `void onDropped(Coordinate2D)` |
| `MouseDragEnterListener`          | `void onDragEntered(Coordinate2D, MouseDraggedListener)`  |
| `MouseDragExitListener`           | `void onDragExited(Coordinate2D, MouseDraggedListener)`   |
| `MouseDropListener`               | `void onDrop(Coordinate2D, MouseDraggedListener)`         |
