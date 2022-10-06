# Scenes aka levels

After scenes have been added, Yaeger will load the first one. In this chapter we
will dive deeper into them.

## What are scenes?

A game is usually comprised of different 'parts', such as levels, menu's or a
game-over screen. Some of those are just simple images and other are complex
levels, with lots of behaviour.

In Yaeger, all of them implement `YaegerScene` and will be referred to as a
Yaeger scene. For a simple Yaeger scene, without any moving parts, Yaeger
provides the abstract class `StaticScene`. In case of a complex scene, with
moving parts and a so-called Game World Update, Yaeger provides a
`DynamicScene`. Both can be added to Yaeger, since they both implement
`YaegerScene`. Their behaviour is, however, quite different.

### `StaticScene`

A `StaticScene` is a scene that is not aware of time. It can contain anything
you like, and those things will listen to user interaction, but their behaviour
will not be based on the concept of time. Typical use cases are:

* A Menu
* A Game Over scene
* An inventory selection scene

### `DynamicScene`

A `DynamicScene` is exactly the same as a `StaticScene`, but it is also aware of
time. A `DynamicScene` contains a Game World Update, that calls a
`update(long)` method on the scene itself and all dynamic parts of the scene.
This way, it is possible to create movement and add time-based behaviour.
Typical use cases are:

* A level for a game
* A Splash screen
* A scene with any form of finiteAnimation

### `ScrollableDynamicScene`

A `ScrollableDynamicScene` is exactly the same as a `DynamicScene`, but allows a
different width/height for its content. This way the scene can be much larger
that the viewable area (the viewport). The with and height of the scene can be
set from the `setupScene()` and the part of the scene that is visible can be set
by setting the scroll-position.

## Creating a scene

To create either a `StaticScene` or `DynamicScene`, let your own scene extend
one of those. As when extending `YaegerGame`, again two methods will need to be
implemented:

* `setupScene()`, which should be used for setting all the properties of the
  scene
* `setupEntities()`, which should be used for adding various entities (Game
  Objects) to the Scene

### Setting up the scene, through `setupScene()`

The first method to implement is `setupScene()`, which has the following
signature:

```java
@Override
protected void setupScene(){
        }
```

This method will be called first and must be used to set up the background
image, background color and background audio of the scene.

A typical implementation can look like:

```java
@Override
public void setupScene(){
        setBackgroundAudio("audio/ocean.mp3");
        setBackgroundImage("backgrounds/background1.jpg");
        }
```

For more information, check the
[API](https://han-yaeger.github.io/yaeger/hanyaeger/com/github/hanyaeger/api/scenes/YaegerScene.html#setupScene())

### Adding entities to the scene, through `setupEntities()`

After `setupScene()` has been called, Yaeger will call `setupEntities()`. This
is the second step in the life cycle of a `YaegerScene`, in which it expects the
developer to add entities to the scene.

Entities can be added by calling `addEntity(YaegerEntity)`. Since entities can
overlap, their placement will follow the order in which they are added to the
scene. So newly added entities may overlap entities that had been placed
earlier.

More on entities in the following chapter of this documentation.

A typical implementation can look like:

```java
@Override
public void setupEntities(){
        addEntity(new Hanny(new Coordinate2D(1,1),waterworld));
        addEntity(new Swordfish(new Coordinate2D(200,300)));
        addEntity(new Sharky(new Coordinate2D(0,100)));
        }
```

## What about the constructor?

The same rules apply to a `YaegerScene`, as do to a `YaegerGame`. Configuring
the scene should only be done from the `setupScene()` method.

It is however possible to use the constructor for basic wiring of objects or
other commons object-oriented practices.
