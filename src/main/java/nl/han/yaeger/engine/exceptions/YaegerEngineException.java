package nl.han.yaeger.engine.exceptions;

public class YaegerEngineException extends RuntimeException {

    public YaegerEngineException(final String message) {
        super(message);
    }

    public YaegerEngineException(final Exception e) {
        super(e);
    }
}
