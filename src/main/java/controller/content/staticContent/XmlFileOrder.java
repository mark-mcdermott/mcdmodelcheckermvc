package controller.content.staticContent;

public class XmlFileOrder {

    String[] fileOrder;

    public XmlFileOrder() {
        // fileOrder = new String[] {"OneStep.ljx","TwoSteps.ljx","ThreeSteps.ljx","FourSteps.ljx","FiveSteps.ljx","SeqTest.ljx","ParTwoSteps.ljx","ParThreeStep.ljx","TransSeqTwoSubsteps.ljx","TransParTwoSubsteps.ljx"};
        // fileOrder = new String[] {"OneStep.ljx"};
        fileOrder = new String[] {"Covid.ljx"};
        // fileOrder = new String[] {"Bank-Parallel.ljx"};
    }

    public String[] getFileOrder() {
        return fileOrder;
    }

}
