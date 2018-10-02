package nl.han.ica.yaeger.module.debugger;

import javafx.scene.Group;
import nl.han.ica.yaeger.engine.debug.Debugger;

/**
 * A {@link DebuggerFactory} can be used to create instances of {@link Debugger}.
 */
public class DebuggerFactory {
    /**
     * Create a {@link Debugger}.
     *
     * @param group The {@link Group} for which the {@link Debugger} must be created.
     * @return an instance of {@link Debugger}
     */
    public Debugger create(Group group) {
        return new Debugger(group);
    }
}
