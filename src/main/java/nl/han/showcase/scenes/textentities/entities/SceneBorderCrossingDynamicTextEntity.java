package nl.han.showcase.scenes.textentities.entities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.han.yaeger.engine.entities.entity.motion.Direction;
import nl.han.yaeger.engine.entities.entity.shape.text.DynamicTextEntity;
import nl.han.yaeger.engine.scenes.SceneBorder;

public class SceneBorderCrossingDynamicTextEntity extends DynamicTextEntity implements SceneBorderCrossingWatcher {

    public static final String TEXT = "SceneBorderCrossing";

    public SceneBorderCrossingDynamicTextEntity(final Location position) {
        super(position, TEXT);
        setFill(Color.TURQUOISE);
        setFont(Font.font("palatino", FontWeight.BOLD, 30));
        setMotionTo(4, Direction.LEFT.getValue());
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border.equals(SceneBorder.LEFT)) {
            setFill(Color.THISTLE);
        } else {
            setFill(Color.TURQUOISE);
        }

        changeDirectionBy(180);
    }
}
