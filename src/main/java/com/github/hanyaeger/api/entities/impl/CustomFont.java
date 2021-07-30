package com.github.hanyaeger.api.entities.impl;

import com.github.hanyaeger.core.ResourceConsumer;
import com.github.hanyaeger.core.exceptions.ResourceNotAvailableException;
import javafx.scene.text.Font;

/**
 * Although many fonts are already available through the underlying technology JavaFX,
 * it is also possible to use custom fonts. To use such a custom font (*.ttf), the font file
 * should be available on the Class-path, preferably in the {@code resources/} folder.
 */
public record CustomFont(String path, double size) implements ResourceConsumer {

    /**
     * Create a new Custom Font for the given font file. Note that this font file should be available on
     * the Class Path and should be opened through the module descriptor.
     *
     * @param path the path of the *.ttf file. Note that this font file should be available on the Class Path and
     *             should be opened through the module descriptor
     * @param size the size of the font as a {@code double}
     * @throws ResourceNotAvailableException an {@link ResourceNotAvailableException} is thrown if the
     *                                       resource can not be found on the class path
     */
    public CustomFont(final String path, final double size) {
        this.path = createPathForResource(path);
        this.size = size;
    }

    /**
     * Return a {@link Font} with the specified size.
     *
     * @return a {@link Font} of the specified size
     */
    Font getFont() {
        return Font.loadFont(path, size);
    }
}
