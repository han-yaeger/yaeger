package nl.meron.yaeger.engine;

import nl.meron.yaeger.engine.entities.entity.Updatable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WithTimersTest {

    public static final long TIMESTAMP = 0l;
    private WithTimersImpl sut;

    @BeforeEach
    void setup() {
        sut = new WithTimersImpl();
    }

    @Test
    void initCallsRegisterTimers() {
        // Setup

        // Test
        sut.init();

        // Verify
        assertTrue(sut.isRegisterTimersCalled());
    }

    @Test
    void registerTimerAddTheTimerToTheTimers() {
        // Setup
        var timer = mock(Timer.class);

        // Test
        sut.registerTimer(timer);

        // Verify
        assertEquals(timer, sut.getTimers().get(0));
    }

    @Test
    void callTimersReturnsAnUpdatable() {
        // Setup

        // Test
        var updatable = sut.callTimers();

        // Verify
        assertTrue(updatable instanceof Updatable);
    }

    @Test
    void invokingTheUpdatableCallsHandleOnEachTimer() {
        // Setup
        var timer1 = mock(Timer.class);
        var timer2 = mock(Timer.class);
        sut.registerTimer(timer1);
        sut.registerTimer(timer2);
        var updatable = sut.callTimers();

        // Test
        updatable.update(TIMESTAMP);

        // Verify
        verify(timer1).handle(TIMESTAMP);
        verify(timer2).handle(TIMESTAMP);
    }

    private class WithTimersImpl implements WithTimers {

        private List<Timer> timers = new ArrayList<>();
        private boolean registerTimersCalled = false;

        @Override
        public void registerTimers() {
            registerTimersCalled = true;
        }

        @Override
        public List<Timer> getTimers() {
            return timers;
        }

        public boolean isRegisterTimersCalled() {
            return registerTimersCalled;
        }
    }
}
