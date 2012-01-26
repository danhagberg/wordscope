/**
 * 
 */
package net.digitaltsunami.word.util;

/**
 * @author dhagberg
 * 
 */
public class WordscopeException extends Exception {

    private static final long serialVersionUID = 4764556586444782373L;

    /**
     * Construct a new exception with a message and the cause for the exception.
     * 
     * @param message
     *            Message specific to this exception.
     * @param cause
     *            Error that occurred causing this exception to be created.
     */
    public WordscopeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new exception with a message.
     * 
     * @param message
     *            Message specific to this exception.
     */
    public WordscopeException(String message) {
        super(message);
    }

    /**
     * Construct a new exception with the cause for the exception. The message
     * from the cause will be used as the message for this exception.
     * 
     * @param message
     *            Message specific to this exception.
     * @param cause
     *            Error that occurred causing this exception to be created.
     * @param cause
     */
    public WordscopeException(Throwable cause) {
        super(cause);
    }

}
