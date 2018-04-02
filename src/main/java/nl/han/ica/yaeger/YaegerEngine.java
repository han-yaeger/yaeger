package nl.han.ica.yaeger;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class YaegerEngine extends Application {

    /**
     * Implement this method the set the width and height of the game.
     *
     * @return A GameDimensions that contains the width and height of the game
     */
    public abstract GameDimensions getGameDimensions();

    /**
     * Implement this method to set the title of the game.
     *
     * @return A String containing the title of the game
     */
    public abstract String getGameTitle();

    /**
     * Override this method to
     *
     * @param scene
     */
    protected void beforeStageIsShown(Scene scene) {
    }

    @Override
    public void start(Stage primaryStage) {

        setGameTitle(primaryStage);
        var scene = createPrimaryScene(primaryStage);

        primaryStage.show();

        beforeStageIsShown(scene);

    }


    private Scene createPrimaryScene(Stage primaryStage) {
        var dimensions = getGameDimensions();
        Scene scene = new Scene(new Group(), dimensions.getWidth(), dimensions.getHeight());
        primaryStage.setScene(scene);

        return scene;
    }

    private void setGameTitle(Stage primaryStage) {
        primaryStage.setTitle(getGameTitle());
    }
}
