package nl.meron.yaeger.engine.entities.entity.text;

import com.google.inject.Injector;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class DynamicTextEntityTest {

    private static final String YAEGER = "Yaeger";

    private DynamicTextEntityImpl sut;
    private Injector injector;
    private Text text;

    @BeforeEach
    void setup() {
        sut = new DynamicTextEntityImpl();
        text = mock(Text.class);
        sut.setText(YAEGER);
        sut.setTextDelegate(text);
        injector = mock(Injector.class);
    }

    @Test
    void configureIsCalledOnInitialization() {
        // Setup

        // Test
        sut.init(injector);

        // Verify
        Assertions.assertTrue(sut.configureCalled);
    }
}

class DynamicTextEntityImpl extends DynamicTextEntity {

    boolean configureCalled = false;

    @Override
    public void configure() {
        configureCalled = true;
    }
}
