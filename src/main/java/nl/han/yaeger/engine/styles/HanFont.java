package nl.han.yaeger.engine.styles;

import javafx.scene.text.Font;

/**
 * Yaeger's interface to all styles that are part of the HAN University of applied sciences
 * house style.
 */
public class HanFont {

    private static final String FONT_LOCATION = "/fonts/";
    private static final String FONT_DEFAULT_NAME = "AvenirNext";
    private static final String FONT_STYLE_SEPERATOR = "-";
    private static final String FONT_DEFAULT_CONDENSED_NAME = "AvenirNextCondensed";
    private static final String FONT_EXTENTION = "ttf";

    public static HanFont getInstance() {
        return new HanFont();
    }

    public Font createDefaultFont(final double size) {
        return createDefaultFont(HanFontStyle.REGULAR, size);
    }

    /**
     * Create a {@link Font} for the given size in the HAN default style.
     *
     * @param size The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default style.
     */
    public Font createDefaultFont(final HanFontStyle style, final double size) {
        return Font.loadFont(constructUrl(false, style), size);
    }

    private String constructUrl(final boolean condensed, final HanFontStyle style) {
        var fontName = condensed ? FONT_DEFAULT_CONDENSED_NAME : FONT_DEFAULT_NAME;
        return FONT_LOCATION + fontName + FONT_STYLE_SEPERATOR + style.getStyle() + FONT_EXTENTION;
    }

    public Font createDefaultCondensedFont(double size) {
        return createDefaultCondensedFont(HanFontStyle.REGULAR, size);
    }

    /**
     * Create a {@link Font} for the given size in the HAN default condensed style.
     *
     * @param size The size of the font as a {@code double}.
     * @return A {@link Font} of the given size and the HAN default condensed style.
     */
    public Font createDefaultCondensedFont(final HanFontStyle style, double size) {
        return Font.loadFont(constructUrl(true, style), size);
    }
}
