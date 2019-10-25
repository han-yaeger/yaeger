package nl.meron.waterworld.entities.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;
import nl.meron.yaeger.engine.scenes.SceneType;

public class StartPressed extends TextEntity implements MousePressedListener {

    public static final String PLAY_GAME = "Play game";
    private Waterworld waterworld;

    public StartPressed(Waterworld waterworld) {
        super(new Point(380, 400), PLAY_GAME);
        this.waterworld = waterworld;
        setFill(Color.VIOLET);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.nextScene(SceneType.LEVEL_ONE);
        }
    }
}
