package nl.meron.yaeger.module.factories;

import com.google.inject.Singleton;
import nl.meron.yaeger.engine.entities.entity.updatetasks.SceneBoundaryCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate;

/**
 * A {@link SceneBoundaryCrossingDelegateFactory} can be used to create instances of {@link SceneBoundaryCrossingDelegate}.
 */
@Singleton
public class SceneBoundaryCrossingDelegateFactory {

    /**
     * Create a {@link SceneBoundaryCrossingDelegate}.
     *
     * @param sceneBoundaryCrossingWatcher The {@link SceneBoundaryCrossingWatcher} that the {@link SceneBoundaryCrossingDelegate}
     *                             should check
     * @return An instance of {@link SceneBoundaryCrossingDelegate}
     */
    public SceneBoundaryCrossingDelegate create(SceneBoundaryCrossingWatcher sceneBoundaryCrossingWatcher) {
        return new SceneBoundaryCrossingDelegate(sceneBoundaryCrossingWatcher);
    }
}
