package nl.meron.yaeger.javafx.animationtimer;

@FunctionalInterface
/**
 * A Functional Interface to be used for the default handler of a {@link javafx.animation.AnimationTimer}.
 */
public interface AnimationTimerHandler {
    /**
     * Called when a {@link javafx.animation.AnimationTimer} calls its internal {@code handle()}
     *
     * @param interval  the interval in milliseconds at which the {@link javafx.animation.AnimationTimer} should
     *                  call its internal {@code handle()}
     */
    void handle(long interval);
}
