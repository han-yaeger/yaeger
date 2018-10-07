package nl.han.ica.yaeger.engine.collisions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Mockito.mock;

class CollisionDelegateTest {

    private CollisionDelegate collisionDelegate;

    @BeforeEach
    void setup() {
        collisionDelegate = new CollisionDelegate();
    }

    @Test
    void onlyCollidedGetsCollisionCheck() {
        // Setup
        Collided collided = mock(Collided.class);
        Collider collider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void collidableGetsCheckedIncludingItself() {
        // Setup
        Collidable collidable = mock(Collidable.class);
        Collider collider = mock(Collider.class);
        collisionDelegate.register(collidable);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collidable).checkForCollisions(argument.capture());
        Assertions.assertEquals(2, argument.getValue().size());
    }


}


