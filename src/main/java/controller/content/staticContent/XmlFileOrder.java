package controller.content.staticContent;

public class XmlFileOrder {

    String[] fileOrder;

    public XmlFileOrder() {
        // fileOrder = new String[] {"TransSeqTwoSubsteps.ljx"};
        // fileOrder = new String[] {"OneStep.ljx","TwoSteps.ljx","ThreeSteps.ljx","FourSteps.ljx","FiveSteps.ljx","SeqTest.ljx","ParTwoSteps.ljx","ParThreeStep.ljx","TransSeqTwoSubsteps.ljx","TransParTwoSubsteps.ljx"};
        // fileOrder = new String[] {"ProcessChecks2.ljx", "OneStep.ljx","TwoSteps.ljx","ThreeSteps.ljx","FourSteps.ljx","FiveSteps.ljx","SeqTest.ljx","ParTwoSteps.ljx","ParThreeStep.ljx","TransSeqTwoSubsteps.ljx","TransParTwoSubsteps.ljx"};
        fileOrder = new String[] {"Bank-Parallel.ljx"};
        // fileOrder = new String[] {"ProcessTransfers.ljx", "ProcessChecks.ljx"};
        // fileOrder = new String[] {"covid-test-no-right-side.ljx"};
        // fileOrder = new String[] {"ParTwoSteps.ljx","ParWith3Leaves.ljx","ParWith4Leaves.ljx","ParWith5Leaves.ljx","ParWith10Leaves.ljx","ParWith25Leaves.ljx"};
        // fileOrder = new String[] {"choice-two-steps.ljx"};
        //fileOrder = new String[] {"TwoSteps.ljx"};
    }

    public String[] getFileOrder() {
        return fileOrder;
    }

}
