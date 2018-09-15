package nl.han.ica.yaeger.debug;

import javafx.scene.Node;
import nl.han.ica.yaeger.scene.YaegerScene;


public class Debugger extends Node {

    private boolean active = false;

    public Debugger(YaegerScene scene) {

    }

    public void toggle() {
        if (active) {
            active = false;
            hideDebugger();
        } else {
            active = true;
            showDebugger();
        }
    }

    private void hideDebugger() {
        System.out.println("Debugger is hidden");
    }

    private void showDebugger() {
        System.out.println("Debugger is showing");
    }
}
