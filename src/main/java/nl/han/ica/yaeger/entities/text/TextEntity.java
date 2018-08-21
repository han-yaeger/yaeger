package nl.han.ica.yaeger.entities.text;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.han.ica.yaeger.entities.Entity;

public class TextEntity extends Entity {

    private Text text;

    /**
     * Creëer een nieuw {@code TextEntity} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param x De x-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     * @param y De y-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     */
    public TextEntity(final double x, final double y) {
        this(x, y, "");
    }

    /**
     * Creëer een nieuw {@code TextEntity} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param x    De x-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     * @param y    De y-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     * @param text De initiele text die getoond moet worden.
     */
    public TextEntity(final double x, final double y, final String text) {
        this.text = new Text(x, y, text);
        this.text.setOnMousePressed(event -> onMousePressed(event.getButton()));
    }

    /**
     * Deze methode wordt aangeroepen wanneer er op deze {@code TextEntity} wordt geklikt.
     *
     * @param button De muisknop waarmee wordt geklikt. Dit is een instantie van {@link MouseButton}.
     */
    protected void onMousePressed(MouseButton button) {
        // implementeer deze methode om gebruik te maken van dit gedrag
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
     * Zet de x-locatie van dit {@code TextEntity}.
     *
     * @param x De x-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     */
    public void setX(double x) {
        text.setX(x);
    }

    /**
     * Zet de y-locatie van dit {@code TextEntity}.
     *
     * @param y De y-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     */
    public void setY(double y) {
        text.setY(y);
    }

    /**
     * Zet de locatie van dit {@code TextEntity}.
     *
     * @param x De x-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
     * @param y De y-coordinaat waarop het {@code TextEntity} moet worden gepositioneerd.
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
