package controller.content.staticContent;

public class Models {

    String[][] models; // array with two elements, each a String[]: models1Var & models2Var

    public Models() {
        String[] models1Var = {"⊤","⊥","p","¬p","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))"};
        // String[] models2Var = {"∧(p,q)","v(p,q)","→(p,q)","AG(→(p,q))","EX(p)","AX(p)","EG(p)","AG(p)","EF(p)","AF(p)","EX(AF(p))","AG(EF(p))","AG(p→q)","EG(pq)","E[pUq]","A[pUq]","E[qUp]","A[qUp]","A[¬qUp]","AG(¬(∧(r,v)))"};
        String[] models2Var = {"AG(¬∧(s,q))","AG(→(u,AF(v)))","AG(¬∧(p,q))"};
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
}
