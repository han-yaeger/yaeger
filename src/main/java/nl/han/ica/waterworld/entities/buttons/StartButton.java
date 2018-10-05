package nl.han.ica.waterworld.entities.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.ica.waterworld.Waterworld;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import nl.han.ica.yaeger.engine.entities.entity.text.TextEntity;
import nl.han.ica.yaeger.engine.scene.SceneType;

public class StartButton extends TextEntity {

    public static final String PLAY_GAME = "Play game";
    private Waterworld waterworld;

    public StartButton(Waterworld waterworld) {
        super(new Position(380, 400), PLAY_GAME);
        this.waterworld = waterworld;
        setFill(Color.VIOLET);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    protected void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            waterworld.nextScene(SceneType.LEVEL_ONE);
        }
    }
}
