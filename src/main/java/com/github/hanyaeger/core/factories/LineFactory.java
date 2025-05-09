package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.core.ViewOrders;
import com.google.inject.Singleton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * An {@code LineFactory} should be used for creating instance of {@link javafx.scene.shape.Line}.
 */
@Singleton
public class LineFactory {

    /**
     * @param startX      The x-coordinate at which the line should start
     * @param startY      The y-coordinate at which the line should start
     * @param endX        The x-coordinate at which the line should end
     * @param endY        The y-coordinate at which the line should end
     * @param strokeWidth the stroke width of the line
     * @param lineColor   the color of the line
     * @return A {@link Line} that confirms to the given parameters
     */
    public Line createLine(final double startX, final double startY, final double endX, final double endY, double strokeWidth, Color lineColor) {
        var line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(strokeWidth);
        line.setStroke(lineColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().addAll(4d, 2d);
        line.setStrokeDashOffset(0);
        line.setViewOrder(ViewOrders.VIEW_ORDER_COORDINATE_GRID);
        line.setMouseTransparent(true);

        return line;
    }
}
