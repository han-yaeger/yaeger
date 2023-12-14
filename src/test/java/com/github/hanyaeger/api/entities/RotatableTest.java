package com.github.hanyaeger.api.entities;

import com.github.hanyaeger.core.entities.motion.InitializationBuffer;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RotatableTest {

    public static final int DEGREES = 37;
    private RotatableImpl sut;
    private InitializationBuffer rotationBuffer;

    @BeforeEach
    void setup() {
        sut = new RotatableImpl();
        rotationBuffer = mock(InitializationBuffer.class);
        sut.setRotationBuffer(rotationBuffer);
    }

    @Nested
    class RotatableWithoutNodeSet {


        @BeforeEach
        void setup() {


        }

        @Test
        void setRotateDelegatesToRotationBufferIfNodeNotAvailable() {
            // Arrange

            // Act
            sut.setRotate(DEGREES);

            // Assert
            verify(rotationBuffer).setRotation(DEGREES);
        }

        @Test
        void getRotationDelegatesToRotationBufferIfNodeNotAvailable() {
            // Arrange

            // Act
            sut.getRotation();

            // Assert
            verify(rotationBuffer).getRotation();
        }

        @Test
        void getRotationFromRotationBufferIsAValueBetween0And360IfValueExceeds360() {
            // Arrange
            when(rotationBuffer.getRotation()).thenReturn(397D);

            // Act
            var actual = sut.getRotation();

            // Assert
            assertEquals(37D, actual);
        }

        @Test
        void getRotationFromRotationBufferIsAbsoluteValue() {
            // Arrange
            when(rotationBuffer.getRotation()).thenReturn(-397D);

            // Act
            var actual = sut.getRotation();

            // Assert
            assertEquals(37D, actual);
        }
    }

    @Nested
    class RotatableWithNodeSet {

        private Node node;

        @BeforeEach
        void setup() {
            node = mock(Node.class, withSettings().withoutAnnotations());

            sut.setNode(node);

            when(node.getRotate()).thenReturn(37D);
        }

        @Test
        void setRotateDelegatesToNode() {
            // Arrange

            // Act
            sut.setRotate(DEGREES);

            // Assert
            verify(node).setRotate(-DEGREES);
        }

        @Test
        void getRotationDelegatesToNode() {
            // Arrange

            // Act
            sut.getRotation();

            // Assert
            verify(node).getRotate();
        }

        @Test
        void getRotationFromRotationBufferIsAValueBetween0And360IfValueExceeds360() {
            // Arrange
            when(node.getRotate()).thenReturn(397D);

            // Act
            var actual = sut.getRotation();

            // Assert
            assertEquals(37D, actual);
        }

        @Test
        void getRotationFromRotationBufferIsAbsoluteValue() {
            // Arrange
            when(node.getRotate()).thenReturn(-397D);

            // Act
            var actual = sut.getRotation();

            // Assert
            assertEquals(37D, actual);
        }
    }

    private static class RotatableImpl implements Rotatable {

        private InitializationBuffer initializationBuffer;
        private Node node;

        @Override
        public Optional<? extends Node> getNode() {
            if (node != null) {
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
        }

        public void setNode(final Node node) {
            this.node = node;
        }

        @Override
        public InitializationBuffer getInitializationBuffer() {
            return initializationBuffer;
        }

        public void setRotationBuffer(final InitializationBuffer initializationBuffer) {
            this.initializationBuffer = initializationBuffer;
        }
    }

}
