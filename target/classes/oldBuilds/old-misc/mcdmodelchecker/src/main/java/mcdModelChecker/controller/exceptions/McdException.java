package mcdModelChecker.controller.exceptions;

/**
 * McdException wraps all checked standard Java exception and enriches them with a custom error code.
 * This custom exception code modified from the  MyBusinessExcpetion code by Thorben Janssen at
 * <a href="https://stackify.com/java-custom-exceptions">https://stackify.com/java-custom-exceptions</a>,
 * accessed 9/18/20
 *
 * @author TJanssen
 */
public class McdException extends Exception {

    public McdException() {
        super();
    }

    public McdException(String message, Throwable cause) {
        super(message, cause);
    }

    public McdException(String message) {
        super(message);
    }

    public McdException(Throwable cause) {
        super(cause);
    }

}
