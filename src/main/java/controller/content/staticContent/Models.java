package controller.content.staticContent;

public class Models {

    String[][] models; // array with two elements, each a String[]: models1Var & models2Var

    public Models() {
        String[] models1Var = {"⊤","⊥","p","¬p","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))"};
        // String[] models2Var = {"A[pUq]","∧(¬p,¬q)","EG(¬q)","E[¬qU∧(¬p,¬q)]","∨(E[¬qU∧(¬p,¬q)],EG(¬q))","¬(∨(E[¬qU∧(¬p,¬q)],EG(¬q)))"};
        // String[] models2Var = {"∧(p,q)","∨(p,q)","→(p,q)","AG(→(p,q))","EG(→(p,q))","E[pUq]","A[pUq]","∧(¬p,¬q)","EG(¬q)","E[¬qU∧(¬p,¬q)]","∨(E[¬qU∧(¬p,¬q)],EG(¬q))","¬(∨(E[¬qU∧(¬p,¬q)],EG(¬q)))"};
        // String[] models2Var = {"A[pUq]","∧(¬p,¬q)","EG(¬q)","¬E[¬qU∧(¬p,¬q)]","¬(∨(E[¬qU∧(¬p,¬q)],EG(¬q)))"};
        // String[] models2Var = {"∧(p,q)","v(p,q)","→(p,q)","AG(→(p,q))","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))","AG(p→q)","EG(p→q)","E[pUq]","A[pUq]","E[qUp]","A[qUp]","A[¬qUp]","AG(¬(∧(r,v)))"};
        //
        // this is the Covid.ljx models
        // String[] models2Var = {"AG(¬∧(s,q))","AG(→(u,AF(v)))"};
        //
        // this is the banking model
        // String[] models2Var = {"¬∨(E[∧(s,¬q)U∧(t,¬∧(s,¬q))],EG(¬∧(s,¬q)))"}; // this gets the correct answer, verified by hand
        // String[] models2Var = {"A[¬tU∧(s,¬q)]"}; // this gets the correct answer, verified by hand.
        // String[] models2Var = {"∧(t,¬∧(s,¬q))"};
        // String[] models2Var = {"∧(s,¬q)"};
        String[] models2Var = {"¬t"};
        // String[] models2Var = {"EG(¬∧(s,¬q))","¬AF(∧(s,¬q))"};

        //
        // this is scratchwork for the banking model
        // String[] models2Var = {"t"};
        // String[] models2Var = {"¬(∧(E[∧(s,¬q)U∧(¬(¬t),¬∧(s,¬q))],EG(¬(∧(s,¬q)))))","A[(¬t)U∧(s,¬q)]"};
        // ¬(∨(E[¬qU∧(¬p,¬q)],EG(¬∧(s,¬q))))
        // p = ¬t
        // q = ∧(s,¬q)
        this.models = new String[][] {models1Var, models2Var};
    }

    public String[][] getModels() {
        return this.models;
    }

    public String[] getModels1Var() {
        return this.models[0];
    }

    public String[] getModels2Var() {
        return this.models[1];
    }

    // code strategy from https://www.tutorialspoint.com/javaexamples/arrays_merge.htm
    public String[] getModels1And2Var() {
        String[] models1And2Var = new String[this.models[0].length + this.models[1].length];
        int count = 0;
        for (int i=0; i<this.models[0].length; i++) {
            models1And2Var[i] = this.models[0][i];
            count++;
        }
        for (int j=0; j<this.models[1].length; j++) {
            models1And2Var[count++] = this.models[1][j];
        }
        return models1And2Var;
    }

}
