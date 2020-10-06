package controller.helpers;

// From https://alvinalexander.com/java/java-custom-exception-create-throw-exception
public class McdException extends Exception {
    public McdException(String message) {
        super(message);
    }
}