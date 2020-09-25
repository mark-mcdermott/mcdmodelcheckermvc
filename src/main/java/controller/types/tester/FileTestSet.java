package controller.types.tester;

import java.util.ArrayList;

import static controller.types.tester.PassFail.FAIL;
import static controller.types.tester.PassFail.PASS;

public class FileTestSet {

    ArrayList<FileTest> fileTests;
    PassFail fileTestSetPassFail;
    int numTests;
    int numPassingTests;
    String passFailStr;

    public FileTestSet(ArrayList<FileTest> fileTests) {
        this.fileTests = fileTests;
        this.numTests = fileTests.size();
        this.numPassingTests = calcNumPassingTests();
        this.fileTestSetPassFail = calcSetPassFail();
        this.passFailStr = setPassFailStr();
    }

    String setPassFailStr() {
        return fileTestSetPassFail.toString() + ". " + numPassingTests + "/" + numTests + " tests passing.";
    }

    PassFail calcSetPassFail() {
        if (numPassingTests == numTests) {
            return PASS;
        } else {
            return FAIL;
        }
    }

    int calcNumPassingTests() {
        int numPassing = 0;
        for (FileTest fileTest : fileTests) {
            if (fileTest.getTestResult().getTestPassFail() == PASS) { numPassing++; }
        }
        return numPassing;
    }

    // getters & setters

    public ArrayList<FileTest> getFileTests() {
        return fileTests;
    }

    public int getNumPassingTests() {
        return numPassingTests;
    }

    public int getNumTests() {
        return numTests;
    }

    public PassFail getFileTestSetPassFail() {
        return fileTestSetPassFail;
    }

    public String getPassFailStr() {
        return passFailStr;
    }

}
