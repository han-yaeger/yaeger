package nl.meron.showcase.scenes.selection.entities;

import javafx.scene.input.MouseButton;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.yaeger.engine.entities.entity.Point;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.text.TextEntity;

public class ClickableTextEntity extends TextEntity implements MousePressedListener {

    private YaegerShowCase showCase;
    private int scene;

    public ClickableTextEntity(final Point initialPosition, final String text, YaegerShowCase showCase, int scene) {
        super(initialPosition, text);
        this.showCase = showCase;
        this.scene = scene;
    }

    @Override
    public void onMousePressed(MouseButton button) {
        showCase.setActiveScene(scene);
    }
}
