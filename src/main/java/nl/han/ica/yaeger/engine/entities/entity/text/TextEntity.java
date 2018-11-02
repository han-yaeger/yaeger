package nl.han.ica.yaeger.engine.entities.entity.text;

import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;

public class TextEntity implements Entity {

    private Text text;
    private Position position;
    private String initialText;

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Position}.
     *
     * @param position the initial {@link Position} of this {@code TextEntity}
     */
    public TextEntity(final Position position) {
        this(position, "");
    }

    /**
     * Instantiate a new {@code TextEntity} for the given {@link Position} and text.
     *
     * @param position the initial {@link Position} of this {@code TextEntity}
     * @param text     a {@link String} containing the initial text to be displayed
     */
    public TextEntity(final Position position, final String text) {
        this.position = position;
        this.initialText = text;
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
     * Set the {@link String} that should be shown.
     *
     * @param text the {@link String} that should be shown
     */
    public void setText(final String text) {
        this.text.setText(text);
    }

    /**
     * Set the color of the text.
     *
     * @param color an instance of {@link Color}
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

    @Inject
    public void setDelgate(Text text) {
        this.text = text;
        if (position != null) {
            this.text.setX(position.getX());
            this.text.setY(position.getY());
        }
        if (initialText != null && !initialText.isEmpty()) {
            this.text.setText(initialText);
        }
        this.text.setOnMousePressed(event -> onMousePressed(event.getButton()));
    }
}
