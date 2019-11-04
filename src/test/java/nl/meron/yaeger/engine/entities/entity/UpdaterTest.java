package nl.meron.yaeger.engine.entities.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class UpdaterTest {

    private Updater sut;
    private Updatable updatable;
    private static final long TIMESTAMP = 0;

    @BeforeEach
    void setup() {
        sut = new Updater();
        updatable = mock(Updatable.class);
    }

    @Test
    void testFirstUpdatableIsCalledFirst() {
        // Setup
        var firstUpdatable = mock(Updatable.class);
        sut.addUpdatable(updatable);
        sut.addAsFirstUpdatable(firstUpdatable);

        // Test
        sut.update(TIMESTAMP);

        // Verify
        InOrder inOrder = inOrder(firstUpdatable, updatable);
        inOrder.verify(firstUpdatable).update(TIMESTAMP);
        inOrder.verify(updatable).update(TIMESTAMP);
    }

    @Test
    void testUpdaterDelegatesUpdateToAddedUpdatable() {
        // Setup
        sut.addUpdatable(updatable);

        // Test
        sut.update(TIMESTAMP);

        // Verify
        verify(updatable).update(TIMESTAMP);
    }
}
