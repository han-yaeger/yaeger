## Changes in this release

### Configuration
* Generated JavaDoc is now styled somewhat similar to the HAN style
* Updated JavaFX to 14.0.2.1 
* Updated jUnit to 5.6.2
* Updated Mockito to 3.5.9
* Updated Jacoco to 0.8.5
* Updated JDK dependency to 14

### Refactor
* Renamed the WithTimers interface to TimerContainer.
* Renamed Location to Coordinate2D.
* Scenes no longer implement the Keylistener interface by default. This should be done explicitly.
* EntityCollections are now based on Lists, meaning the placement order will be maintained. 
Consequently, the toFront() and toBack() have been removed.

### Features
* Scenes must now explicitly implement the KeyListener interface (#78)
* Added a Circle and an Ellipse Entity.
* Added the option to set the Brightness of a Scene.
* Entities can now calculate the distance and angle to a given different Entity

### Bugfixes
* Added default implementations to the SpeedProvider and DirectionProvider interfaces (#84)
* Entities will not perform one extra step when setSpeed(0) is called.
* Entities from an EntityMap no longer have a small gap between them.
