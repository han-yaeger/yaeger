package nl.meron.pong.scenes;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.meron.pong.Pong;
import nl.meron.pong.scenes.entities.Ball;
import nl.meron.pong.scenes.entities.PlayerOneBat;
import nl.meron.pong.scenes.entities.PlayerTwoBat;
import nl.meron.yaeger.engine.entities.entity.AnchorPoint;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.SceneBorder;
import nl.meron.yaeger.engine.scenes.DynamicScene;

/**
 * Level one of the game
 */
public class LevelOne extends DynamicScene implements ScoreKeeper {

    public static final Color GREEN_COLOR = Color.GREENYELLOW;
    int playerOnePoints;
    int playerTwoPoints;

    private TextEntity playerOne;
    private TextEntity playerTwo;

    @Override
    protected void setupDeprecatedSpawners() {

    }

    @Override
    public void setupScene() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        setBackgroundImage("pong/pong-bg.jpg");
    }

    @Override
    public void setupEntities() {
        var ball = new Ball(new Location(getWidth() / 2, getHeight() / 2), this);
        ball.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(ball);

        setupPlayerOne();
        setupPlayerTwo();


        updateScore();
    }

    private void setupPlayerOne() {
        var rectBat = new PlayerOneBat(new Location(20, 280));
        addEntity(rectBat);

        playerOne = new TextEntity(new Location(300, 5));
        playerOne.setFont(Font.font(Pong.FONT, 11));
        playerOne.setFill(GREEN_COLOR);
        addEntity(playerOne);
    }

    private void setupPlayerTwo() {
        var bat2 = new PlayerTwoBat(new Location(925, 280));
        addEntity(bat2);

        playerTwo = new TextEntity(new Location(600, 5));
        playerTwo.setFont(Font.font(Pong.FONT, 11));
        playerTwo.setFill(GREEN_COLOR);
        addEntity(playerTwo);
    }

    private void updateScore() {
        playerOne.setText("Player one: " + playerOnePoints);
        playerTwo.setText("Player two: " + playerTwoPoints);
    }

    public void playerScores(SceneBorder screenBorder) {
        if (screenBorder.equals(SceneBorder.LEFT)) {
            playerTwoPoints++;
        } else if (screenBorder.equals(SceneBorder.RIGHT)) {
            playerOnePoints++;
        }
        updateScore();
    }
}
