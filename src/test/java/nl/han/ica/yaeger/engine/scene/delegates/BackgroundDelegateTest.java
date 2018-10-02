package nl.han.ica.yaeger.engine.scene.delegates;

import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BackgroundDelegateTest {

    static final String IMAGE_FILE = "underwater2.jpg";
    static final String AUDIO_FILE = "Waterworld.mp3";

    private BackgroundDelegate backgroundDelegate;

    @BeforeEach
    void setup() {
        backgroundDelegate = new BackgroundDelegate();
    }

    @Test
    void settingBackgroundImageWorks() {
        // Setup

        // Test
        backgroundDelegate.setBackgroundImageUrl(IMAGE_FILE);

        // Verify
        Assertions.assertEquals(IMAGE_FILE, backgroundDelegate.backgroundImageUrl);
    }

    @Test
    void settingBackgroundAudioWorks() {
        // Setup

        // Test
        backgroundDelegate.setBackgroundAudio(AUDIO_FILE);

        // Verify
        Assertions.assertEquals(AUDIO_FILE, backgroundDelegate.backgroundAudioUrl);
    }

    @Test
    void callingTearDownDoesNotClearTheSetAudio() {
        // Setup
        Scene scene = Mockito.mock(Scene.class);
        backgroundDelegate.setBackgroundAudio(AUDIO_FILE);

        // Test
        backgroundDelegate.tearDown(scene);

        // Verify
        Assertions.assertEquals(AUDIO_FILE, backgroundDelegate.backgroundAudioUrl);
    }

    @Test
    void callingTearDownDoesClearTheCreatedAudioFile() {
        // Setup
        Scene scene = Mockito.mock(Scene.class);
        backgroundDelegate.setBackgroundAudio(AUDIO_FILE);

        // Test
        backgroundDelegate.tearDown(scene);

        // Verify
        Assertions.assertNull(backgroundDelegate.backgroundAudio);
    }
}
