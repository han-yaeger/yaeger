package nl.meron.showcase.buttons;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.selection.SelectionScene;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseEnterListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseExitListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseButtonPressedListener;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;

public class Button extends TextEntity implements MouseButtonPressedListener, MouseEnterListener, MouseExitListener {

    protected YaegerShowCase showCase;
    private int scene;

    public Button(final Location initialPosition, final String text, YaegerShowCase showCase, int scene) {
        super(initialPosition, text);
        this.showCase = showCase;
        this.scene = scene;
    }

    @Override
    public void onMouseButtonPressed(MouseButton button, double x, double y) {
        showCase.setActiveScene(scene);
    }

    @Override
    public void onMouseEntered() {
        setFill(SelectionScene.TEXT_COLOR_HIGHLIGHT);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        setFill(SelectionScene.TEXT_COLOR);
        setCursor(Cursor.DEFAULT);
    }
}
