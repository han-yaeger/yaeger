package nl.meron.showcase.scenes.shapeentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.shapeentities.entities.*;
import nl.meron.yaeger.engine.entities.entity.Location;

public class ShapeEntitiesScene extends ShowCaseScene {

    private YaegerShowCase showCase;

    public ShapeEntitiesScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase, new Location(20, getHeight() - 30));
        addEntity(backButton);

        var rect = new StaticRectangle(new Location(40, 60));
        addEntity(rect);

        var dynamicRectangle = new DynamicRectangle(new Location(40, 160));
        addEntity(dynamicRectangle);

        var timedDynamicRectangle = new TimedDynamicRectangle(new Location(40, 260));
        addEntity(timedDynamicRectangle);

        var circle = new StaticCircle(new Location(150, 560));
        addEntity(circle);

        var ellipse = new StaticEllipse(new Location(400, 560));
        addEntity(ellipse);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
