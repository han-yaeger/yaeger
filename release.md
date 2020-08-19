## Changes in this release

### Configuration
* Generated JavaDoc is now styled somewhat similar to the HAN style
* Updated JavaFX to 14.0.2.1 
* Updated Guice to 62
* Updated Mockito to 3.5.0

### Refactor
* Renamed the WithTimers interface to TimerContainer.
* Scenes no longer implement the Keylistener interface by default. This should be done explicitly.

### Features
* Scenes must now explicitly implement the KeyListener interface (#78)
* Added a Circle and an Ellipse Entity.
* Added the option to set the Brightness of a Scene.

### Bugfixes
* Added default implementations to the SpeedProvider and DirectionProvider interfaces (#84)
* Entities will not perform one extra step when setSpeed(0) is called.
