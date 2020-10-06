package controller.types.ctl;

import controller.utils.ExceptionMessage;

import static controller.utils.Utils.getLineNumber;

public class Label {

    private char value;

    public Label() {
        value = Character.MIN_VALUE; // null
    }

    public Label(char thisValue) {
        value = thisValue;
    }

    public Label(String thisValue) throws ExceptionMessage {
        if (thisValue.length() == 1 && thisValue.charAt(0) >= 'a' && thisValue.charAt(0) <= 'z') {
            value = thisValue.charAt(0);
        } else {
            throw new ExceptionMessage("controller.dataTypes.vertex.Label " + getLineNumber() + ": if Label() constructor's parameter is a string, it must be a string of length 1 and must be a lower case letter of the alphabet.");
        }

    }

    public Label getLabel() {
        return this;
    }

    public char getLabelChar() {
        return value;
    }

    public String getLabelStr() {
        return String.valueOf(value);
    }

    public void setLabel(char value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}
