package nl.meron.showcase.scenes.shapeentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.shapeentities.entities.CircleEntity;
import nl.meron.showcase.scenes.shapeentities.entities.EllipseEntity;
import nl.meron.showcase.scenes.shapeentities.entities.RectangleEntity;
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

        var rect = new RectangleEntity(new Point(100, 100));
        addEntity(rect);

        var circle = new CircleEntity(new Point(400, 100));
        addEntity(circle);

        var ellipse = new EllipseEntity(new Point(600, 100));
        addEntity(ellipse);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
