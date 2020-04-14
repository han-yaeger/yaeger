package nl.meron.yaeger.engine.scenes;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.mouseevents.MouseEventsScene;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MouseEventsSceneTest {

    private double x_coordinate = 0;
    private double y_coordinate = 0;
    private String x_val = Double.toString(x_coordinate);
    private String y_val = Double.toString(y_coordinate);
    private String cmp_x_val = Double.toString(50.0);
    private String cmp_y_val = Double.toString(100.0);
    private YaegerShowCase showCase;
    private TestMouseEventsScene sut;

    @BeforeEach
    void setup() {
        sut = new TestMouseEventsScene(showCase);
    }

    @Test
    void testOnMousePressed() {
        MouseEvent event = null;
        double test_x = 50.0;
        double test_y = 100.0;
        var sut = new TestMouseEventsScene(showCase);
        sut.onMousePressed(event, test_x, test_y);
        Assertions.assertEquals(x_val, cmp_x_val);
        Assertions.assertEquals(y_val, cmp_y_val);
    }

    private class TestMouseEventsScene extends MouseEventsScene {

        public TestMouseEventsScene(YaegerShowCase showCase) {
            super(showCase);
        }

        @Override
        public void onMousePressed(MouseEvent event, Double xCoordinates, Double yCoordinates) {
            x_val = (Double.toString(xCoordinates));
            y_val = (Double.toString(yCoordinates));
        }

    }
}
