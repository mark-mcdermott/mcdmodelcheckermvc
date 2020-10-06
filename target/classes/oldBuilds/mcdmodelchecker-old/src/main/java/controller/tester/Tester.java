package controller.tester;

import _options.Options;
import controller.dataTypes.testHelpers.ExpectedResult;
import controller.dataTypes.testHelpers.FileTest;
import controller.helpers.McdException;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {

    ArrayList<String> testFiles;

    public Tester(Model model, View view, Options options) throws IOException, ParserConfigurationException, McdException, SAXException {

        String[] testFiles = model.getFileList();
        String pathToTests = options.getPathToTests();
        ArrayList<FileTest> fileTests = new ArrayList<>();

        // get expected results from file
        for (String testFile : testFiles) {
            String testFilePath = pathToTests + testFile;
            FileTest fileTest = new FileTest(testFile, testFilePath, model, view, options);
            fileTests.add(fileTest);
        }

    }



    // helper

    public static String[] getThreeLineStrArr(String line, BufferedReader reader) throws IOException {
        String[] expected = new String[3];
        expected[0] = line;
        expected[1] = reader.readLine();
        expected[2] = reader.readLine();
        return expected;
    }

}
