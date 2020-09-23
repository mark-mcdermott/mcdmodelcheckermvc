package controller.utils;

import static controller.utils.Utils.getLineNumber;

// From https://alvinalexander.com/java/java-custom-exception-create-throw-exception, accessed 9/22/20
// TODO: this isn't printing to the console - why?
public class ExceptionMessage extends Exception {
    public ExceptionMessage(String message) {
        super(message + ": " + getLineNumber());
    }
}
