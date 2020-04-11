package nl.meron.showcase.scenes.mouseevents;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.StaticScene;

import java.util.Optional;
import java.util.Set;

public class MouseEventsScene extends StaticScene implements MousePressedListener {

    public static final Color TEXT_COLOR = Color.BLACK;
    private static final Font TEXT_FONT = Font.font("American Typewriter", FontWeight.NORMAL, 20);
    private YaegerShowCase showCase;

    public MouseEventsScene(final YaegerShowCase showCase){
        this.showCase = showCase;
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {

    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.BLUEVIOLET);
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        addEntity(backButton);
        var text = new TextEntity(new Location(20, 30), "Click anywhere to see the x and y coordinates for that point...");
        var x_label = new TextEntity(new Location(800, 625), "X: ");
        var y_label = new TextEntity(new Location(900, 625), "Y: ");
        styleText(x_label);
        styleText(y_label);
        styleText(text);
        addEntity(x_label);
        addEntity(y_label);
        addEntity(text);
    }

    void styleText(TextEntity entity) {
        entity.setFill(TEXT_COLOR);
        entity.setFont(TEXT_FONT);
    }

    @Override
    public void onMousePressed(MouseEvent event, MouseButton button, Double xCoordinates, Double yCoordinates) {
        double x_coord = event.getX();
        double y_coord = event.getY();
        System.out.println(x_coord);
        System.out.println(y_coord);
        var x_label = new TextEntity(new Location(30, 30), Double.toString(x_coord));
        var y_label = new TextEntity(new Location(110, 30), Double.toString(y_coord));
        styleText(x_label);
        styleText(y_label);
        addEntity(x_label);
        addEntity(y_label);
    }

    @Override
    public Optional<Node> getGameNode() {
        return Optional.empty();
    }
}
