package nl.meron.waterworld.entities.buttons;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseEnterListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseExitListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;

public class StartPressed extends TextEntity implements MousePressedListener, MouseEnterListener, MouseExitListener {

    private static final Color TEXT_COLOR = Color.PURPLE;
    private static final Color TEXT_COLOR_HIGHLIGHT = Color.VIOLET;

    public static final String PLAY_GAME = "Play game";
    private Waterworld waterworld;

    public StartPressed(Waterworld waterworld) {
        super(new Point(380, 400), PLAY_GAME);
        this.waterworld = waterworld;
        setFill(TEXT_COLOR);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.nextScene(Waterworld.SCENE_LEVEL_ONE);
        }
    }

    @Override
    public void onMouseEntered() {
        setFill(TEXT_COLOR_HIGHLIGHT);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        setFill(TEXT_COLOR);
        setCursor(Cursor.DEFAULT);
    }
}
