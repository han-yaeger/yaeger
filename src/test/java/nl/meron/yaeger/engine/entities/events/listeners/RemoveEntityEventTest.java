package nl.meron.yaeger.engine.entities.events.listeners;

import nl.meron.yaeger.engine.entities.entity.Removeable;
import nl.meron.yaeger.engine.entities.entity.Removeable;
import nl.meron.yaeger.engine.entities.events.RemoveEntityEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RemoveEntityEventTest {

    @Test
    void correctSourceSet() {
        // Setup
        var removeable = Mockito.mock(Removeable.class);
        var event = new RemoveEntityEvent(removeable);

        // Test
        var source = event.getSource();

        // Verify
        Assertions.assertSame(removeable, source);
    }
}
