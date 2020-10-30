## Changes in this release

### Configuration
- Updated Guice to 1.0.19.13

### Refactor
- Made the update() method of a Dynamic Scene and Dynamic Enitities final, so it cannot be
overwritten by an user. If the update() is required, the UpdateExposer should be implemented.

### Features

### Bugfixes
