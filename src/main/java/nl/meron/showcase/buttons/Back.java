package nl.meron.showcase.buttons;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.waterworld.Waterworld;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;

public class Back extends TextEntity implements MousePressedListener {

    public static final String BACK = "Back";
    private YaegerShowCase showCase;

    public Back(YaegerShowCase showCase) {
        super(new Point(10, 650), BACK);
        this.showCase = showCase;
        setFill(Color.SNOW);
        setFont(Font.font(Waterworld.FONT, FontWeight.BOLD, 30));
    }

    @Override
    public void onMousePressed(MouseButton button) {
        if (button.equals(MouseButton.PRIMARY)) {
            showCase.setActiveScene(YaegerShowCase.SCENE_SELECTION);
        }
    }
}
