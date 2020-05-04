package nl.han.yaeger.engine.styles;

/**
 * The different styles that are available for the default HAN font.
 */
public enum HanFontStyle {

    BOLD("Bold"),
    BOLD_ITALIC("BoldItalic"),
    DEMI_BOLD("DemiBold"),
    DEMI_BOLD_ITALIC("DemiBoldItalic"),
    HEAVY("Heavy"),
    HEAVY_ITALIC("HeavyItalic"),
    ITALIC("Italic"),
    MEDIUM("Medium"),
    MEDIUM_ITALIC("MediumItalic"),
    REGULAR("Regular"),
    ULTRA_LIGHT("UltraLight"),
    ULTRA_LIGHT_ITALIC("UltraLightItalic");

    private final String style;

    HanFontStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
