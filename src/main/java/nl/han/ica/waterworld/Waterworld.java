package nl.han.ica.waterworld;

import nl.han.ica.waterworld.gameobjects.Bubble;
import nl.han.ica.waterworld.gameobjects.Swordfish;
import nl.han.ica.yaeger.YaegerEngine;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public class Waterworld extends YaegerEngine {

    private static final String GAME_TITLE = "Waterworld";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void beforeStageIsCreated() {
        setGameDimensions(new GameDimensions(WATERWORLD_WIDTH, WATERWORLD_HEIGHT));
        setGameTitle(GAME_TITLE);
    }

    @Override
    protected void beforeStageIsShown() {
        addInitialGameObjects();
        addBackgroundAudio();

    }

    private void addInitialGameObjects() {
        var swordFish = new Swordfish(200, 200);
        addGameObject(swordFish);

        var bubble = new Bubble(600, 403);
        addGameObject(bubble);
    }

    private void addBackgroundAudio() {
        var clip = new Sound("Waterworld.mp3", Sound.INDEFINITE);
        clip.play();
    }
}
