package com.github.hanyaeger.core.factories;

import com.github.hanyaeger.core.ViewOrders;
import com.google.inject.Singleton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * An {@code LineFactory} should be used for creating instance of {@link javafx.scene.shape.Line}.
 */
@Singleton
public class TextFactory {

    public Text createText(final String textValue, final Color textColor) {
        var text = new Text(textValue);
        text.setStroke(textColor);
        text.setFont(Font.font("roboto", FontWeight.EXTRA_LIGHT, 6));
        text.setViewOrder(ViewOrders.VIEW_ORDER_COORDINATE_GRID);
        text.setMouseTransparent(true);

        return text;
    }
}
