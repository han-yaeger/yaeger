package com.github.hanyaeger.api.engine.styles;

import com.github.hanyaeger.api.engine.media.ResourceConsumer;
import javafx.scene.text.Font;

/**
 * Yaeger's interface to all font styles that are part of the HAN University of applied sciences
 * house style.
 */
public class HanFont implements ResourceConsumer {

    private static final String FONT_LOCATION = "yaegerfonts/";
    private static final String FONT_DEFAULT_NAME = "avenirnext/AvenirNext";
    private static final String FONT_STYLE_SEPERATOR = "-";
    private static final String FONT_DEFAULT_CONDENSED_NAME = "avenirnextcondensed/AvenirNextCondensed";
    private static final String FONT_EXTENTION = ".ttf";

    private static HanFont getInstance() {
        return new HanFont();
    }

    /**
     * Create a {@link Font} for the given size in the HAN default style.
     *
     * @param size The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default style.
     */
    public static Font createDefaultFont(final double size) {
        return createDefaultFont(HanFontStyle.REGULAR, size);
    }

    /**
     * Create a {@link Font} for the given size in the HAN default style.
     *
     * @param style The {@link HanFontStyle} to be used.
     * @param size  The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default style.
     */
    public static Font createDefaultFont(final HanFontStyle style, final double size) {
        return Font.loadFont(getInstance().constructUrl(false, style), size);
    }

    /**
     * Create a {@link Font} for the given size in the HAN default condensed style.
     *
     * @param size The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default condensed style.
     */
    public static Font createDefaultCondensedFont(final double size) {
        return createDefaultCondensedFont(HanFontStyle.REGULAR, size);
    }

    /**
     * Create a {@link Font} for the given size in the HAN default condensed style.
     *
     * @param style The {@link HanFontStyle} to be used.
     * @param size  The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default condensed style.
     */
    public static Font createDefaultCondensedFont(final HanFontStyle style, final double size) {
        return Font.loadFont(getInstance().constructUrl(true, style), size);
    }

    private String constructUrl(final boolean condensed, final HanFontStyle style) {
        var fontName = condensed ? FONT_DEFAULT_CONDENSED_NAME : FONT_DEFAULT_NAME;
        return createPathForResource(FONT_LOCATION + fontName + FONT_STYLE_SEPERATOR + style.getStyle() + FONT_EXTENTION);
    }
}
