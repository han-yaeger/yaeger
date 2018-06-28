package nl.han.ica.waterworld;

import nl.han.ica.waterworld.gameobjects.BubbleSpawner;
import nl.han.ica.waterworld.gameobjects.Player;
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
    protected void beforeGameLoopIsCreated() {
        addInitialGameObjects();
        addBackgroundAudio();
    }


    private void addInitialGameObjects() {
        var swordFish = new Swordfish(200, 200);
        addGameObject(swordFish);

        var player = new Player(100, 100);
        addGameObject(player);
    }

    @Override
    public void afterStageIsShown() {
        BubbleSpawner spawner = new BubbleSpawner(WATERWORLD_WIDTH, WATERWORLD_HEIGHT);
        registerSpawner(spawner);
    }

    private void addBackgroundAudio() {
        Sound clip = new Sound("audio/waterworld.mp3", Sound.INDEFINITE);
        clip.play();
    }
}
