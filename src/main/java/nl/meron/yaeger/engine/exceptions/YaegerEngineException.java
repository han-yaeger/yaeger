package nl.meron.yaeger.engine.exceptions;

public class YaegerEngineException extends RuntimeException {

    public YaegerEngineException(String message) {
        super(message);
    }

    public YaegerEngineException(Exception e) {
        super(e);
    }
}
