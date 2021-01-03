# FAQ

This chapter will sum up known issues and solutions on how to bypass them. The list is likely not complete, so do feel
free to share your findings.

Some of the issues noted here can be seen as bugs, but that does not mean they are easily resolved.

## My MP3 file is not being played

For some reason, not all MP3 files are supported. If playing an MP3 file does not give any exceptions, but you can not
hear it in game, it might be because the MP3 file is nog supported. To correct this, use
[a tool](https://online-audio-converter.com/) to create an MP3 file that is supported.

## An Entity behaves strangely when colliding

Sometimes an Entity behaves unexpected when colliding. For instance, it is set to change its direction on collision, but
when this happens it doesn't immediately do so. First it seems to stutter a few times, after which it finally behaves as
expected.

This behaviour likely follows from the fact that the bounding box is used for collision detection, and the bounding box
is usually larger that the Entity we can see. Especially if the Entity is a SpriteEntity containing a round image, the
bounding box is still rectangular. When this SpriteEntity is also rotating, it is not unlikely that the angles of the
rectangular bounding box will again cause a collision on the Game World Update after the first collision. Because of the
second collision the Entity will again change its direction and this cycle can repeat itself until the Entity no longer
collides.

A workaround could be to use a `CompositeEntity` with a smaller hit box.
