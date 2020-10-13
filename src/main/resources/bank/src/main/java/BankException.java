package main.java;

public class BankException extends Exception {
    public BankException(String errorMessage) {
        super(errorMessage);
    }
}