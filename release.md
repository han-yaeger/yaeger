## Changes in this release

### Configuration
- Updated JavaFX to 15.0.1

### Refactor
- Renamed the `setReferenceX(double)` and `setReferenceY(double)` to `setAnchorLocationX(double)` and 
`setAnchorLocationX(double)`. Also introduced a `setAnchorLocation(Coordinate2D)`.

### Features
- (#112) Yaeger can no be run with commandline arguments. At this point only the
  `-noSplash` is support, which skips the Splash Screen during startup.
- (#125) Added a getter for the opacity of each YaegerEntity.
- (#128) Added getters for all properties of YaegerEntities and its children.

### Bugfixes
- (#120) Fixed Yaeger ignoring calling setRotation() on an Entity, before init() was called.
- (#126) Fixed a NullPointer if a CircleEntity was created without a radius being set.
- (#107) Fixed a NullPointer if setCurrentFrameIndex() was called from the constructor of a SpriteEntity.
- (#104) Fixed an issue with the mouse pointer being reset to the default style whenever a new Entity
 was added to the Scene
- (#124) Fixed an issue where ShapeEntities that change their width/height during a movement, change their trajectory
- (#109) Made width/height of Entites available before init was called (see #128)