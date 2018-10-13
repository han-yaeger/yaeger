package nl.han.ica.yaeger.engine.repositories;

public class SpriteRepository {

    private static SpriteRepository spriteRepository;

    private SpriteRepository() {
    }

    /**
     * Return an instance of the {@code SpriteRepository}. Note that this is a Singleton.
     *
     * @return A Singleton instance of a {@code SpriteRepository}
     */
    public SpriteRepository getInstance() {
        if (spriteRepository == null) {
            spriteRepository = new SpriteRepository();
        }

        return spriteRepository;
    }
}
