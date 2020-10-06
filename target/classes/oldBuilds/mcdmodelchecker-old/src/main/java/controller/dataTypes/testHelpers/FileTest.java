package controller.dataTypes.testHelpers;

import _options.Options;
import controller.ModelChecker;
import controller.ReadKrp;
import controller.dataTypes.ctlHelpers.AllStatesThatHoldForAllModels;
import controller.dataTypes.ctlHelpers.StatesThatHoldForModel;
import controller.dataTypes.graphHelpers.FileSelectResult;
import controller.dataTypes.graphHelpers.LabelHash;
import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphItems.Vertex;
import controller.helpers.ListHelper;
import controller.helpers.McdException;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileTest {

    private String testFile;
    private String testPath;
    private String testFilePath;
    private TestResult testResult;

    public FileTest() { }

    public FileTest(String testFile, String testPath, Model model, View view, Options options) throws IOException, McdException, SAXException, ParserConfigurationException {
        this.testFile = testFile;
        this.testPath = testPath;
        this.testFilePath = testPath + testFile;
        // System.out.println(testFile); // TODO: For Testing!
        this.testResult = new TestResult();
        getExpectedResults(this.testResult, this.testFilePath, options);
        getActualResults(this.testResult, testFile, model, view, options);
        this.testResult.setPassFailResults();
    }

    // line by line file read strategy/code from https://www.journaldev.com/709/java-read-file-line-by-line
    void getExpectedResults(TestResult blankTestResult, String testFilePath, Options options) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(testFilePath));
        ReadKrp readKrp = new ReadKrp();
        blankTestResult.setXmlExpected(readKrp.getKripkeStringsFromFile(reader, options, false));
        blankTestResult.setTranslationExpected(readKrp.getKripkeStringsFromFile(reader, options, false));
        blankTestResult.setInterleavingsExpected(readKrp.getKripkeStringsFromFile(reader, options, false));
        blankTestResult.setModelCheckingExpected(getModelCheckingExpectedFromFile(reader, options));
        reader.close();
    }

    void getActualResults(TestResult testResultWithoutActualResults, String testFile, Model model, View view, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
        ListHelper listHelper = new ListHelper();
        String[] singleTestFileArr = new String[] {testFile};
        FileSelectResult actualResults = new FileSelectResult(singleTestFileArr, null, null, model, view, options, false, false);
        int numProps = actualResults.getNumProperties();
        Kripke kripke = actualResults.getInterleavingsKripke();
        ArrayList<Vertex> states = actualResults.getInterleavingsVertexList().getList();
        Vertex initialState = states.get(0);
        ArrayList<String> modelList = listHelper.stringArrToArrList(model.initModelList(numProps, options));
        LabelHash labelHash = actualResults.getLabelHash();
        int loops = 0; // TODO: add loop variations to the tests
        String[] allStatesThatHoldForAllModelsStrArr = getAllStatesThatHoldForAllModelsStrArr(modelList, kripke, loops, labelHash);

        if (testFile.contains(".ljx")) {
            testResultWithoutActualResults.setXmlActual(actualResults.getXmlKripke().toMultiLineString());
            testResultWithoutActualResults.setTranslationActual(actualResults.getTranslationKripke().toMultiLineString());
        }
        testResultWithoutActualResults.setInterleavingsActual(actualResults.getInterleavingsKripke().toMultiLineString());
        testResultWithoutActualResults.setModelCheckingActual(allStatesThatHoldForAllModelsStrArr);
    }

    // helpers

    String[] getAllStatesThatHoldForAllModelsStrArr(ArrayList<String> modelList, Kripke kripke, int loops, LabelHash labelHash) {
        ArrayList<StatesThatHoldForModel> statesThatHoldForModelArrList = new ArrayList<>();
        Vertex initialState = kripke.S.get(0);
        ListHelper listHelper = new ListHelper();
        for (String thisModel : modelList) {
            ModelChecker modelChecker = new ModelChecker(thisModel, kripke, initialState, loops, labelHash);
            StatesThatHoldForModel statesThatHoldForModel = modelChecker.getModelCheckResult().getStatesThatHoldForModel();
            statesThatHoldForModelArrList.add(statesThatHoldForModel);
        }
        AllStatesThatHoldForAllModels allStatesThatHoldForAllModels = new AllStatesThatHoldForAllModels(statesThatHoldForModelArrList);
        ArrayList<String> allStatesThatHoldForAllModelsStrArrList = allStatesThatHoldForAllModels.getAllStatesThatHoldForAllModelsStrArrList();
        String[] allStatesThatHoldForAllModelsStrArr = listHelper.listToStringArray(allStatesThatHoldForAllModelsStrArrList);
        return allStatesThatHoldForAllModelsStrArr;
    }

    /*
    String[] getKripkeFromFile(BufferedReader reader, Options options) throws IOException {
        int numLinesToSkip = options.getTesterKripkeLinesToSkip(); // based on the stucture of the tests. if the structure of the tests were changed, this would have to change.
        String[] expected = new String[3];
        for (int i=0; i<numLinesToSkip; i++) { reader.readLine(); }
        expected[0] = reader.readLine();
        expected[1] = reader.readLine();
        expected[2] = reader.readLine();
        return expected;
    }
    */

    String[] getModelCheckingExpectedFromFile(BufferedReader reader, Options options) throws IOException {
        int numLinesToSkip = options.getTesterModelCheckingLinesToSkip(); // based on the stucture of the tests. if the structure of the tests were changed, this would have to change.
        ListHelper listHelper = new ListHelper();
        String line;
        ArrayList<String> modelCheckingTestLines = new ArrayList<>();

        for (int i=0; i<numLinesToSkip; i++) { reader.readLine(); }

        // read to end of file code from https://stackoverflow.com/a/17678946, accessed 5/8/20
        while ((line = reader.readLine()) != null) {
            modelCheckingTestLines.add(line);
        }
        String[] expected = listHelper.listToStringArray(modelCheckingTestLines);

        return expected;
    }




    // getters & setters

    public String getTestFile() {
        return testFile;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestFile(String testFile) {
        this.testFile = testFile;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public String getTestFilePath() {
        return testPath;
    }

    public void setTestFilePath(String testFilePath) {
        this.testPath = testFilePath;
    }
}
