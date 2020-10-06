package controller.dataTypes.testHelpers;

public class ExpectedResult {

    private String[] xmlExpected;
    private String[] xmlActual;
    private String[] translationExpected;
    private String[] translationActual;
    private String[] interleavingsExpected;
    private String[] interleavingsActual;
    private String[] modelCheckingExpected;

    public ExpectedResult() { }

    public String[] getXmlExpected() {
        return xmlExpected;
    }

    public String[] getXmlActual() {
        return xmlActual;
    }

    public String[] getTranslationExpected() {
        return translationExpected;
    }

    public String[] getModelCheckingExpected() {
        return modelCheckingExpected;
    }

    public String[] getTranslationActual() {
        return translationActual;
    }

    public String[] getInterleavingsExpected() {
        return interleavingsExpected;
    }

    public String[] getInterleavingsActual() {
        return interleavingsActual;
    }

}
