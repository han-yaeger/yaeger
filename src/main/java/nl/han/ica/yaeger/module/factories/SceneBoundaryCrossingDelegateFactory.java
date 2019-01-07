package nl.han.ica.yaeger.module.factories;

import com.google.inject.Singleton;
import nl.han.ica.yaeger.engine.entities.entity.SceneBoundaryCrosser;
import nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate;

/**
 * A {@link SceneBoundaryCrossingDelegateFactory} can be used to create instances of {@link nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate}.
 */
@Singleton
public class SceneBoundaryCrossingDelegateFactory {

    /**
     * Create a {@link nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate}.
     *
     * @param sceneBoundaryCrosser The {@link SceneBoundaryCrosser} that the {@link nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate}
     *                             should check
     * @return An instance of {@link nl.han.ica.yaeger.engine.entities.entity.sprites.delegates.SceneBoundaryCrossingDelegate}
     */
    public SceneBoundaryCrossingDelegate create(SceneBoundaryCrosser sceneBoundaryCrosser) {
        return new SceneBoundaryCrossingDelegate(sceneBoundaryCrosser);
    }
}
