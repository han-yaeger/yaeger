## Changes in this release

### Configuration

### Refactor

### Features
- (#112) Yaeger can no be run with commandline arguments. At this point only the
  `-noSplash` is support, which skips the Splash Screen during startup.
- (#125) Added a getter for the opacity of each YaegerEntity.

### Bugfixes
- (#120) Fixed Yaeger ignoring calling setRotation() on an Entity, before init() was called.
- (#126) Fixed a NullPointer if a CircleEntity was created without a radius being set.
- (#107) Fixed a NullPointer if setCurrentFrameIndex() was called from the constructor of a SpriteEntity.
- (#104) Fixed an issue with the mouse pointer being reset to the default style whenever a new Entity
 was added to the Scene