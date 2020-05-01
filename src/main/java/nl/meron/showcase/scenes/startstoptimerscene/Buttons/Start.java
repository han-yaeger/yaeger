package nl.meron.showcase.scenes.startstoptimerscene.Buttons;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import nl.meron.showcase.scenes.selection.SelectionScene;
import nl.meron.showcase.scenes.startstoptimerscene.timer.StartStopTimer;
import nl.meron.yaeger.engine.entities.entity.events.userinput.ButtonPressedListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseEnterListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseExitListener;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.entities.entity.Location;

public class Start extends TextEntity implements ButtonPressedListener, MouseEnterListener, MouseExitListener {

    private StartStopTimer s;
    private int timesPressed = 0;

    public Start(final Location Location, final String text, final StartStopTimer s){
        super(Location, text);
        this.s = s;
    }

    public void onButtonPressed(MouseButton button){
        if (timesPressed % 2 == 0) {
            s.startThread();
            this.setText("Stop");
        } else {
            s.stopThread();
            this.setText("Start");
        }
        timesPressed += 1;
    }

    public void onMouseEntered(){
        setFill(SelectionScene.TEXT_COLOR_HIGHLIGHT);
        setCursor(Cursor.HAND);
    }

    public void onMouseExited(){
        setFill(SelectionScene.TEXT_COLOR);
        setCursor(Cursor.DEFAULT);
    }

}
