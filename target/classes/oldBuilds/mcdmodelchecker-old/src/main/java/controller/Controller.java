package controller;

import _options.Options;
import controller.dataTypes.ctlHelpers.ModelCheckResult;
import controller.dataTypes.graphHelpers.DisplayGraphsEnum;
import controller.dataTypes.graphHelpers.FileSelectResult;
import controller.dataTypes.graphHelpers.GraphModelEnum;
import controller.dataTypes.graphHelpers.LabelHash;
import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphItems.Vertex;
import controller.dataTypes.pageHelpers.AnalyzerState;
import controller.dataTypes.pageHelpers.PageEnum;
import controller.helpers.McdException;
import controller.helpers.ListHelper;
import model.Model;
import org.xml.sax.SAXException;
import view.renderPages.MainPage;
import view.View;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL;
import static controller.dataTypes.pageHelpers.AnalyzerState.*;
import static controller.dataTypes.pageHelpers.PageEnum.ANALYZER_PAGE;
import static controller.dataTypes.pageHelpers.PageEnum.TESTER_PAGE;
import static controller.helpers.LineNumber.getLineNumber;

public class Controller {

    MainPage mainPage;

    public Controller(Model model, View view, Options options) throws IOException, ParseException, McdException, ParserConfigurationException, SAXException, BadLocationException {
        mainPage = initalPageRender(model, view, options);
    }

    public MainPage initalPageRender(Model model, View view, Options options) throws IOException, ParseException, McdException, ParserConfigurationException, SAXException, BadLocationException {
        PageEnum whichPage = options.getDefaultPageToRender();
        if (whichPage == ANALYZER_PAGE) {
            analyzerPage(SELECT_FILE, model, view, options, true, GRAPH_MODEL);
        } else if (whichPage == TESTER_PAGE) {
            testerPage(mainPage, model, view, options);
        }
        return mainPage;
    }

    public void renderPage(PageEnum whichPage, MainPage mainPage, Model model, View view, Options options, GraphModelEnum graphModelEnum) throws IOException, ParseException, McdException, ParserConfigurationException, SAXException, BadLocationException {
        if (whichPage == ANALYZER_PAGE) {
            analyzerPage(SELECT_FILE, model, view, options, false, graphModelEnum);
        } else if (whichPage == TESTER_PAGE) {
            testerPage(mainPage, model, view, options);
        }
    }

    private void analyzerPage(AnalyzerState analyzerState, Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws IOException, ParseException, McdException, SAXException, ParserConfigurationException, BadLocationException {

        if (options.getDefaultGraphsToRender() != null) {
            setDisplayGraphOptionInModel(options.getDefaultGraphsToRender(), model, graphModelEnum);
        }

        // if selected file/model are already set in options file, set model settings and then set local analyzerState
        if (options.getDefaultFileSelection() != null || options.getDefaultModelSelection() != null) {
            setupAnalyzerPageForFileOverrideInOptions(model, view, options, analyzerState, graphModelEnum);
            // i believe this only runs on the inital run, so no need to duplicate everything for a comparison graph (initial run set to "all 3 graphs" & not a comparison graph display)

            if (options.getDefaultModelSelection() != null) {
                setOptionModelInTheModel(model, view, options, analyzerState, graphModelEnum);
                runModelCheckAndStoreResults(options.getDefaultModelSelection(), model, view.getMainPage().getLabelHash(), graphModelEnum);
                analyzerState = DISPLAY_RESULTS;
            } else {
                analyzerState = SELECT_MODEL;
            }
        }

        // render different page states, depending on analyzerState
        switch (analyzerState) {
            case SELECT_FILE:
                analyzerPageFileState(model, view, options, isInitialRender, graphModelEnum);
                break;
            case SELECT_MODEL:
                analyzerPageModelState(model, view, options, isInitialRender, graphModelEnum);
                break;
             case DISPLAY_RESULTS:
                 analyzerPageResultsState(model, view, options, isInitialRender, graphModelEnum);
                break;
            default:
                new McdException(getLineNumber() + ": Analyzer state not recognized");
                break;
        }
    }

    public void setDisplayGraphOptionInModel(DisplayGraphsEnum graphsEnum, Model model, GraphModelEnum graphModelEnum) {
        model.setSelectedDisplayGraph(graphsEnum);
    }

    public void setupAnalyzerPageForFileOverrideInOptions(Model model, View view, Options options, AnalyzerState analyzerState, GraphModelEnum graphModelEnum) throws IOException, ParseException, SAXException, McdException, ParserConfigurationException {
        model.setModelToInitialDefaults(options);
        analyzerSetModelToDefaults(model, options, graphModelEnum);
        String selectedFiles[] = new String[] { options.getDefaultFileSelection() };
        // String selectedDisplayGraph = options.getAnalyzerDisplayGraphListItems();
        DisplayGraphsEnum selectedDisplayGraph = options.getDefaultGraphsToRender();
        model.getGraphModelFromEnum(graphModelEnum).setFileSelectResult(new FileSelectResult(selectedFiles, selectedDisplayGraph, null, model, view, options, false, false));
        model.getGraphModelFromEnum(graphModelEnum).setSelectedStateToInitialState();
    }

    public void setOptionModelInTheModel(Model model, View view, Options options, AnalyzerState analyzerState, GraphModelEnum graphModelEnum) throws IOException, ParseException, SAXException, McdException, ParserConfigurationException {
        String selectedModel = options.getDefaultModelSelection();
        model.setSelectedModelAndModelIndex(selectedModel, options);
    }

    public static ModelCheckResult runModelChecker(String selectedModel, Model model, LabelHash labelHash, GraphModelEnum graphModelEnum) {
        // get items needed for the model checking from the model
        Kripke kripke = model.getGraphModelFromEnum(graphModelEnum).getInterleavingsKripke();
        int loops = model.getAnalyzerLoopsNumber();
        Vertex stateToCheck = model.getGraphModelFromEnum(graphModelEnum).getSelectedState();

        // run the model checker
        ModelChecker modelChecker = new ModelChecker(selectedModel, kripke, stateToCheck, loops, labelHash);
        ModelCheckResult modelCheckResult = modelChecker.getModelCheckResult();
        return modelCheckResult;
    }

    public static void runModelCheckAndStoreResults(String selectedModel, Model model, LabelHash labelHash, GraphModelEnum graphModelEnum) {
        // run model checker and get the results
        ModelCheckResult modelCheckResult = runModelChecker(selectedModel, model, labelHash, graphModelEnum);

        // set the results in the model
        model.setSelectedModel(selectedModel);
        model.getGraphModelFromEnum(graphModelEnum).setModelCheckResult(modelCheckResult);
    }

    public void analyzerPageFileState(Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws FileNotFoundException, ParseException, McdException, BadLocationException {
        model.setModelToInitialDefaults(options);
        analyzerSetModelToDefaults(model, options, graphModelEnum);
        view.getMainPage().renderAnalyzerPage(SELECT_FILE, model, view, options, isInitialRender, graphModelEnum);
    }

    public void analyzerPageModelState(Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws McdException, BadLocationException {
        view.getMainPage().renderAnalyzerPage(SELECT_MODEL, model, view, options, isInitialRender, graphModelEnum);
        view.getMainPage().setListeners(ANALYZER_PAGE, SELECT_MODEL, model, view, options, graphModelEnum);
    }

    public void analyzerPageResultsState(Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws McdException, BadLocationException {
        view.getMainPage().renderAnalyzerPage(DISPLAY_RESULTS, model, view, options, isInitialRender, graphModelEnum);
        view.getMainPage().setListeners(ANALYZER_PAGE, DISPLAY_RESULTS, model, view, options, graphModelEnum);
    }

    public void testerPage(MainPage mainPage, Model model, View view, Options options) {
        // model.setModelToInitialDefaults(options);
    }

    public void analyzerSetModelToDefaults(Model model, Options options, GraphModelEnum graphModelEnum) throws ParseException, FileNotFoundException {
        analyzerShellSetModelTextFromOptions(model, options, graphModelEnum);
        analyzerShellSetFileAndModelListsFromDisk(model, options, graphModelEnum);
    }

    public void analyzerShellSetModelTextFromOptions(Model model, Options options, GraphModelEnum graphModelEnum) {
        model.setAnalyzerButtonText(options.getButtonTextAnalyzer());
        model.setTesterButtonText(options.getButtonTextTester());
        model.setAnalyzerInstructionsText(options.getAnalyzerInstructionsText());
        model.setAnalyzerFilesTitle(options.getAnalyzerFilesTitle());
        model.setAnalyzerModelsTitle(options.getAnalyzerModelsTitle());
        model.setAnalyzerLoopsTitle(options.getAnalyzerLoopsTitle());
        model.setAnalyzerLoopsNumber(options.getAnalyzerLoopsNumber());
        model.setAnalyzerStatesTitle(options.getAnalyzerStatesTitle());
        model.setAnalyzerLabelsTitle(options.getAnalyzerLabelsTitle());
        model.setAnalyzerResultsTitle(options.getAnalyzerResultsTitle());
        model.setXmlGraphTitle(options.getXmlGraphTitle());
        model.setTranslationGraphTitle(options.getTranslationGraphTitle());
        model.setInterleavingsGraphTitle(options.getInterleavingsGraphTitle());
    }

    public void analyzerShellSetFileAndModelListsFromDisk(Model model, Options options, GraphModelEnum graphModelEnum) throws ParseException, FileNotFoundException {
        ListHelper listHelper = new ListHelper();
        // String[] modelFileList = listHelper.fetchFileList(options.getPathToXmlDir());
        String xmlPath = options.getPathToXmlDir();
        String krpPath = options.getPathToKrpDir();
        String[] modelFileList = listHelper.fetchFileListFromTwoDirs(xmlPath, krpPath);
        model.setFileList(modelFileList);
        model.setAnalyzerModelsList(listHelper.textFileLinesToArr(options.getModelsPath1Var()));
    }

}
