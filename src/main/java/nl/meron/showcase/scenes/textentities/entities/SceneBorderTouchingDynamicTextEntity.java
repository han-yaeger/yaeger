package nl.meron.showcase.scenes.textentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderTouchingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.shapebased.text.DynamicTextEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class SceneBorderTouchingDynamicTextEntity extends DynamicTextEntity implements SceneBorderTouchingWatcher {

    public static final String TEXT = "SceneBorderTouching";

    public SceneBorderTouchingDynamicTextEntity(final Point position) {
        super(position, TEXT);
        setFill(Color.SILVER);
        setFont(Font.font("arial", FontWeight.BOLD, 30));
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setFill(Color.SILVER);
        } else {
            setFill(Color.TEAL);
        }
        changeDirectionBy(180);
    }

    @Override
    public void configure() {
        setMotionTo(6, Direction.RIGHT.getValue());
    }
}
