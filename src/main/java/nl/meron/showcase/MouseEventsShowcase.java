package nl.meron.showcase;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseEnterListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MouseExitListener;
import nl.meron.yaeger.engine.entities.entity.events.userinput.MousePressedListener;

import java.util.Optional;

public class MouseEventsShowcase extends YaegerShowCase implements MousePressedListener, MouseEnterListener, MouseExitListener {

    private final Font TEXT_FONT = Font.font("American Typewriter", FontWeight.NORMAL, 20);
    public Text mouseEnterText = new Text(20, 100, "Mouse entered the screen.");
    public Text mouseExitText = new Text(20, 150, "Mouse exited the screen.");
    public double x_coordinate;
    public double y_coordinate;
    public Text x_val = new Text(820,625, Double.toString(x_coordinate));
    public Text y_val = new Text(920,625, Double.toString(y_coordinate));
    private final String GAME_TITLE = "Mouse Events Showcase";
    private final int WIDTH = 1280;
    private final int HEIGHT = 719;
    Button quitButton;

    public void initializeGame() {}

    @Override
    public void setupScenes() {}

    @Override
    public void start(Stage primaryStage) {
        quitButton = new Button("QUIT");
        quitButton.setLayoutX(100);
        quitButton.setLayoutY(680);

        Text instructionText = new Text(20, 30, "Click anywhere to see the x and y coordinates for that point...");
        instructionText.setFont(TEXT_FONT);
        Text x_label = new Text(800, 625, "X: ");
        Text y_label = new Text(900, 625, "Y: ");
        x_label.setFont(TEXT_FONT);
        y_label.setFont(TEXT_FONT);

        mouseEnterText.setFont(TEXT_FONT);
        mouseExitText.setFont(TEXT_FONT);
        mouseEnterText.setVisible(false);
        mouseExitText.setVisible(false);
        x_val.setFont(TEXT_FONT);
        y_val.setFont(TEXT_FONT);

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        root.getChildren().add(quitButton);
        root.getChildren().addAll(instructionText, x_label, y_label, mouseEnterText, mouseExitText, x_val, y_val);

        scene.setOnMousePressed(event -> onMousePressed(event, event.getX(), event.getY()));
        scene.setOnMouseEntered(event -> onMouseEntered());
        scene.setOnMouseExited(event -> onMouseExited());
        quitButton.setOnMousePressed(event -> onQuitPressed());

        primaryStage.setTitle(GAME_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void onMousePressed(MouseEvent event, Double xCoordinates, Double yCoordinates) {
        x_val.setText(Double.toString(xCoordinates));
        y_val.setText(Double.toString(yCoordinates));
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

    public void onQuitPressed() {
        System.exit(0);
    }

    @Override
    public Optional<Node> getGameNode() {
        return Optional.empty();
    }

    public void setupEntities() {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
