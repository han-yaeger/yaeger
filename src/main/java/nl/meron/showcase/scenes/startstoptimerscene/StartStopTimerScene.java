package nl.meron.showcase.scenes.startstoptimerscene;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.showcase.buttons.Button;
import nl.meron.showcase.scenes.ShowCaseScene;
import nl.meron.showcase.scenes.startstoptimerscene.timer.StartStopTimer;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;

public class StartStopTimerScene extends ShowCaseScene {

    private YaegerShowCase showCase;


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

        var explanationText = new TextEntity(new Location(getWidth() / 2, 150), "Press start to start the timer");
        explanationText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
        explanationText.setFill(Color.YELLOW);
        explanationText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(explanationText);

        var startButton = new Button(new Location(1200, 350), "Start", showCase, 8);

        while (true) {
            StartStopTimer s = new StartStopTimer();
            s.startThread();
            int[] curTime = s.getTime();

            var displayNumberText = new TextEntity(new Location(getWidth() / 2, getHeight() / 2), curTime[0] + " : " + curTime[1] + " : " + curTime[2] + " : " + curTime[3]);
            displayNumberText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 300));
            displayNumberText.setFill(Color.YELLOW);
            displayNumberText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
            addEntity(displayNumberText);
        }
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }
}
