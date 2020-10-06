package view.renderPages;

import _options.Options;
import controller.dataTypes.graphHelpers.GraphModelEnum;
import controller.dataTypes.testHelpers.FileTest;
import controller.dataTypes.testHelpers.FileTestSet;
import controller.dataTypes.testHelpers.TestResult;
import controller.helpers.ListHelper;
import controller.helpers.McdException;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static controller.dataTypes.pageHelpers.PageEnum.TESTER_PAGE;

public class TesterPage {

    public void initTesterPage(Model model, View view, Options options, GraphModelEnum graphModelEnum) throws IOException, McdException, ParserConfigurationException, SAXException {
        initTesterModel(model, view, options);
        initTesterComponents(view, options);
        setTesterComponentsLayoutModes(view);
        initTesterStyles(view);
        setTesterStyles(view, options);
        setTesterContent(model, view, options);
        setTesterListSelections(model, view, options);
        addTesterComponents(view);
        view.getMainPage().setVisible();
        view.getMainPage().setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
    }

    public void reinitTesterPage(Model model, View view, Options options, GraphModelEnum graphModelEnum) throws IOException, McdException, ParserConfigurationException, SAXException {
        updateTesterModel(model, view, options);
        initTesterComponents(view, options);
        setTesterComponentsLayoutModes(view);
        initTesterStyles(view);
        setTesterStyles(view, options);
        setTesterContent(model, view, options);
        addTesterComponents(view);
        view.getMainPage().setVisible();
        view.getMainPage().setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
    }

    // can only run for a fresh tester page, not when a file is clicked on the testerpage
    public void initTesterModel(Model model, View view, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
        // use first file in file list and run the tests on it
        String xmlFile = getInitialSelectedFile(model);
        String xmlPath = options.getPathToTests();
        model.setSelectedFileTest(getFileTest(xmlFile, xmlPath, model, view, options));
        // get tests for all xml files
        model.setFileTestsSet(getFileTestsSet(model, view, options));
    }

    // this runs when a file is clicked on the tester page
    public void updateTesterModel(Model model, View view, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
        // get tests for the selected file in the sidebar
        String xmlFile = model.getSelectedFiles().get(0);
        String xmlPath = options.getPathToTests();
        model.setSelectedFileTest(getFileTest(xmlFile, xmlPath, model, view, options));
        // get tests for all xml files
        model.setFileTestsSet(getFileTestsSet(model, view, options));
    }

    public void initTesterComponents(View view, Options options) {
        view.getMainPage().mainPanel = new JPanel();
        view.getMainPage().sidebarPanel = new JPanel(new GridBagLayout());
        view.getMainPage().col1 = new JPanel(new BorderLayout());
        view.getMainPage().col1Inner = new JPanel(new GridBagLayout());
        view.getMainPage().col1LeftPadding = new JPanel();
        view.getMainPage().buttonsPanel = new JPanel();
        view.getMainPage().analyzerButton = new JButton(options.getButtonTextAnalyzer());
        view.getMainPage().testerButton = new JButton((options.getButtonTextTester()));
        view.getMainPage().fileLabel = new JLabel(options.getTesterFileLabel());
        view.getMainPage().fileList = new JList();
        view.getMainPage().individualResultLabel = new JLabel(options.getTesterIndividualTestLabel());
        view.getMainPage().individualResultTextarea = new JTextArea();
        view.getMainPage().allResultsLabel = new JLabel(options.getTesterAggregateTestLabel());
        view.getMainPage().allResultsTextarea = new JTextArea();
        view.getMainPage().col1RightPadding = new JPanel();
        view.getMainPage().testsPanel = new JPanel();
        view.getMainPage().testsXmlPanel = new JPanel();
        view.getMainPage().testsXmlTitle = new JLabel();
        view.getMainPage().testsXmlExpectedLabel = new JLabel();
        view.getMainPage().testsXmlExpectedList = new JList();
        view.getMainPage().testsXmlExpectedScrollPane = new JScrollPane();
        view.getMainPage().testsXmlActualScrollPane = new JScrollPane();
        view.getMainPage().testsXmlExpectedTextArea = new JTextArea();
        view.getMainPage().testsXmlActualTextArea = new JTextArea();
        view.getMainPage().testsTranslationExpectedScrollPane = new JScrollPane();
        view.getMainPage().testsTranslationActualScrollPane = new JScrollPane();
        view.getMainPage().testsTranslationExpectedTextArea = new JTextArea();
        view.getMainPage().testsTranslationActualTextArea = new JTextArea();
        view.getMainPage().testsInterleavingsExpectedScrollPane = new JScrollPane();
        view.getMainPage().testsInterleavingsActualScrollPane = new JScrollPane();
        view.getMainPage().testsInterleavingsExpectedTextArea = new JTextArea();
        view.getMainPage().testsInterleavingsActualTextArea = new JTextArea();
        view.getMainPage().testsXmlExpectedTextAreaText = new String();
        view.getMainPage().testsXmlActualTextAreaText = new String();
        view.getMainPage().testsTranslationExpectedTextAreaText = new String();
        view.getMainPage().testsTranslationActualTextAreaText = new String();
        view.getMainPage().testsInterleavingsExpectedTextAreaText = new String();
        view.getMainPage().testsInterleavingsActualTextAreaText = new String();
        view.getMainPage().testsModelCheckingExpectedTextAreaText = new String();
        view.getMainPage().testsModelCheckingActualTextAreaText = new String();
        view.getMainPage().testsXmlActualLabel = new JLabel();
        view.getMainPage().testsXmlActualList = new JList();
        view.getMainPage().testsTranslationPanel = new JPanel();
        view.getMainPage().testsTranslationTitle = new JLabel();
        view.getMainPage().testsTranslationExpectedLabel = new JLabel();
        view.getMainPage().testsTranslationExpectedList = new JList();
        view.getMainPage().testsTranslationActualLabel = new JLabel();
        view.getMainPage().testsTranslationActualList = new JList();
        view.getMainPage().testsInterleavingsPanel = new JPanel();
        view.getMainPage().testsInterleavingsTitle = new JLabel();
        view.getMainPage().testsInterleavingsExpectedLabel = new JLabel();
        view.getMainPage().testsInterleavingsExpectedList = new JList();
        view.getMainPage().testsInterleavingsActualLabel = new JLabel();
        view.getMainPage().testsInterleavingsActualList = new JList();
        view.getMainPage().testsModelCheckingPanel = new JPanel();
        view.getMainPage().testsModelCheckingTitle = new JLabel();
        view.getMainPage().testsModelCheckingExpectedLabel = new JLabel();
        view.getMainPage().testsModelCheckingExpectedList = new JList();
        view.getMainPage().testsModelCheckingExpectedScrollPane = new JScrollPane();
        view.getMainPage().testsModelCheckingExpectedTextArea = new JTextArea();
        view.getMainPage().testsModelCheckingExpectedSplitPane = new JSplitPane();
        view.getMainPage().testsModelCheckingActualScrollPane = new JScrollPane();
        view.getMainPage().testsModelCheckingActualLabel = new JLabel();
        view.getMainPage().testsModelCheckingActualList = new JList();
        view.getMainPage().testsModelCheckingActualTextArea = new JTextArea();
        view.getMainPage().testerSpacerRight = new JPanel();
        view.getMainPage().testerSpacerBottom0 = new JPanel();
        view.getMainPage().testerSpacerBottom1 = new JPanel();
        view.getMainPage().testerSpacerBottom2 = new JPanel();
        view.getMainPage().testerSpacerBottom3 = new JPanel();
    }

    public void setTesterComponentsLayoutModes(View view) {
        view.getMainPage().getFrame().setLayout(new BorderLayout());
        view.getMainPage().mainPanel.setLayout(new BorderLayout());
        view.getMainPage().testsPanel.setLayout(new GridBagLayout());
        view.getMainPage().testsXmlPanel.setLayout(new BorderLayout());
        view.getMainPage().testsTranslationPanel.setLayout(new BorderLayout());
        view.getMainPage().testsInterleavingsPanel.setLayout(new BorderLayout());
        view.getMainPage().testsModelCheckingPanel.setLayout(new BorderLayout());
    }

    public void initTesterStyles(View view) {
        view.getMainPage().columnStyle = new GridBagConstraints();
        view.getMainPage().buttonsPanelStyle = new GridBagConstraints();
        view.getMainPage().col1InnerStyle = new GridBagConstraints();
        view.getMainPage().fileLabelStyle = new GridBagConstraints();
        view.getMainPage().fileListStyle = new GridBagConstraints();
        view.getMainPage().individualResultLabelStyle = new GridBagConstraints();
        view.getMainPage().individualResultStyle = new GridBagConstraints();
        view.getMainPage().aggregateResultLabel = new GridBagConstraints();
        view.getMainPage().aggregateResultStyle = new GridBagConstraints();
        view.getMainPage().layoutSettings = new GridBagConstraints();
        view.getMainPage().xmlPanelGridPosition = new GridBagConstraints();
        view.getMainPage().translationPanelGridPosition = new GridBagConstraints();
        view.getMainPage().interleavingsPanelGridPosition = new GridBagConstraints();
        view.getMainPage().modelCheckingPanelGridPosition = new GridBagConstraints();
        view.getMainPage().testerSpacerRightStyle = new GridBagConstraints();
        view.getMainPage().testerSpacerBottom0Style = new GridBagConstraints();
        view.getMainPage().testerSpacerBottom1Style = new GridBagConstraints();
        view.getMainPage().testerSpacerBottom2Style = new GridBagConstraints();
        view.getMainPage().testerSpacerBottom3Style = new GridBagConstraints();
    }

    public void setTesterStyles(View view, Options options) {
        // pagewide styles
        view.getMainPage().layoutSettings.anchor = GridBagConstraints.NONE;
        view.getMainPage().columnStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().titleFont = new Font(options.getFont(), Font.PLAIN, options.getLargeFontSize());
        view.getMainPage().labelFont = new Font(options.getFont(), Font.PLAIN, options.getSectionTitleFontSize());
        view.getMainPage().listFont = new Font(options.getFont(), Font.PLAIN, options.getListFontSize());
        view.getMainPage().fileLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().fileListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().individualResultLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().individualResultStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().aggregateResultLabel.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().aggregateResultStyle.fill = GridBagConstraints.HORIZONTAL;
        // col 1
        view.getMainPage().col1InnerStyle.gridx = 0;
        view.getMainPage().col1InnerStyle.gridy = 0;
        view.getMainPage().col1InnerStyle.anchor = GridBagConstraints.NORTH;
        view.getMainPage().col1InnerStyle.weighty = 1;
        view.getMainPage().buttonsPanel.setBorder(BorderFactory.createEmptyBorder(7, 10, 6, 10));
        view.getMainPage().buttonsPanelStyle.gridx = 1;
        view.getMainPage().buttonsPanelStyle.gridy = 0;
        view.getMainPage().fileLabelStyle.gridx = 1;
        view.getMainPage().fileLabelStyle.gridy = 1;
        view.getMainPage().fileLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        view.getMainPage().fileLabel.setFont(view.getMainPage().labelFont);
        view.getMainPage().fileList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        view.getMainPage().fileList.setFont(view.getMainPage().listFont);
        view.getMainPage().fileList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().fileListStyle.gridx = 1;
        view.getMainPage().fileListStyle.gridy = 2;
        view.getMainPage().individualResultLabelStyle.gridx = 1;
        view.getMainPage().individualResultLabelStyle.gridy = 3;
        view.getMainPage().individualResultLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().individualResultLabel.setFont(view.getMainPage().labelFont);
        view.getMainPage().individualResultStyle.gridx = 1;
        view.getMainPage().individualResultStyle.gridy = 4;
        view.getMainPage().individualResultTextarea.setFont(view.getMainPage().labelFont);
        view.getMainPage().individualResultTextarea.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().individualResultBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        view.getMainPage().individualResultTextarea.setBorder(BorderFactory.createCompoundBorder(view.getMainPage().individualResultBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        view.getMainPage().aggregateResultLabel.gridx = 1;
        view.getMainPage().aggregateResultLabel.gridy = 5;
        view.getMainPage().allResultsLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        view.getMainPage().allResultsLabel.setFont(view.getMainPage().titleFont);
        view.getMainPage().aggregateResultStyle.gridx = 1;
        view.getMainPage().aggregateResultStyle.gridy = 6;
        view.getMainPage().allResultsTextarea.setFont(view.getMainPage().labelFont);
        view.getMainPage().allResultsBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        view.getMainPage().allResultsTextarea.setBorder(BorderFactory.createCompoundBorder(view.getMainPage().allResultsBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        // col 1 right padding - invisible spacer column for padding
        view.getMainPage().columnStyle.gridx = 2;
        view.getMainPage().columnStyle.gridy = 0;
        // tests panel
        view.getMainPage().testsPanel.setBackground(Color.white);
        view.getMainPage().testsPanel.setPreferredSize(new Dimension(500, 800));
        // xml panel
        view.getMainPage().xmlPanelGridPosition.gridx = 0;
        view.getMainPage().xmlPanelGridPosition.gridy = 0;
        view.getMainPage().xmlPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().testsXmlPanel.setPreferredSize(new Dimension(options.getTesterExpectedActualPanelWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsXmlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsXmlTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        view.getMainPage().testsXmlExpectedTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsXmlExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsXmlExpectedScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsXmlActualTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsXmlActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsXmlActualScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));

        // translation panel
        view.getMainPage().translationPanelGridPosition.gridx = 1;
        view.getMainPage().translationPanelGridPosition.gridy = 0;
        view.getMainPage().translationPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().testsTranslationPanel.setPreferredSize(new Dimension(options.getTesterExpectedActualPanelWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsTranslationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsTranslationTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        view.getMainPage().testsTranslationExpectedTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsTranslationExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsTranslationExpectedScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsTranslationActualTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsTranslationActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsTranslationActualScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));

        // interleavings panel
        view.getMainPage().interleavingsPanelGridPosition.gridx = 2;
        view.getMainPage().interleavingsPanelGridPosition.gridy = 0;
        view.getMainPage().interleavingsPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().testsInterleavingsPanel.setPreferredSize(new Dimension(options.getTesterExpectedActualPanelWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsInterleavingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsInterleavingsTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        view.getMainPage().testsInterleavingsExpectedTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsInterleavingsExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsInterleavingsExpectedScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsInterleavingsActualTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsInterleavingsActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsInterleavingsActualScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));

        // model checking panel
        view.getMainPage().modelCheckingPanelGridPosition.gridx = 3;
        view.getMainPage().modelCheckingPanelGridPosition.gridy = 0;
        view.getMainPage().modelCheckingPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().testsModelCheckingPanel.setPreferredSize(new Dimension(options.getTesterExpectedActualPanelWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsModelCheckingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsModelCheckingTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        view.getMainPage().testsModelCheckingExpectedLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsModelCheckingExpectedLabel.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingExpectedList.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsModelCheckingExpectedList.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingActualLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        view.getMainPage().testsModelCheckingActualLabel.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingActualList.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsModelCheckingActualList.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingExpectedTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsModelCheckingExpectedScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));
        view.getMainPage().testsModelCheckingActualTextArea.setFont(view.getMainPage().titleFont);
        view.getMainPage().testsModelCheckingActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        view.getMainPage().testsModelCheckingActualScrollPane.setPreferredSize(new Dimension(options.getTesterExpectedActualTextAreaWidth(), options.getTesterExpectedActualPanelHeight()));

        // spacer panels
        view.getMainPage().testerSpacerRightStyle.gridx = 4;
        view.getMainPage().testerSpacerRightStyle.gridy = 0;
        view.getMainPage().testerSpacerRightStyle.weightx = 1;
        view.getMainPage().testerSpacerBottom0Style.gridx = 0;
        view.getMainPage().testerSpacerBottom0Style.gridy = 1;
        view.getMainPage().testerSpacerBottom0Style.weighty = 1;
        view.getMainPage().testerSpacerBottom1Style.gridx = 1;
        view.getMainPage().testerSpacerBottom1Style.gridy = 1;
        view.getMainPage().testerSpacerBottom1Style.weighty = 1;
        view.getMainPage().testerSpacerBottom2Style.gridx = 2;
        view.getMainPage().testerSpacerBottom2Style.gridy = 1;
        view.getMainPage().testerSpacerBottom2Style.weighty = 1;
        view.getMainPage().testerSpacerBottom3Style.gridx = 3;
        view.getMainPage().testerSpacerBottom3Style.gridy = 1;
        view.getMainPage().testerSpacerBottom3Style.weighty = 1;
    }

    public void setTesterContent(Model model, View view, Options options) throws IOException, McdException, ParserConfigurationException, SAXException {
        view.getMainPage().getFrame().setTitle(options.getTesterPageTitle());

        // pull the results out of the model
        FileTest selectedFileTest = model.getSelectedFileTest();
        FileTestSet fileTestSet = model.getFileTestsSet();
        TestResult selectedFileTestResult = selectedFileTest.getTestResult();
        String selectedFilePassFail = selectedFileTestResult.getTestResultStr();
        String allResultsStr = fileTestSet.getPassFailStr();

        // sidebar
        view.getMainPage().fileListArr = model.getFileList();
        view.getMainPage().fileList.setListData(view.getMainPage().fileListArr);
        view.getMainPage().individualResultTextarea.setText(selectedFilePassFail);
        view.getMainPage().allResultsTextarea.setText(allResultsStr);

        // main panel

        // get expected/actual lists for xml, translation and interleavings
        String[] xmlExpectedKripkeStrArr = selectedFileTestResult.getXmlExpected();
        String[] xmlActualKripkeStrArr = selectedFileTestResult.getXmlActual();
        String[] translationExpectedKripkeStrArr = selectedFileTestResult.getTranslationExpected();
        String[] translationActualKripkeStrArr = selectedFileTestResult.getTranslationActual();
        String[] interleavingsExpectedKripkeStrArr = selectedFileTestResult.getInterleavingsExpected();
        String[] interleavingsActualKripkeStrArr = selectedFileTestResult.getInterleavingsActual();
        String[] modelCheckingExpectedResultStrArr = selectedFileTestResult.getModelCheckingExpected();
        String[] modelCheckingActualResultStrArr = model.getSelectedFileTest().getTestResult().getModelCheckingActual();

        // format expected/actual lists for xml, translation and interleavings
        int lineLength = options.getTesterLineLength();
        if (xmlActualKripkeStrArr != null && translationActualKripkeStrArr != null) {
            xmlExpectedKripkeStrArr = wrapStrArrElements(xmlExpectedKripkeStrArr, lineLength);
            xmlActualKripkeStrArr = wrapStrArrElements(xmlActualKripkeStrArr, lineLength);
            translationExpectedKripkeStrArr = wrapStrArrElements(translationExpectedKripkeStrArr, lineLength);
            translationActualKripkeStrArr = wrapStrArrElements(translationActualKripkeStrArr, lineLength);
        }
        interleavingsExpectedKripkeStrArr = wrapStrArrElements(interleavingsExpectedKripkeStrArr, lineLength);
        interleavingsActualKripkeStrArr = wrapStrArrElements(interleavingsActualKripkeStrArr, lineLength);
        modelCheckingExpectedResultStrArr = wrapStrArrElements(modelCheckingExpectedResultStrArr, lineLength);
        modelCheckingActualResultStrArr = wrapStrArrElements(modelCheckingActualResultStrArr, lineLength);

        // get expected/actual text for xml, translation and interleavings
        String xmlExpectedTitle = options.getTesterXmlExpectedTitle();
        String xmlActualTitle = options.getTesterXmlActualTitle();
        String translationExpectedTitle = options.getTesterTranslationExpectedTitle();
        String translationActualTitle = options.getTesterTranslationActualTitle();
        String interleavingsExpectedTitle = options.getTesterInterleavingsExpectedTitle();
        String interleavingsActualTitle = options.getTesterInterleavingsActualTitle();
        String modelCheckingExpectedTitle = options.getTesterModelCheckingExpectedTitle();
        String modelCheckingActualTitle = options.getTesterModelCheckingActualTitle();

        view.getMainPage().testsXmlTitle.setText(options.getTesterXmlTitle());
        view.getMainPage().testsTranslationTitle.setText(options.getTesterTranslationTitle());
        view.getMainPage().testsInterleavingsTitle.setText(options.getTesterInterleavingsTitle());
        view.getMainPage().testsModelCheckingTitle.setText(options.getTesterModelCheckingTitle());

        if (xmlActualKripkeStrArr != null && translationActualKripkeStrArr != null) {
            view.getMainPage().testsXmlExpectedTextAreaText = xmlExpectedTitle + "\n\n" + strArrToString(xmlExpectedKripkeStrArr);
            view.getMainPage().testsXmlActualTextAreaText = xmlActualTitle + "\n\n" + strArrToString(xmlActualKripkeStrArr);
            view.getMainPage().testsTranslationExpectedTextAreaText = translationExpectedTitle + "\n\n" + strArrToString(translationExpectedKripkeStrArr);
            view.getMainPage().testsTranslationActualTextAreaText = translationActualTitle + "\n\n" + strArrToString(translationActualKripkeStrArr);
        }
        view.getMainPage().testsInterleavingsExpectedTextAreaText = interleavingsExpectedTitle + "\n\n" + strArrToString(interleavingsExpectedKripkeStrArr);
        view.getMainPage().testsInterleavingsActualTextAreaText = interleavingsActualTitle + "\n\n" + strArrToString(interleavingsActualKripkeStrArr);
        view.getMainPage().testsModelCheckingExpectedTextAreaText = modelCheckingExpectedTitle + "\n\n" + strArrToString(modelCheckingExpectedResultStrArr);
        view.getMainPage().testsModelCheckingActualTextAreaText = modelCheckingActualTitle + "\n\n" + strArrToString(modelCheckingActualResultStrArr);

        view.getMainPage().testsXmlExpectedTextArea.setText(view.getMainPage().testsXmlExpectedTextAreaText);
        view.getMainPage().testsXmlActualTextArea.setText(view.getMainPage().testsXmlActualTextAreaText);
        view.getMainPage().testsTranslationExpectedTextArea.setText(view.getMainPage().testsTranslationExpectedTextAreaText);
        view.getMainPage().testsTranslationActualTextArea.setText(view.getMainPage().testsTranslationActualTextAreaText);
        view.getMainPage().testsInterleavingsExpectedTextArea.setText(view.getMainPage().testsInterleavingsExpectedTextAreaText);
        view.getMainPage().testsInterleavingsActualTextArea.setText(view.getMainPage().testsInterleavingsActualTextAreaText);
        view.getMainPage().testsModelCheckingExpectedTextArea.setText(view.getMainPage().testsModelCheckingExpectedTextAreaText);
        view.getMainPage().testsModelCheckingActualTextArea.setText(view.getMainPage().testsModelCheckingActualTextAreaText);

        view.getMainPage().testsXmlExpectedScrollPane.setViewportView(view.getMainPage().testsXmlExpectedTextArea);
        view.getMainPage().testsXmlActualScrollPane.setViewportView(view.getMainPage().testsXmlActualTextArea);
        view.getMainPage().testsTranslationExpectedScrollPane.setViewportView(view.getMainPage().testsTranslationExpectedTextArea);
        view.getMainPage().testsTranslationActualScrollPane.setViewportView(view.getMainPage().testsTranslationActualTextArea);
        view.getMainPage().testsInterleavingsExpectedScrollPane.setViewportView(view.getMainPage().testsInterleavingsExpectedTextArea);
        view.getMainPage().testsInterleavingsActualScrollPane.setViewportView(view.getMainPage().testsInterleavingsActualTextArea);
        view.getMainPage().testsModelCheckingExpectedScrollPane.setViewportView(view.getMainPage().testsModelCheckingExpectedTextArea);
        view.getMainPage().testsModelCheckingActualScrollPane.setViewportView(view.getMainPage().testsModelCheckingActualTextArea);
    }

    public void addTesterComponents(View view) {
        view.getMainPage().col1Inner.add(view.getMainPage().col1LeftPadding, view.getMainPage().col1InnerStyle);
        view.getMainPage().buttonsPanel.add(view.getMainPage().analyzerButton);
        view.getMainPage().buttonsPanel.add(view.getMainPage().testerButton);
        view.getMainPage().col1Inner.add(view.getMainPage().buttonsPanel, view.getMainPage().buttonsPanelStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().fileLabel, view.getMainPage().fileLabelStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().fileList, view.getMainPage().fileListStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().individualResultLabel, view.getMainPage().individualResultLabelStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().individualResultTextarea, view.getMainPage().individualResultStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().allResultsLabel, view.getMainPage().aggregateResultLabel);
        view.getMainPage().col1Inner.add(view.getMainPage().allResultsTextarea, view.getMainPage().aggregateResultStyle);
        view.getMainPage().col1Inner.add(view.getMainPage().col1RightPadding, view.getMainPage().columnStyle);
        view.getMainPage().col1.add(view.getMainPage().col1Inner, BorderLayout.NORTH);
        view.getMainPage().sidebarPanel.add(view.getMainPage().col1, view.getMainPage().col1InnerStyle);
        view.getMainPage().mainPanel.add(view.getMainPage().sidebarPanel, BorderLayout.WEST);

        view.getMainPage().testsXmlPanel.add(view.getMainPage().testsXmlTitle, BorderLayout.NORTH);
        view.getMainPage().testsXmlPanel.add(view.getMainPage().testsXmlExpectedScrollPane, BorderLayout.WEST);
        view.getMainPage().testsXmlPanel.add(view.getMainPage().testsXmlActualScrollPane, BorderLayout.EAST);
        view.getMainPage().testsPanel.add(view.getMainPage().testsXmlPanel, view.getMainPage().xmlPanelGridPosition);

        view.getMainPage().testsTranslationPanel.add(view.getMainPage().testsTranslationTitle, BorderLayout.NORTH);
        view.getMainPage().testsTranslationPanel.add(view.getMainPage().testsTranslationExpectedScrollPane, BorderLayout.WEST);
        view.getMainPage().testsTranslationPanel.add(view.getMainPage().testsTranslationActualScrollPane, BorderLayout.EAST);
        view.getMainPage().testsPanel.add(view.getMainPage().testsTranslationPanel, view.getMainPage().translationPanelGridPosition);

        view.getMainPage().testsInterleavingsPanel.add(view.getMainPage().testsInterleavingsTitle, BorderLayout.NORTH);
        view.getMainPage().testsInterleavingsPanel.add(view.getMainPage().testsInterleavingsExpectedScrollPane, BorderLayout.WEST);
        view.getMainPage().testsInterleavingsPanel.add(view.getMainPage().testsInterleavingsActualScrollPane, BorderLayout.EAST);
        view.getMainPage().testsPanel.add(view.getMainPage().testsInterleavingsPanel, view.getMainPage().interleavingsPanelGridPosition);

        view.getMainPage().testsModelCheckingPanel.add(view.getMainPage().testsModelCheckingTitle, BorderLayout.NORTH);
        view.getMainPage().testsModelCheckingPanel.add(view.getMainPage().testsModelCheckingExpectedScrollPane, BorderLayout.WEST);
        view.getMainPage().testsModelCheckingPanel.add(view.getMainPage().testsModelCheckingActualScrollPane, BorderLayout.EAST);
        view.getMainPage().testsPanel.add(view.getMainPage().testsModelCheckingPanel, view.getMainPage().modelCheckingPanelGridPosition);

        // spacer panels
        view.getMainPage().testsPanel.add(view.getMainPage().testerSpacerRight, view.getMainPage().testerSpacerRightStyle);
        view.getMainPage().testsPanel.add(view.getMainPage().testerSpacerBottom0, view.getMainPage().testerSpacerBottom0Style);
        view.getMainPage().testsPanel.add(view.getMainPage().testerSpacerBottom1, view.getMainPage().testerSpacerBottom1Style);
        view.getMainPage().testsPanel.add(view.getMainPage().testerSpacerBottom2, view.getMainPage().testerSpacerBottom2Style);
        view.getMainPage().testsPanel.add(view.getMainPage().testerSpacerBottom3, view.getMainPage().testerSpacerBottom3Style);

        // add main panels
        view.getMainPage().mainPanel.add(view.getMainPage().testsPanel);
        view.getMainPage().getFrame().add(view.getMainPage().mainPanel, BorderLayout.NORTH);
    }

    void setTesterListSelections(Model model, View view, Options options) {

    }

    // helper

    String[] wrapStrArrElements(String[] strArr, int lineLength) {
        for (int i=0; i<strArr.length; i++) {
            String string = strArr[i];
            String stringWrapped = wrapString(string, "\n", lineLength) + "\n";
            strArr[i] = stringWrapped;
        }
        return strArr;
    }

    // insert line break every x characters (code from https://stackoverflow.com/questions/537174/putting-char-into-a-java-string-for-each-n-characters/537190#537190)
    static String wrapString(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text);
        int i = 0;
        while (i + period < builder.length() && (i = builder.lastIndexOf(",", i + period)) != -1) {
            builder.replace(i, i + 1, ",\n");
        }
        return builder.toString();
    }

    // helper

    FileTest getFileTest(String xmlFile, String xmlPath, Model model, View view, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
        return new FileTest(xmlFile, xmlPath, model, view, options);
    }

    String getInitialSelectedFile(Model model) {
        return model.getFileList()[0];
    }

    ArrayList<String> getInitialFileArrList(Model model) {
        ArrayList<String> selectedFiles = new ArrayList<>();
        selectedFiles.add(model.getFileList()[0]);
        return selectedFiles;
    }

    // TODO: refactor this to return FileTestSet type
    // ArrayList<FileTest> getFileTestsSet(Model model, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
    FileTestSet getFileTestsSet(Model model, View view, Options options) throws SAXException, McdException, ParserConfigurationException, IOException {
        ListHelper listHelper = new ListHelper();
        ArrayList<FileTest> fileTestsArrList = new ArrayList<>();
        ArrayList<String> xmlFiles = listHelper.stringArrToArrList(model.getFileList());
        String xmlPath = options.getPathToTests();
        for (String xmlFile : xmlFiles) {
            FileTest fileTest = new FileTest(xmlFile, xmlPath, model, view, options);
            fileTestsArrList.add(fileTest);
        }
        FileTestSet fileTestSet = new FileTestSet(fileTestsArrList);
        return fileTestSet;
    }

    String stringInsert(String origStr, char insertChar, int insertPoint) {
        StringBuffer stringBuffer = new StringBuffer(origStr);
        stringBuffer.insert(insertPoint, insertChar);
        return stringBuffer.toString();
    }

    String strArrToString(String[] strArr) {
        String combinedString = "";
        for (String thisString : strArr) {
            combinedString = combinedString + thisString + "\n";
        }
        return combinedString;
    }

    String modelCheckingExpectedActualStrToString(String[] strArr) {
        String combinedString = "";
        for (int i=0; i<strArr.length; i++) {
            if (i==0) {
                combinedString = combinedString + strArr[i] + "\n";
            } else {
                combinedString = combinedString + strArr[i] + ",";
            }
        }
        return combinedString;
    }

}
