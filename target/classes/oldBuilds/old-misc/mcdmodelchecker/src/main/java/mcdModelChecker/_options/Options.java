package mcdModelChecker._options;

import java.awt.*;

/**
 * Program options are set here and can be changed here. Height, width, etc.
 */
public class Options {

    // debug setting
    Boolean debug = false;              /** When debug is on, translation stops after <code>targetNumNodesExpanded</code> number of nodes have been expanded */
    int targetNumNodesExpanded = 133;   /** number expanded nodes to stop translating at when <code>debug</code> is on */

    // window size
    private int windowWidth = 1650;                             /** App's window height. Fixed, so change for different monitor sizes, etc */
    private int windowHeight = 950;                             /** App's window height. Fixed, so change for different monitor sizes, etc */

    // file paths
    private String pathToXmlDir = "src/main/resources/xml/";    /** Path to xml/ljx files (.ljx is Little-JIL's version of xml files) */
    private String pathToKrpDir = "src/main/resources/krp/";    /** Path to .krp files (these are kripke structures, for testing) */
    private String pathToTests = "src/main/resources/tests/";   /** Path to tests used on the tester page (checking that the translations & model checking are correct) */
    private String[] xmlFileOrder = {"OneStep.ljx","TwoSteps.ljx","ThreeSteps.ljx","FourSteps.ljx","FiveSteps.ljx","ParTwoSteps.ljx","ParThreeStep.ljx","TransParTwoSubsteps.ljx","TransParThreeSubsteps.ljx","ThreeStepsTry.ljx","SeqTest.ljx","choice-two-steps.ljx","covid-hospitalization-simple.ljx","C++ParallelExample.ljx","C++ParallelExampleSimplified.ljx","CodeBlue-Parallel-Extra-Transition.ljx","CodeBlue-Parallel.ljx","Covid-19-Code-Blue.ljx","covid-hospitalization.ljx","Intubation-Sequential.ljx","NestingTest.ljx","NestingTestSimplified.ljx","ProcessChecks.ljx","ProcessTransfers.ljx","TransSeqTwoSubsteps.ljx","ventilator-order-simple.ljx","ventilator-order.ljx"}; /** This is the order the file list files will show in, if found */
    private String[] models1Var = {"⊤","⊥","p","¬p","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))"}; /** Models that populate the models list when the selected file only has one property */
    private String[] models2Var = {"∧(p,q)","v(p,q)","→(p,q)","AG(→(p,q))","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))","AG(p→q)","EG(pq)","E[pUq]","A[pUq]","E[qUp]","A[qUp]","A[¬qUp]"}; /** Models that populate the models list when the selected file has two properties */

    // fonts
    private String font = "Verdana";        /** Font used throughout the app content */
    private int largeFontSize = 13;         /** Large font size is used on the instructions (i think) */
    private int sectionTitleFontSize = 11;  /** Section title font size is for the titles above the sidebar lists */
    private int listFontSize = 10;          /** List font size is used for the elements in a selection list (in the left sidebar) */

    // colors
    Color mainPanelBackgroundColor = Color.white;          /** Color of the background of the whole window (i think) */
    Color selectedListItemColor = Color.decode("#5e7f85"); /** Color of the highlighted item in a selection list (#5e7f85 is a a gray/blue) */
    // TODO: should probably move more of the hard coded colors out to here

    int analyzerListVertMultiplier = 12;    /** Factor to multiply selected list index by for setting the vertical scrollbar position so selected index shows (this is still a little wonky, I think) */

    /**
     *
     * @return windowHeight
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     *
     * @return windowWidth
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Getter for the list of models that have one property in them
     *
     * @return models1Var
     */
    public String[] getModels1Var() {
        return models1Var;
    }

    /**
     * Getter for the list of models that have two properties in them
     *
     * @return models2Var
     */
    public String[] getModels2Var() {
        return models2Var;
    }

    public String getPathToXmlDir() {
        return pathToXmlDir;
    }

    public String[] getXmlFileOrder() {
        return xmlFileOrder;
    }

    public Boolean getDebug() {
        return debug;
    }

    public int getTargetNumNodesExpanded() {
        return targetNumNodesExpanded;
    }

}
