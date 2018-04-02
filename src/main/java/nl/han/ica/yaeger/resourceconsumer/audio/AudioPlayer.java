package nl.han.ica.yaeger.resourceconsumer.audio;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nl.han.ica.yaeger.resourceconsumer.ResourceConsumer;
import nl.han.ica.yaeger.resourceconsumer.audio.exceptions.AudioFileIsNullException;

/**
 *
 */
public class AudioPlayer extends ResourceConsumer {

    private final Scene scene;

    /**
     * @param scene
     */
    public AudioPlayer(Scene scene) {
        this.scene = scene;
    }

    /**
     * This method can be used to play an AudioFile. If this AudioFile should
     * be played more that once, the returned {@link AudioPlayer} can be used. In that
     * case use the play() method on the returned Object.
     *
     * @param audioFile The AudioFile that should be played
     * @return The mediaPlayer created for this AudioFile
     */
    public MediaPlayer playAudio(AudioFile audioFile) throws AudioFileIsNullException {

        if (audioFile == null) {
            throw new AudioFileIsNullException();
        }
        if (audioFile.getFile() == null) {
            throw new AudioFileIsNullException();
        }


        String path = createPathForResource(audioFile.getFile());
        var media = new Media(path);
        var mediaPlayer = new MediaPlayer(media);

        if (AudioPlayerMode.LOOP.equals(audioFile.getMode())) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }

        mediaPlayer.setAutoPlay(true);
        var mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);
        ((Group) scene.getRoot()).getChildren().add(mediaView);

        return mediaPlayer;
    }
}
