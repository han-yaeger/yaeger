package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.api.userinput.KeyListener;
import com.github.hanyaeger.core.YaegerConfig;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerFactory;
import com.github.hanyaeger.core.factories.animationtimer.AnimationTimerHandler;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyListenerDelegateTest {

    private KeyListenerDelegate sut;
    private Scene mockScene;
    private YaegerConfig mockConfig;
    private KeyListener mockKeyListener;
    private KeyEvent keyEvent;
    private AnimationTimerFactory animationTimerFactoryMock;
    private AnimationTimer animationTimerMock;

    private static final KeyCode KEYCODE = KeyCode.K;

    @BeforeEach
    void setup() {
        sut = new KeyListenerDelegate();
        mockScene = mock(Scene.class);
        mockConfig = mock(YaegerConfig.class);
        when(mockConfig.limitGWU()).thenReturn(false);
        mockKeyListener = mock(KeyListener.class);
        keyEvent = mock(KeyEvent.class);
        when(keyEvent.getCode()).thenReturn(KEYCODE);

        animationTimerFactoryMock = mock(AnimationTimerFactory.class);

        sut.setAnimationTimerFactory(animationTimerFactoryMock);
        animationTimerMock = mock(AnimationTimer.class);
        when(animationTimerFactoryMock.create(any(), eq(false))).thenReturn(animationTimerMock);
    }

    @AfterEach
    void teardown() {
        sut.tearDown(mockScene);
    }

    @Test
    void setupAttachesKeyPressedListenerToTheScene() {
        // Arrange

        // Act
        sut.setup(mockScene, null, mockConfig);

        // Verify
        verify(mockScene).setOnKeyPressed(any(EventHandler.class));
    }

    @Test
    void setupAttachesKeyReleasedListenerToTheScene() {
        // Arrange

        // Act
        sut.setup(mockScene, null, mockConfig);

        // Verify
        verify(mockScene).setOnKeyReleased(any(EventHandler.class));
    }

    @Test
    void tearDownRemovesListenersFomTheScene() {
        // Arrange
        sut.setup(mockScene, null, mockConfig);

        // Act
        sut.tearDown(mockScene);

        // Verify
        verify(mockScene).setOnKeyPressed(null);
    }

    @Test
    void tearDownStopsAnimationTimer() {
        // Arrange
        sut.setup(mockScene, null, mockConfig);

        // Act
        sut.tearDown(mockScene);

        // Verify
        verify(animationTimerMock).stop();
    }

    @Test
    void onKeyPressedFromSceneCallsOnInputChange() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KEYCODE);

        sut.setup(mockScene, mockKeyListener, mockConfig);

        ArgumentCaptor<EventHandler> captor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene, times(1)).setOnKeyPressed(captor.capture());

        // Act
        captor.getValue().handle(keyEvent);

        // Verify
        verify(mockKeyListener).onPressedKeysChange(input);
    }

    @Test
    void onKeyReleasedFromSceneCallsOnInputChange() {
        // Arrange
        var input = new HashSet<KeyCode>();
        input.add(KEYCODE);

        sut.setup(mockScene, mockKeyListener, mockConfig);

        ArgumentCaptor<EventHandler> captor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene, times(1)).setOnKeyReleased(captor.capture());

        // Act
        captor.getValue().handle(keyEvent);

        // Verify
        verify(mockKeyListener).onPressedKeysChange(new HashSet<>());
    }

    @Test
    void firstOnKeyPressedForwardedToListenerWhileNoNewEventReceived() {
        // Arrange
        var numberOfGWUsAfterFirstOnKeyPressed = 37;
        var input = new HashSet<KeyCode>();
        input.add(KEYCODE);

        sut.setup(mockScene, mockKeyListener, mockConfig);

        ArgumentCaptor<AnimationTimerHandler> animationTimerArgumentCaptor = ArgumentCaptor.forClass(AnimationTimerHandler.class);
        verify(animationTimerFactoryMock).create(animationTimerArgumentCaptor.capture(), eq(false));

        ArgumentCaptor<EventHandler> eventHandlerArgumentCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene, times(1)).setOnKeyPressed(eventHandlerArgumentCaptor.capture());

        // Act
        eventHandlerArgumentCaptor.getValue().handle(keyEvent);
        IntStream.range(0, numberOfGWUsAfterFirstOnKeyPressed).forEach(i -> animationTimerArgumentCaptor.getValue().handle(i));

        // Verify
        verify(mockKeyListener, times(numberOfGWUsAfterFirstOnKeyPressed + 1)).onPressedKeysChange(input);
    }

    @Test
    void firstOnKeyPressedForwardedToListenerUntilSameKeyPressedEventOccurs() {
        // Arrange
        var numberOfGWUsAfterFirstOnKeyPressed = 3;
        var input = new HashSet<KeyCode>();
        input.add(KEYCODE);

        sut.setup(mockScene, mockKeyListener, mockConfig);

        // Arrange the argumentcaptor for the AnimationTimer, so we can mock the GWU
        ArgumentCaptor<AnimationTimerHandler> animationTimerArgumentCaptor = ArgumentCaptor.forClass(AnimationTimerHandler.class);
        verify(animationTimerFactoryMock).create(animationTimerArgumentCaptor.capture(), eq(false));

        ArgumentCaptor<EventHandler> keyPressedCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene).setOnKeyPressed(keyPressedCaptor.capture());

        // Act
        keyPressedCaptor.getValue().handle(keyEvent);
        IntStream.range(0, numberOfGWUsAfterFirstOnKeyPressed).forEach(i -> animationTimerArgumentCaptor.getValue().handle(i));
        keyPressedCaptor.getValue().handle(keyEvent);
//        IntStream.range(0, numberOfGWUsAfterFirstOnKeyPressed).forEach(i -> animationTimerArgumentCaptor.getValue().handle(i));

        // Verify
        ArgumentCaptor<Set<KeyCode>> pressedKeyCaptor = ArgumentCaptor.forClass(Set.class);
        verify(mockKeyListener, times(numberOfGWUsAfterFirstOnKeyPressed + 2)).onPressedKeysChange(pressedKeyCaptor.capture());

        for (Set<KeyCode> actual : pressedKeyCaptor.getAllValues()) {
            assertEquals(actual, input);
        }

        verify(animationTimerMock, times(4)).start();
        verify(animationTimerMock, times(1)).stop();
    }

    @Test
    void firstOnKeyPressedForwardedToListenerUntilSameKeyReleasedEventOccurs() {
        // Arrange
        var numberOfGWUsAfterFirstOnKeyPressed = 3;
        var input = new HashSet<KeyCode>();
        input.add(KEYCODE);

        sut.setup(mockScene, mockKeyListener, mockConfig);

        // Arrange the argumentcaptor for the AnimationTimer, so we can mock the GWU
        ArgumentCaptor<AnimationTimerHandler> animationTimerArgumentCaptor = ArgumentCaptor.forClass(AnimationTimerHandler.class);
        verify(animationTimerFactoryMock).create(animationTimerArgumentCaptor.capture(), eq(false));

        // Arrange the keyPressedCaptor, so we can create KeyPressed Events
        ArgumentCaptor<EventHandler> keyPressedCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene).setOnKeyPressed(keyPressedCaptor.capture());

        // Arrange the keyPressedCaptor, so we can create KeyReleased Events
        ArgumentCaptor<EventHandler> keyReleasedCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(mockScene).setOnKeyReleased(keyReleasedCaptor.capture());

        // Act
        keyPressedCaptor.getValue().handle(keyEvent);
        IntStream.range(0, numberOfGWUsAfterFirstOnKeyPressed).forEach(i -> animationTimerArgumentCaptor.getValue().handle(i));
        keyReleasedCaptor.getValue().handle(keyEvent);
//        IntStream.range(0, numberOfGWUsAfterFirstOnKeyPressed).forEach(i -> animationTimerArgumentCaptor.getValue().handle(i));

        // Verify
        ArgumentCaptor<Set<KeyCode>> pressedKeyCaptor = ArgumentCaptor.forClass(Set.class);
        verify(mockKeyListener, times(numberOfGWUsAfterFirstOnKeyPressed + 2)).onPressedKeysChange(pressedKeyCaptor.capture());
        verify(animationTimerMock, times(4)).start();
        verify(animationTimerMock, times(1)).stop();
    }
}
