package nl.meron.showcase.scenes.brightnessdemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.buttons.Back;
import nl.meron.yaeger.engine.entities.entity.Location;
import nl.meron.yaeger.engine.entities.entity.shape.text.TextEntity;
import nl.meron.yaeger.engine.scenes.StaticScene;

import java.util.Set;

public class BrightnessDemoScene extends StaticScene {

    private final Font TEXT_FONT = Font.font("American Typewriter", FontWeight.NORMAL, 18);
    private YaegerShowCase showCase;

    public BrightnessDemoScene(YaegerShowCase showCase) {
        this.showCase = showCase;
    }

    @Override
    public void setupScene() {
        Image image = new Image("showcase/backgrounds/brightness_demo_img.png");
        ImageView imageView = new ImageView(image);
        super.root.autosize();
        super.root.getChildren().add(imageView);
        setRoot(super.root);
    }

    @Override
    public void setupEntities() {
        TextEntity instructionText = new TextEntity(new Location(450, 40), "Raise Brightness: F12  Lower Brightness: F11  Raise Contrast: F10  Lower Contrast: F9");
        instructionText.setFont(TEXT_FONT);
        addEntity(instructionText);

        var backButton = new Back(showCase, new Location(20, 625));
        backButton.setFill(Color.BLACK);
        addEntity(backButton);
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        if (input.contains(KeyCode.F12)) {
            super.currentBrightness += 0.1;
            super.colorAdjust.setBrightness(super.currentBrightness);
            super.root.setEffect(super.colorAdjust);
        } else if (input.contains(KeyCode.F11)) {
            super.currentBrightness -= 0.1;
            super.colorAdjust.setBrightness(super.currentBrightness);
            super.root.setEffect(super.colorAdjust);
        } else if (input.contains(KeyCode.F10)) {
            super.currentContrast += 0.1;
            super.colorAdjust.setContrast(super.currentContrast);
            super.root.setEffect(super.colorAdjust);
        } else if (input.contains(KeyCode.F9)) {
            super.currentContrast -= 0.1;
            super.colorAdjust.setContrast(super.currentContrast);
            super.root.setEffect(super.colorAdjust);
        }
    }
}
