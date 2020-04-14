package nl.meron.yaeger.engine.scenes;

import javafx.scene.input.MouseEvent;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.mouseevents.MouseEventsScene;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MouseEventsSceneTest {
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
        sut.onMousePressed(null, test_x, test_y);
        var cmp_x = new TextEntity(new Location(820, 625), Double.toString(test_x));
        var cmp_y = new TextEntity(new Location(920, 625), Double.toString(test_y));
        Assertions.assertEquals(sut.x_val.getText(), cmp_x.getText());
        Assertions.assertEquals(sut.y_val.getText(), cmp_y.getText());
    }

    private class TestMouseEventsScene extends MouseEventsScene {

        public TestMouseEventsScene(YaegerShowCase showCase) {
            super(showCase);
        }

    }
}
