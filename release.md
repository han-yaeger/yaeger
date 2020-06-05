## Changes in this release

### Configuration

### Refactor
- Renamed method initializeGame() to setupGame() (#79)
- Renamed class YaegerApplication to YaegerGame
- Renamed quitGame() to quit (#82)
- Set the visibillity of setActiveScene(int) to public (#81)
- Renamed Collider to AABBColider (#80)
- Renamed WithSpawners to EntitySpawning

### Features

### Bugfixes
- Fixed the faulty link in the Maven Central badge
- Fixed Yaeger crashing after the SplashScreen if no Scene was added (#77)
- Renamed internal resources folders to prevent name clashes (#75)
