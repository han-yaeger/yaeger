package nl.meron.showcase.scenes.textentitiesscene.entities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.scenes.textentitiesscene.TextEntitiesScene;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.SceneBorderCrossingWatcher;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;
import nl.meron.yaeger.engine.entities.entity.text.DynamicTextEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;

public class SceneBorderCrossingDynamicTextEntity extends DynamicTextEntity implements SceneBorderCrossingWatcher {

    public static final String TEXT = "SceneBorderCrossing";

    public SceneBorderCrossingDynamicTextEntity(final Point position) {
        super(position, TEXT);
        setFill(Color.TURQUOISE);
        setFont(Font.font(TextEntitiesScene.FONT, FontWeight.BOLD, 30));
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

    @Override
    public void configure() {
        setMotionTo(4, Direction.LEFT.getValue());
    }
}
