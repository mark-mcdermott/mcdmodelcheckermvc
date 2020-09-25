package controller.types.tester.testerData;

import controller.types.tester.FileTest;
import controller.types.tester.FileTestSet;

public class TesterData {

    private String selectedFile;
    private String[] allFiles;
    private FileTestSet fileTestsSet;
    private FileTest selectedFileTest;

    public TesterData(String selectedFile, String[] allFiles, FileTestSet fileTestsSet, FileTest selectedFileTest) {
        this.selectedFile = selectedFile;
        this.allFiles = allFiles;
        this.fileTestsSet = fileTestsSet;
        this.selectedFileTest = selectedFileTest;
    }

}
