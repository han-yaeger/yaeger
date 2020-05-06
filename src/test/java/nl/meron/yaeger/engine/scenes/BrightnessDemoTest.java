package nl.meron.yaeger.engine.scenes;

import javafx.scene.input.KeyCode;
import nl.meron.showcase.YaegerShowCase;
import nl.meron.showcase.scenes.brightnessdemo.BrightnessDemoScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

class BrightnessDemoTest {
    private YaegerShowCase showCase;
    private TestBrightnessDemoScene testScene;

    @BeforeEach
    void setup() {
        testScene = new TestBrightnessDemoScene(showCase);
    }

    @Test
    void testOnInputChanged() {
        testScene.onInputChanged(Collections.singleton(KeyCode.F12));
        assert testScene.colorAdjust.getBrightness() == 0.2;
    }


    private class TestBrightnessDemoScene extends BrightnessDemoScene {

        public TestBrightnessDemoScene(YaegerShowCase showCase) {
            super(showCase);
        }

        @Override
        public void onInputChanged(Set<KeyCode> input) {
            if (input.contains(KeyCode.F12)) {
                currentBrightness += 0.1;
                colorAdjust.setBrightness(super.currentBrightness);
            } else if (input.contains(KeyCode.F11)) {
                currentBrightness -= 0.1;
                colorAdjust.setBrightness(super.currentBrightness);
            } else if (input.contains(KeyCode.F10)) {
                currentContrast += 0.1;
                colorAdjust.setContrast(super.currentContrast);
            } else if (input.contains(KeyCode.F9)) {
                currentContrast -= 0.1;
                colorAdjust.setContrast(super.currentContrast);
            }
        }
    }
}
