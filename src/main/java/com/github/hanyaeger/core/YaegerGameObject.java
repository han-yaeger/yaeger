package com.github.hanyaeger.core;

import javafx.scene.effect.ColorAdjust;

/**
 * The abstract base class of both the {@link com.github.hanyaeger.api.entities.YaegerEntity}
 * and {@link com.github.hanyaeger.api.scenes.StaticScene} and contains the shared behaviour.
 */
public abstract class YaegerGameObject implements Effectable {

    /**
     * The {@link ColorAdjust}, which is responsible for setting the effects on the {@link javafx.scene.Node}.
     */
    protected ColorAdjust colorAdjust;

    @Override
    public void setBrightness(final double brightness) {
        colorAdjust.setBrightness(brightness);
    }

    @Override
    public void setContrast(final double contrast) {
        colorAdjust.setContrast(contrast);
    }

    @Override
    public void setHue(final double hue) {
        colorAdjust.setHue(hue);
    }

    @Override
    public void setSaturation(final double saturation) {
        colorAdjust.setSaturation(saturation);
    }

    @Override
    public double getBrightness() {
        return colorAdjust.getBrightness();
    }

    @Override
    public double getContrast() {
        return colorAdjust.getContrast();
    }

    @Override
    public double getHue() {
        return colorAdjust.getHue();
    }

    @Override
    public double getSaturation() {
        return colorAdjust.getSaturation();
    }
}
