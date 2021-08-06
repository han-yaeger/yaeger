# Creating a game

To create a new Yaeger Game, a Java class should be created that

* extends `YeagerGame`
* contains the following method:

```java
public static void main(String[]args){
    launch(args);
}
```

Such a game does not have any content yet, but it can be run and will only
show the Splash screen. This Splash screen  will be shown before each Yaeger
Game. It can, however, be disabled by adding a commandline argument when running
your game. More information on this can be found
[here](commandline-arguments.md).

## Lifecycle methods of `YeagerGame`

By extending `YeagerGame`, several methods will need to be implemented.
These methods are part of the lifecycle of a Yaeger Game and will be responsible
for setting up the content of the Game.

### Setting up the game, through `setupGame()`

The first method to implement is `setupGame()`, which has the following s
ignature:

```java
@Override
protected void setupGame(){
}
```

This method will be called first and must be used to set up the width/height
and title of the Game. A typical implementation can look like:

```java
@Override
protected void setupGame(){
    setGameTitle("Waterworld");
    setSize(new Size(800,600));
}
```

For more information, check the
[API](https://han-yaeger.github.io/yaeger/hanyaeger/com/github/hanyaeger/api/YaegerGame.html#setupGame())
of `setupGame()`

### Adding Scenes, through `setupScenes()`

After `setupGame()` has been called, Yaeger will call `setupScenes()`. This is
the second step in the life cycle of a Yaeger Game, in which it expects the
developer to add Scenes to the Game. Scenes can be added by calling
`addScene(int, YaegerScene)`. The first parameter is the `id` of the
`YaegerScene`, which can be used to select which `YaegerScene` will be shown.
By default, the first scene that is added, will be the first scene that is
shown.

A typical implementation can ook like:

```java
@Override
protected void setupScenes(){
    addScene(0, new TitleScene(this));
    addScene(1, new GameLevel(this));
    addScene(2, new GameOverScene(this));
}
```

## Loading a scene

When adding scenes to the Yaeger Game, the first added scene will also be the
one that gets loaded and becomes visible. To load a specific scene,
`YaegerGame` provides the method `setActiveScene(int)`. Read
the [API](https://han-yaeger.github.io/yaeger/hanyaeger/com/github/hanyaeger/api/YaegerGame.html#setActiveScene(int))
for the details on how to use this method.

## What about the constructor?

For Plain Old Java Objects (POJO's) the constructor is usually used for
configuring your Object. Although it is not always the best approach, since
the constructor should be only used for __constructing__ an Object, not for
__configuring__ it, it will usually still work.

With Yaeger, however, it will usually _not_ work. Configuring a `YaegerGame`
should only be done from the `setupGame()` method. Furthermore, the
constructor of the class that extends `YaegerGame` should be empty, since Yaeger
will otherwise not know how to make an instance of it.
