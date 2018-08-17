package nl.han.ica.yaeger.gameobjects.text;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.han.ica.yaeger.gameobjects.GameObject;

public class TextObject extends GameObject {

    private Text text;

    /**
     * Creëer een nieuw {@code TextObject} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param x De x-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     * @param y De y-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     */
    public TextObject(final double x, final double y) {
        this(x, y, "");
    }

    /**
     * Creëer een nieuw {@code TextObject} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param x    De x-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     * @param y    De y-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     * @param text De initiele text die getoond moet worden.
     */
    public TextObject(final double x, final double y, final String text) {
        this.text = new Text(x, y, text);
    }

    /**
     * Zet de text die getoond moet worden.
     *
     * @param text De text die getoond moet worden.
     */
    public void setText(final String text) {
        this.text.setText(text);
    }

    /**
     * Zet de kleur van de text.
     *
     * @param color De kleur van de text.
     */
    public void setFill(Color color) {
        text.setFill(color);
    }

    /**
     * Zet het font van de text. Gebruik deze methode om zowel het lettertype, de grootte als andere eigenschappen
     * van het font te zetten.
     * <p>
     * Voor het enkel het zetten van het lettertype en de grootte:
     * <p>
     * {@code setFont(Font.font ("Verdana", 20));}
     * <p>
     * Ook is het mogelijk gelijk andere waardes, zoals fontweight te zetten:
     * {@code setFont(Font.font("Verdana", FontWeight.BOLD, 70));}
     *
     * @param font Het font.
     */
    public void setFont(Font font) {
        text.setFont(font);
    }

    /**
     * Zet de x-locatie van dit {@code TextObject}.
     *
     * @param x De x-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     */
    public void setX(double x) {
        text.setX(x);
    }

    /**
     * Zet de y-locatie van dit {@code TextObject}.
     *
     * @param y De y-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     */
    public void setY(double y) {
        text.setY(y);
    }

    /**
     * Zet de locatie van dit {@code TextObject}.
     *
     * @param x De x-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     * @param y De y-coordinaat waarop het {@code TextObject} moet worden gepositioneerd.
     */
    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    @Override
    public Node getGameNode() {
        return text;
    }

    @Override
    public void setVisible(boolean visible) {
        text.setVisible(visible);
    }
}
