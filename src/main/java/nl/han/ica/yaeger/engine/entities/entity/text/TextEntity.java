package nl.han.ica.yaeger.engine.entities.entity.text;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class TextEntity implements Entity {

    private Text text;

    /**
     * Creëer een nieuw {@code TextEntity} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param position the initial {@link Position} of this Entity
     */
    public TextEntity(final Position position) {
        this(position, "");
    }

    /**
     * Creëer een nieuw {@code TextEntity} op de gegeven coördinaten. Het ankerpunt is hierbij links-boven.
     *
     * @param position the initial {@link Position} of this Entity
     * @param text     De initiele text die getoond moet worden.
     */
    public TextEntity(Position position, final String text) {
        this.text = new Text(position.getX(), position.getY(), text);
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
     * Zet het FONT van de text. Gebruik deze methode om zowel het lettertype, de grootte als andere eigenschappen
     * van het FONT te zetten.
     * <p>
     * Voor het enkel het zetten van het lettertype en de grootte:
     * <p>
     * {@code setFont(Font.FONT ("Verdana", 20));}
     * <p>
     * Ook is het mogelijk gelijk andere waardes, zoals fontweight te zetten:
     * {@code setFont(Font.FONT("Verdana", FontWeight.BOLD, 70));}
     *
     * @param font Het FONT.
     */
    public void setFont(Font font) {
        text.setFont(font);
    }

    @Override
    public void remove() {
        text.setVisible(false);
        text.setText(null);
        notifyRemove();
    }

    @Override
    public Node getGameNode() {
        return text;
    }

    @Override
    public void setVisible(boolean visible) {
        text.setVisible(visible);
    }

    @Override
    public Position getPosition() {
        return new Position(text.getX(), text.getY());
    }
}
