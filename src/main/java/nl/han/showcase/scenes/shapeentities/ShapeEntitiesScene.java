package nl.han.showcase.scenes.shapeentities;

import nl.han.showcase.YaegerShowCase;
import nl.han.showcase.buttons.Back;
import nl.han.showcase.scenes.ShowCaseScene;
import nl.han.showcase.scenes.shapeentities.entities.DynamicRectangle;
import nl.han.showcase.scenes.shapeentities.entities.StaticRectangle;
import nl.han.showcase.scenes.shapeentities.entities.TimedDynamicRectangle;
import nl.han.yaeger.engine.entities.entity.Location;

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
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
