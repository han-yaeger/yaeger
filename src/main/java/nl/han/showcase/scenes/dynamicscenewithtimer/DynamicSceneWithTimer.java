package nl.han.showcase.scenes.dynamicscenewithtimer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.showcase.YaegerShowCase;
import nl.han.showcase.buttons.Back;
import nl.han.showcase.scenes.ShowCaseScene;
import nl.han.showcase.scenes.dynamicscenewithtimer.timers.SceneMinuteTimer;
import nl.han.waterworld.Waterworld;
import nl.han.yaeger.engine.WithTimers;
import nl.han.yaeger.engine.entities.entity.AnchorPoint;
import nl.han.yaeger.engine.entities.entity.Location;
import nl.han.yaeger.engine.entities.entity.shape.text.TextEntity;

public class DynamicSceneWithTimer extends ShowCaseScene implements WithTimers {

    public static final int COUNTDOWN_NUMBER = 5;
    private YaegerShowCase showCase;
    private TextEntity displayNumberText;
    private int displayNumber;

    public DynamicSceneWithTimer(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupTimers() {
        displayNumber = COUNTDOWN_NUMBER;
        addTimer(new SceneMinuteTimer(this));
    }

    @Override
    public void setupScene() {
        setBackgroundImage("showcase/backgrounds/milky-way.jpg");
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase, new Location(20, getHeight() - 30));
        addEntity(backButton);

        var explanationText = new TextEntity(new Location(getWidth() / 2, 150), "This scene will automatically switch back to the selection scene in: ");
        explanationText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
        explanationText.setFill(Color.YELLOW);
        explanationText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(explanationText);

        displayNumberText = new TextEntity(new Location(getWidth() / 2, getHeight() / 2), Integer.toString(displayNumber));
        displayNumberText.setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 300));
        displayNumberText.setFill(Color.YELLOW);
        displayNumberText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(displayNumberText);
    }

    @Override
    public YaegerShowCase getShowCase() {
        return showCase;
    }

    public void update() {
        if (displayNumber <= 0) {
            displayNumber = COUNTDOWN_NUMBER;
            showCase.setActiveScene(YaegerShowCase.SCENE_SELECTION);
        } else {
            displayNumberText.setText(Integer.toString(displayNumber--));
        }
    }
}
