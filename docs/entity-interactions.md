# Entity collisions

Entities can interact with each other by using collision detection.
Collision detection is Yaeger is fairly simple. Each GWU checks if all
entities that implement `Collider` intersect with each entity that implements
`Collided`. If an intersection occurs, the method `onCollision
(List<Collider>)` is called on the entity that implements `Collided`.

Because collision detection is performed on each GWU, which has a discreet
value of 60 times/second, if entities move at a high speed, it is possible that
collision detection misses their collision. This is currently a limitation
of Yaeger.

## Entities leaving the scene

Do be notified whenever an entity leaves the scene border, two interfaces
are available:

* `SceneBorderCrossingWatcher`
* `SceneBorderTouchingWatcher`

Their event handler will be called whenever an entity respectively leaves
the scene border or merely touches the scene border. As with collision
detection, also this checks is performed only on the GWU, meaning it will
not be extremely accurate with fast moving entities.
