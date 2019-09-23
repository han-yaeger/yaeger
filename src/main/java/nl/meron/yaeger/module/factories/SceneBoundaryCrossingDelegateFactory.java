package nl.meron.yaeger.module.factories;

import com.google.inject.Singleton;
import nl.meron.yaeger.engine.entities.entity.SceneBoundaryCrosser;
import nl.meron.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate;
import nl.meron.yaeger.engine.entities.entity.SceneBoundaryCrosser;

/**
 * A {@link SceneBoundaryCrossingDelegateFactory} can be used to create instances of {@link SceneBoundaryCrossingDelegate}.
 */
@Singleton
public class SceneBoundaryCrossingDelegateFactory {

    /**
     * Create a {@link SceneBoundaryCrossingDelegate}.
     *
     * @param sceneBoundaryCrosser The {@link SceneBoundaryCrosser} that the {@link SceneBoundaryCrossingDelegate}
     *                             should check
     * @return An instance of {@link SceneBoundaryCrossingDelegate}
     */
    public SceneBoundaryCrossingDelegate create(SceneBoundaryCrosser sceneBoundaryCrosser) {
        return new SceneBoundaryCrossingDelegate(sceneBoundaryCrosser);
    }
}
