package controller.utils;

import javax.swing.*;

public class Utils {

    public static int getIndexFromListElem(String listElem, JList list) {
        ListModel listModel = list.getModel();
        int numElems = listModel.getSize();
        Boolean found = false;
        for (int i=0; i<numElems; i++) {
            if (listModel.getElementAt(i).equals(listElem)) { found = true; return i; };
        }
        if (!found) { new ExceptionMessage("String not found in JList in getIndexFromListElem(). Utils.java"); }
        return -1;
    }

    public static int[] getIndicesFromListElems(String[] listElems, JList list) {
        int[] listIndices = new int[listElems.length];
        for (int i=0; i< listElems.length; i++) {
            listIndices[i] = getIndexFromListElem(listElems[i], list);
        }
        return listIndices;
    }

    // from https://stackoverflow.com/a/115027, accessed 9/22/20
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

}
