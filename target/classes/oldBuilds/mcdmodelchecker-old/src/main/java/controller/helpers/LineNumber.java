package controller.helpers;

public class LineNumber {

    public LineNumber() { }

    // from https://stackoverflow.com/a/115027
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

}