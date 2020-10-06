package controller.types.tester.testerData;

import controller.types.tester.FileTest;
import controller.types.tester.FileTestSet;

public class TesterData {

    private String selectedFile;
    private String[] allFiles;
    private FileTestSet fileTestsSet;
    private FileTest selectedFileTest;
    private TesterMisc testerMisc;


    public TesterData(TesterMisc testerMisc) {
        this.testerMisc = testerMisc;
    }

    public TesterData(String selectedFile, String[] allFiles, FileTestSet fileTestsSet, FileTest selectedFileTest) {
        this.selectedFile = selectedFile;
        this.allFiles = allFiles;
        this.fileTestsSet = fileTestsSet;
        this.selectedFileTest = selectedFileTest;
    }

    public FileTest getSelectedFileTest() {
        return selectedFileTest;
    }

    public FileTestSet getFileTestsSet() {
        return fileTestsSet;
    }

    public String getSelectedFile() {
        return selectedFile;
    }

    public String[] getAllFiles() {
        return allFiles;
    }

    public TesterMisc getTesterMisc() {
        return testerMisc;
    }

    public void setSelectedFileTest(FileTest selectedFileTest) {
        this.selectedFileTest = selectedFileTest;
    }

    public void setTesterMisc(TesterMisc testerMisc) {
        this.testerMisc = testerMisc;
    }

}
