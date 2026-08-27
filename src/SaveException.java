public class SaveException extends Exception {
    public SaveException (String message) {
        super (message);
    }

    public SaveException (String m, Throwable cause) {
        super (m, cause);
    }
}
