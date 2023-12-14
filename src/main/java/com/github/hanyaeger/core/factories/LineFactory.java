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

    public Line createLine(final double startX, final double startY, final double endX, final double endY, double strokeWidth, Color gridColor) {
        var line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(strokeWidth);
        line.setStroke(gridColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().addAll(4d, 2d);
        line.setStrokeDashOffset(0);
        line.setViewOrder(ViewOrders.VIEW_ORDER_COORDINATE_GRID);
        line.setMouseTransparent(true);

        return line;
    }
}
