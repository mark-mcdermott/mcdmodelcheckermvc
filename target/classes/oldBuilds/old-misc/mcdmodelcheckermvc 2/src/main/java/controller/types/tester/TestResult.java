package controller.types.tester;

import static controller.types.tester.PassFail.FAIL;
import static controller.types.tester.PassFail.PASS;

public class TestResult {

    private String[] xmlExpected;
    private String[] xmlActual;
    private PassFail xmlResult;
    private String[] translationExpected;
    private String[] translationActual;
    private PassFail translationResult;
    private String[] interleavingsExpected;
    private String[] interleavingsActual;
    private PassFail interleavingsResult;
    private String[] modelCheckingExpected;
    private String[] modelCheckingActual;
    private PassFail modelCheckingResult;
    private PassFail testPassFail;
    private int testNumPassFail;
    private String testResultStr;

    public TestResult() { }

    public void setPassFailResults() {
        // if (xmlResult != null && translationResult != null) {
        // if (this.xmlResult != null && this.translationResult != null) {
        if (this.xmlActual != null && this.translationActual != null) {
            setXmlResult();
            setTranslationResult();
        }
        setInterleavingsResult();
        setModelCheckingResult();
        setTestPassFail();
        setTestNumPassFail();
        setTestResultStr();
    }

    void setTestResultStr() {
        this.testResultStr = testPassFail.toString() + ". " + testNumPassFail + "/4 tests passing.";
    }

    void setTestNumPassFail() {
        int numPass = 0;
        if (xmlResult == PASS) { numPass++; }
        if (translationResult == PASS) { numPass++; }
        if (interleavingsResult == PASS) { numPass++; }
        if (modelCheckingResult == PASS) { numPass++; }
        this.testNumPassFail = numPass;
    }

    void setTestPassFail() {
        if (xmlResult == null && translationResult == null) {
            if (interleavingsResult == PASS && modelCheckingResult == PASS) {
                this.testPassFail = PASS;
            }
        } else if (xmlResult == PASS && translationResult == PASS && interleavingsResult == PASS && modelCheckingResult == PASS) {
            this.testPassFail = PASS;
        } else {
            this.testPassFail = FAIL;
        }
    }

    void setXmlResult() {
        this.xmlResult = expectedActualPassFail(xmlExpected, xmlActual);
    }

    void setTranslationResult() {
        this.translationResult = expectedActualPassFail(translationExpected, translationActual);
    }

    void setInterleavingsResult() {
        this.interleavingsResult = expectedActualPassFail(interleavingsExpected, interleavingsActual);
    }

    void setModelCheckingResult() {
        this.modelCheckingResult = expectedActualPassFail(modelCheckingExpected, modelCheckingActual);
    }

    PassFail expectedActualPassFail(String[] expected, String[] actual) {
        PassFail passFail = PASS;
        if (expected.length != actual.length) {
            passFail = FAIL;
        } else {
            for (int i=0; i<expected.length; i++) {
                String thisExpected = expected[i];
                String thisActual = actual[i];
                Boolean doesExpectedEqualActual = thisExpected.equals(thisActual);
                if (!doesExpectedEqualActual) {
                    // System.out.println("expected: " + thisExpected + ". actual: " + thisActual); // TODO: for testing
                    passFail = FAIL;
                }
            }
        }
        return passFail;
    }

    // getters & settings

    public String[] getInterleavingsActual() {
        return interleavingsActual;
    }

    public String[] getInterleavingsExpected() {
        return interleavingsExpected;
    }

    public String[] getModelCheckingActual() {
        return modelCheckingActual;
    }

    public String[] getTranslationActual() {
        return translationActual;
    }

    public String[] getModelCheckingExpected() {
        return modelCheckingExpected;
    }

    public String[] getTranslationExpected() {
        return translationExpected;
    }

    public String[] getXmlActual() {
        return xmlActual;
    }

    public String[] getXmlExpected() {
        return xmlExpected;
    }

    public void setInterleavingsActual(String[] interleavingsActual) {
        this.interleavingsActual = interleavingsActual;
    }

    public void setInterleavingsExpected(String[] interleavingsExpected) {
        this.interleavingsExpected = interleavingsExpected;
    }

    public void setModelCheckingActual(String[] modelCheckingActual) {
        this.modelCheckingActual = modelCheckingActual;
    }

    public void setModelCheckingExpected(String[] modelCheckingExpected) {
        this.modelCheckingExpected = modelCheckingExpected;
    }

    public void setTranslationActual(String[] translationActual) {
        this.translationActual = translationActual;
    }

    public void setTranslationExpected(String[] translationExpected) {
        this.translationExpected = translationExpected;
    }

    public void setXmlActual(String[] xmlActual) {
        this.xmlActual = xmlActual;
    }

    public void setXmlExpected(String[] xmlExpected) {
        this.xmlExpected = xmlExpected;
    }

    public PassFail getInterleavingsResult() {
        return interleavingsResult;
    }

    public PassFail getModelCheckingResult() {
        return modelCheckingResult;
    }

    public PassFail getTranslationResult() {
        return translationResult;
    }

    public PassFail getXmlResult() {
        return xmlResult;
    }

    public PassFail getTestPassFail() {
        return testPassFail;
    }

    public int getTestNumPassFail() {
        return testNumPassFail;
    }

    public String getTestResultStr() {
        return testResultStr;
    }

}
