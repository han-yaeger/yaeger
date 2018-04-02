package nl.han.ica.waterworld;

import javafx.scene.Scene;
import nl.han.ica.yaeger.GameDimensions;
import nl.han.ica.yaeger.YaegerEngine;
import nl.han.ica.yaeger.resourceconsumer.audio.AudioFile;
import nl.han.ica.yaeger.resourceconsumer.audio.AudioPlayer;
import nl.han.ica.yaeger.resourceconsumer.audio.AudioPlayerMode;
import nl.han.ica.yaeger.resourceconsumer.audio.exceptions.AudioFileIsNullException;

public class Waterworld extends YaegerEngine {

    private static final String GAME_TITLE = "Waterworld";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public GameDimensions getGameDimensions() {
        return new GameDimensions(WATERWORLD_WIDTH, WATERWORLD_HEIGHT);
    }

    @Override
    public String getGameTitle() {
        return GAME_TITLE;
    }

    @Override
    protected void beforeStageIsShown(Scene scene) {
        var audioPlayer = new AudioPlayer(scene);
        AudioFile file = new AudioFile("Waterworld.mp3", AudioPlayerMode.LOOP);

        try {
            audioPlayer.playAudio(file);
        } catch (AudioFileIsNullException e) {
            e.printStackTrace();
        }
    }
}
