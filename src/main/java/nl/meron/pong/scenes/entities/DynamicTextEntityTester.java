package nl.meron.pong.scenes.entities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.text.DynamicTextEntity;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class DynamicTextEntityTester extends DynamicTextEntity implements SceneBorderCrossingWatcher {

    public static final String TEST = "Test";

    public DynamicTextEntityTester() {
        super(new Point(400, 30), TEST);
        setFill(Color.LIGHTGREEN);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        changeDirection(180);
    }

    @Override
    public void configure() {
        setMotion(6, Direction.RIGHT.getValue());
    }
}
