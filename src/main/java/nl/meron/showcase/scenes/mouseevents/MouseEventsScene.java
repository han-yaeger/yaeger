package nl.meron.showcase.scenes.mouseevents;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseEnterListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseExitListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.StaticScene;

import java.util.Optional;
import java.util.Set;

public class MouseEventsScene extends StaticScene implements MousePressedListener, MouseEnterListener, MouseExitListener {

    private double x_coordinate = 0;
    private double y_coordinate = 0;
    private final Font TEXT_FONT = Font.font("American Typewriter", FontWeight.NORMAL, 20);
    private TextEntity mouseEnterText = new TextEntity(new Location(20, 100), "Mouse entered the screen.");
    private TextEntity mouseExitText = new TextEntity(new Location(20, 150), "Mouse exited the screen.");
    public TextEntity x_val = new TextEntity(new Location(820,625), Double.toString(x_coordinate));
    public TextEntity y_val = new TextEntity(new Location(920,625), Double.toString(y_coordinate));
    private YaegerShowCase showCase;

    public MouseEventsScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        Scene scene = getScene();
        scene.setOnMousePressed(event -> onMousePressed(event, event.getX(), event.getY()));
        scene.setOnMouseEntered(event -> onMouseEntered());
        scene.setOnMouseExited(event -> onMouseExited());

        setBackgroundColor(Color.BLANCHEDALMOND);
    }

    @Override
    public void setupEntities() {
        var backButton = new Back(showCase);
        backButton.setFill(Color.BLACK);
        addEntity(backButton);

        TextEntity instructionText = new TextEntity(new Location(20, 30), "Click anywhere to see the x and y coordinates for that point...");
        instructionText.setFont(TEXT_FONT);
        addEntity(instructionText);

        TextEntity x_label = new TextEntity(new Location(800, 625), "X: ");
        TextEntity y_label = new TextEntity(new Location(900, 625), "Y: ");
        x_label.setFont(TEXT_FONT);
        y_label.setFont(TEXT_FONT);
        addEntity(x_label);
        addEntity(y_label);

        mouseEnterText.setFont(TEXT_FONT);
        mouseExitText.setFont(TEXT_FONT);
        addEntity(mouseEnterText);
        addEntity(mouseExitText);
        mouseEnterText.setVisible(false);
        mouseExitText.setVisible(false);
        x_val.setFont(TEXT_FONT);
        y_val.setFont(TEXT_FONT);
        addEntity(x_val);
        addEntity(y_val);
    }

    @Override
    public void onMouseEntered() {
        mouseEnterText.setVisible(true);
        mouseExitText.setVisible(false);
    }

    @Override
    public void onMouseExited() {
        mouseExitText.setVisible(true);
        mouseEnterText.setVisible(false);
    }

    @Override
    public void onMousePressed(MouseEvent event, Double xCoordinates, Double yCoordinates) {
        x_val.setText(Double.toString(xCoordinates));
        y_val.setText(Double.toString(yCoordinates));
    }

    @Override
    public Optional<Node> getGameNode() {
        return Optional.empty();
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {

    }
}
