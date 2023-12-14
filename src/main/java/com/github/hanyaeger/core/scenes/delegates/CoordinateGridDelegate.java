package com.github.hanyaeger.core.scenes.delegates;

import com.github.hanyaeger.api.scenes.YaegerScene;
import com.github.hanyaeger.core.Destroyable;
import com.github.hanyaeger.core.factories.LineFactory;
import com.github.hanyaeger.core.factories.TextFactory;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CoordinateGridDelegate} follows the Delegate pattern and embraces Composition over Inheritance.
 * It can be used to add a coordinate-grid to the background of a {@link YaegerScene}.
 */
public class CoordinateGridDelegate implements Destroyable {

    private static final Color gridColor = Color.LIGHTBLUE;
    public static final int LINE_INTERVAL = 5;
    private static final int BOLD_LINE_INTERVAL = 10;
    public static final Color TEXT_COLOR = Color.BLUE;
    private final List<Node> gridElements = new ArrayList<>();
    private Pane pane;
    private LineFactory lineFactory;
    private TextFactory textFactory;

    /**
     * Set up the {@link Pane} belonging to this  {@link CoordinateGridDelegate}.
     *
     * @param pane The {@link Pane} that should be used when setup.
     */
    public void setup(final Pane pane) {
        this.pane = pane;

    }

    /**
     * Use this method to trigger behaviour that should be set after the {@code CoordinateGridDelegate} has been completely
     * set up.
     */
    public void postActivation() {

        // Create horizontal lines
        for (int i = 0; i < pane.getHeight(); i = i + LINE_INTERVAL) {
            drawLine(0, i, pane.getWidth(), i, calculateLineWidth(i));

            if (i % (BOLD_LINE_INTERVAL * LINE_INTERVAL) == 0) {
                drawCoordinateText(LINE_INTERVAL, i, i, -2, 7);
            }
        }

        // Create vertical lines
        for (int i = 0; i < pane.getWidth(); i = i + LINE_INTERVAL) {
            drawLine(i, 0, i, pane.getHeight(), calculateLineWidth(i));

            if (i % (BOLD_LINE_INTERVAL * LINE_INTERVAL) == 0) {
                drawCoordinateText(i, LINE_INTERVAL, i, 3, 1);
            }
        }
    }

    private void drawCoordinateText(final int x, final int y, final int value, final int deltaX, final int deltaY) {
        var text = textFactory.createText(Integer.toString(value), TEXT_COLOR);
        text.setLayoutX((double) x + deltaX);
        text.setLayoutY((double) y + deltaY);
        pane.getChildren().add(text);
        gridElements.add(text);
    }

    private double calculateLineWidth(final int i) {
        return i % (10 * LINE_INTERVAL) == 0 ? 1 : 0.1;
    }

    private void drawLine(final double startX, final double startY, final double endX, final double endY, final double strokeWidth) {
        var line = lineFactory.createLine(startX, startY, endX, endY, strokeWidth, gridColor);

        pane.getChildren().add(line);
        gridElements.add(line);
    }

    @Override
    public void destroy() {
        for (var node : gridElements) {
            pane.getChildren().remove(node);
        }
        gridElements.clear();
    }

    @Inject
    public void setLineFactory(final LineFactory lineFactory) {
        this.lineFactory = lineFactory;
    }

    @Inject
    public void setTextFactory(final TextFactory textFactory) {
        this.textFactory = textFactory;
    }
}
