package Engine;

public class ForbiddenCommandException extends Exception {
    public ForbiddenCommandException(String s) {
        super(s);
    }
}
