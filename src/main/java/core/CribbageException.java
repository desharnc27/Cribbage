package core;

/**
 *
 * @author desharnc27
 */
public class CribbageException extends Exception {

    private boolean initError = false;

    CribbageException(String message) {

        super(message);
    }

    CribbageException(String message, boolean bool) {
        super(message);
        initError = bool;
    }

    public String getErrorMessage() {
        //return "Invalid cribbage hand input: "+ this.getMessage();
        return Langu.smallTextX("errorHand", new String[]{this.getMessage()});
    }

    public boolean isInitError() {
        return initError;
    }
}
