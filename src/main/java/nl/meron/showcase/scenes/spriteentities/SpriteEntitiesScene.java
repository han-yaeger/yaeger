package nl.meron.showcase.scenes.spriteentities;

import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.spriteentities.entities.BasketBall;
import nl.meron.showcase.scenes.spriteentities.entities.GolfBall;
import nl.meron.showcase.scenes.spriteentities.entities.RugbyBall;
import nl.meron.showcase.scenes.spriteentities.entities.TennisBall;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.motion.Direction;


public class SpriteEntitiesScene extends ShowCaseScene {

    private YaegerShowCase showCase;

    public SpriteEntitiesScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    protected void setupSpawners() {

    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/landscape.jpg");
    }

    @Override
    public void setupEntities() {

        var backButton = new Back(showCase);
        addEntity(backButton);

        placeRugbyBallOnWithAllAnchorPoints();

        var tennisBall = new TennisBall(new Location(100, 120));
        addEntity(tennisBall);

        var basketBall = new BasketBall(new Location(100, 220));
        addEntity(basketBall);


        var golfBall1 = new GolfBall(new Location(getWidth() / 2, 420), 4, Direction.RIGHT.getValue());
        golfBall1.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(golfBall1);

        var golfBall2 = new GolfBall(new Location(20, 420), 5, Direction.LEFT.getValue());
        golfBall2.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(golfBall2);

        var golfBall3 = new GolfBall(new Location(getWidth() - 20, 420), 6, Direction.RIGHT.getValue());
        golfBall3.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(golfBall3);
    }

    private void placeRugbyBallOnWithAllAnchorPoints() {
        var rugbyBallTL = new RugbyBall(new Location(0, 0));
        addEntity(rugbyBallTL);

        var rugbyBallTC = new RugbyBall(new Location(getWidth() / 2, 0));
        rugbyBallTC.setAnchorPoint(AnchorPoint.TOP_CENTER);
        addEntity(rugbyBallTC);

        var rugbyBallTR = new RugbyBall(new Location(getWidth(), 0));
        rugbyBallTR.setAnchorPoint(AnchorPoint.TOP_RIGHT);
        addEntity(rugbyBallTR);

        var rugbyBallLC = new RugbyBall(new Location(0, getHeight() / 2));
        rugbyBallLC.setAnchorPoint(AnchorPoint.LEFT_CENTER);
        addEntity(rugbyBallLC);

        var rugbyBallCC = new RugbyBall(new Location(getWidth() / 2, getHeight() / 2));
        rugbyBallCC.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(rugbyBallCC);

        var rugbyBallRC = new RugbyBall(new Location(getWidth(), getHeight() / 2));
        rugbyBallRC.setAnchorPoint(AnchorPoint.RIGHT_CENTER);
        addEntity(rugbyBallRC);

        var rugbyBallBL = new RugbyBall(new Location(0, getHeight()));
        rugbyBallBL.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);
        addEntity(rugbyBallBL);

        var rugbyBallBC = new RugbyBall(new Location(getWidth() / 2, getHeight()));
        rugbyBallBC.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);
        addEntity(rugbyBallBC);

        var rugbyBallBR = new RugbyBall(new Location(getWidth(), getHeight()));
        rugbyBallBR.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);
        addEntity(rugbyBallBR);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
