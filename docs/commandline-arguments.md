# Command Line Arguments

When run, Yaeger accepts command line arguments. These arguments are primarily
meant for debugging your Game.

The following arguments are currently supported:

| Argument   | Explanation                                        |
| :--------- | :------------------------------------------------- |
| --noSplash | Skip the Splash screen during start up.                                                                |
| --showBB   | Show the BoundingBox of all instances of `YaegerEntity` that implement either `Collider` or `Collided`. |
| --showDebug | Show a debug window with information about the Scene. More information on the debug window can be found [here](debugging.md).|
| --enableScroll | Enable the scrolling gesture for all instances of `ScrollableDynamicScene`. This can help during the development process of such a scene. |
| --limitGWU | Limit the Game World Update (GWU) to a max of 60/sec. |
| --help     | Show this help screen with all commandline options. |

## Using command line arguments from an IDE

Since the command line arguments mainly focus on the development cycle, they
will likely only be used during development of a Yaeger Game. In such a case you
will be working in an IDE, which will also be responsible for creating a run
configuration. Part of such a run configuration will be the (extra) arguments
that can be passed to the application. So check the documentation of your IDE to
see where these arguments can be entered.
