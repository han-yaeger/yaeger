package nl.meron.showcase.scenes.shapeentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.shapeentities.entities.DynamicRectangle;
import nl.meron.showcase.scenes.shapeentities.entities.SceneBorderTouchingDynamicTestEntity;
import nl.meron.showcase.scenes.shapeentities.entities.StaticRectangle;
import nl.meron.showcase.scenes.textentities.entities.SceneBorderTouchingDynamicTextEntity;
import nl.meron.yaeger.engine.entities.entity.Point;

public class ShapeEntitiesScene extends ShowCaseScene {

    private YaegerShowCase showCase;

    public ShapeEntitiesScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);

        var rect = new StaticRectangle(new Point(40, 60));
        addEntity(rect);

        var dynamicRectangle = new DynamicRectangle(new Point(40, 580));
        addEntity(dynamicRectangle);

        var borderTouchingDynamicTextEntity = new SceneBorderTouchingDynamicTestEntity(new Point(400, 200));
        addEntity(borderTouchingDynamicTextEntity);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
