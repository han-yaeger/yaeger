package nl.meron.showcase.scenes.startstoptimerscene;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.startstoptimerscene.timer.StartStopTimer;
import nl.meron.showcase.scenes.startstoptimerscene.Buttons.Start;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;

import java.awt.*;


public class StartStopTimerScene extends ShowCaseScene {

    private YaegerShowCase showCase;
    private TextEntity displayNumberText;
    private StartStopTimer s = new StartStopTimer();
    private int[] curTime;


    public StartStopTimerScene(YaegerShowCase showCase){
        this.showCase = showCase;
    }


    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    protected void setupSpawners() {

    }


    @Override
    public void setupEntities() {

        var backButton = new Back(showCase);
        addEntity(backButton);

        Start startButton = new Start(new Location(575, 625), "Start", s);
        startButton.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 50));
        startButton.setFill(Color.SNOW);
        addEntity(startButton);

        var explanationText = new TextEntity(new Location(getWidth() / 2, 150), "Click the start button to start timer.");
        explanationText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
        explanationText.setFill(Color.YELLOW);
        explanationText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(explanationText);

        displayNumberText = new TextEntity(new Location(getWidth() / 2, getHeight() / 2), "0:0:0:0");
        displayNumberText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 300));
        displayNumberText.setFill(Color.YELLOW);
        displayNumberText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(displayNumberText);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }

    public void update(){
        curTime = s.getTime();

        displayNumberText.setText(curTime[0] + ":" + curTime[1] + ":" + curTime[2] + ":" + curTime[3]);
    }

}
