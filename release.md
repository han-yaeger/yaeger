# Changes in this release

This release is mainly focussed on updated the dependencies and fixing bugs.

## Configuration

* Updated Jacoco to 0.8.12
* Updated JavaFX to 23.0.1
* Updated testfx-code to 5.13.0
* Updated openjfx-monocle to 21.0.2
* Updated Guava to 33.3.1-jr so ensure Google Guice does not use the broken version
* Updated Mockito to 5.13.0

## Bugfixes

* Fixed two issues related to the bounding boxes in Composite Entities (#249, #268)
* Fixed #265 (getSpeedInDirection with same direction returns 0 instead of actual speed). Thanks to @Liam-Rougoor.
