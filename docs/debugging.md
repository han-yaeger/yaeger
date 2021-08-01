# Debugging a game

Yaeger provides a debugging dialog that shows information about the current
scene, and the machine it is running on. Since this dialog requires the
Game World Update to refresh itself, it gives little information on a scene that
extends `StaticScene`. But on a scene that extends `DynamicScene` it can give
insight.

To enable the debugging dialog, start you Yaeger game with the command line
argument as shown in [Command line arguments](commandline-arguments.md).

## Processors and memory

When run, a Java-program allocates memory through the JVM, the Java Virtual
Machine, which is part of the Java Runtime Environment (JRE). Depending on the
number of Objects that make up the program, it then uses this memory. Since,
during the lifecycle of a program, more and more Objects are created, you can
see the *total used memory* increase. When some of these Objects are no longer
used by the program, the memory they use gets cleared by the JVM. Hence, the
*total used memory* periodically decreases.

If the *total used memory* reaches the value of *total allocated memory*, the
program will come to a grinding halt.

## Entities, suppliers and garbage

The debugger keeps track of the number of Entities that are present on the
scene.

| Entity   | Explanation                                        |
| :--------- | :------------------------------------------------- |
| **Dynamic Entities** | Number of entities that extend `DynamicEntity`  |
| **Static Entities**   | Number of  entities that extend `StaticEntity` |
| **Suppliers** | Number of objects that are able to supply entities to the scene. This means all instances of `EntitySpawner`, but also the Scene itself. |
| **Garbage**     | Number of Entities that have been marked as garbage, by calling the `remove()` method. The will be removed from the Scene during the next Game World Update, which should result in a drop of *total used memory*|
| **Key listening entities**| Number of entities that implement `KeyListener` |

## Loaded files

Image and audio files will probably make up most of Yaeger's memory usage. To
minimize this memory footprint, Yaeger reuses images if possible. Meaning,
for entities that use the same image file, this image file gets loaded only
once. The same goes for audio files. Because Yaeger tries to reclaim the
memory if such a file is no longer used, the background audio does get added to
the value in the debugger. As soon as it starts playing, Yaeger loses its
reference to the loaded file, and it continues playing.
