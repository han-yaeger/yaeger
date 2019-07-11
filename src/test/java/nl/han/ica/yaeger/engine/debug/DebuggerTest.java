package nl.han.ica.yaeger.engine.debug;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import nl.han.ica.yaeger.engine.media.repositories.AudioRepository;
import nl.han.ica.yaeger.engine.media.repositories.ImageRepository;
import nl.han.ica.yaeger.javafx.debug.DebugGridPaneFactory;
import nl.han.ica.yaeger.javafx.debug.DebugLabelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DebuggerTest {

    private Group group;

    private ImageRepository imageRepository;
    private AudioRepository audioRepository;

    private GridPane gridPane;

    private DebugGridPaneFactory debugGridPaneFactory;
    private DebugLabelFactory debugLabelFactory;

    private Debugger sut;

    @BeforeEach
    void setup() {
        group = mock(Group.class);
        ObservableList<Node> children = mock(ObservableList.class);
        when(group.getChildren()).thenReturn(children);

        imageRepository = mock(ImageRepository.class);
        audioRepository = mock(AudioRepository.class);

        gridPane = mock(GridPane.class);
        debugGridPaneFactory = mock(DebugGridPaneFactory.class);
        when(debugGridPaneFactory.create()).thenReturn(gridPane);

        debugLabelFactory = mock(DebugLabelFactory.class);

        sut = new Debugger();
        sut.setAudioRepository(audioRepository);
        sut.setImageRepository(imageRepository);
        sut.setDebugGridPaneFactory(debugGridPaneFactory);
        sut.setDebugLabelFactory(debugLabelFactory);
    }

    @Test
    void setupCreatesAGridPane() {
        // Setup

        // Test
        sut.setup(group);

        // Verify
        verify(debugGridPaneFactory).create();
        verify(debugLabelFactory, atLeastOnce()).createLabel(anyString());
        verify(debugLabelFactory, atLeastOnce()).createValue(anyString());
    }

    @Test
    void toFrontSetsTheGridPaneToFront() {
        // Setup
        sut.setup(group);

        // Test
        sut.toFront();

        // Verify
        verify(gridPane).toFront();
    }

    @Test
    void toggleChangesVisibillityOfGridPane() {
        // Setup
        when(gridPane.isVisible()).thenReturn(true);
        sut.setup(group);

        // Test
        sut.toggle();

        // Verify
        verify(gridPane).setVisible(false);
    }
}
