## Changes in this release

### Configuration
- Updated Guice to 1.0.19.13

### Refactor
- Made the update() method of a Dynamic Scene and Dynamic Entities final, so it cannot be
overwritten by a user. If the update() is required, the UpdateExposer should be implemented.

### Features

### Bugfixes
- Resolved the problem that caused a performance hit when using TextEntities (#129).
