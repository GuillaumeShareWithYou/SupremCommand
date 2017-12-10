package Engine.exceptions;

public class UnexistingCommandException extends Exception {
    public UnexistingCommandException(String msg) {
        super(msg);
    }
}
