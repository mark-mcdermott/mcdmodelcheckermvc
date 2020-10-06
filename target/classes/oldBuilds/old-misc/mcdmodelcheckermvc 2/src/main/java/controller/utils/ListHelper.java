package controller.utils;

import _options.Options;
import controller.types.graph.VertexList;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;
import controller.types.graph.Vertex;

import static controller.utils.Utils.getLineNumber;

public class ListHelper {

    public String[] fetchFileListFromTwoDirs(String filepath1, String filepath2) throws ParseException {
        String[] fileList1 = fetchFileList(filepath1);
        String[] fileList2 = fetchFileList(filepath2);
        int combinedLen = fileList1.length + fileList2.length;
        String[] combinedList = new String[combinedLen];
        System.arraycopy(fileList1, 0, combinedList, 0, fileList1.length);
        System.arraycopy(fileList2, 0, combinedList, fileList1.length, fileList2.length);
        return combinedList;
    }

    public String[] fetchFileList(String filePath) throws ParseException {
        Vector<String> fileListVector;
        Vector<String> alphabatizedFileListVector;
        String[] fileListArr;
        fileListVector = getFilenamesVectorFromDisk(filePath);
        alphabatizedFileListVector = alphabatizeVector(fileListVector);
        fileListArr = vectorToArray(alphabatizedFileListVector);
        return fileListArr;
    }

    public int[] integerArrListToIntArr(ArrayList<Integer> integerArrList) {
        int[] intArr;
        intArr = integerArrList.stream().mapToInt(Integer::intValue).toArray();
        return intArr;
    }

    public int findIndexInArr(String[] array, String elem) {
        int index;
        for (int i=0; i<array.length; i++) {
            if (array[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    public String[] vectorToArray(Vector<String> vectorToConvert) {
        Object[] objArr;
        String[] array;
        objArr = vectorToConvert.toArray();
        array = Arrays.copyOf(objArr, objArr.length, String[].class);
        return array;
    }

    public Vector<String> alphabatizeVector(Vector<String> vectorToSort) {
        Collections.sort(vectorToSort);
        return vectorToSort;
    }

    public Vector<String> getFilenamesVectorFromDisk(String dirPath) {
        File folder;
        File[] fileArr;
        List<File> files;
        Vector<String> listItems;

        folder = new File(dirPath);
        fileArr = folder.listFiles();
        files = Arrays.asList(fileArr);
        listItems = new Vector<String>();
        files.forEach((file) -> {
            String filename = file.toPath().getFileName().toString();
            if (!filename.equals(".DS_Store")) {
                listItems.add(filename);
            }
        });
        return listItems;
    }

    /* from https://stackoverflow.com/posts/19844967/revisions */
    public String[] textFileLinesToArr(String filePath) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filePath));
        ArrayList<String> modelsArrList = new ArrayList<String>();
        while (file.hasNext()) {
            String line = file.next();
            modelsArrList.add(line);
        }
        String[] modelsArr = new String[modelsArrList.size()];
        for (int i=0; i<modelsArrList.size(); i++) {
            modelsArr[i] = modelsArrList.get(i);
        }
        file.close();
        return modelsArr;
    }

    public String[] listToStringArray(List list) {
        Object[] listObj = list.toArray();
        String[] listArr = new String[listObj.length];
        for (int i=0; i<listArr.length; i++) {
            String string = (String) listObj[i];
            listArr[i] = string;
        }
        return listArr;
    }

    public ArrayList<String> stringArrToArrList(String[] arr) {
        return new ArrayList<String>(Arrays.asList(arr));
    }

    public String[] getListSelections(JList list) {
        List selectionList = list.getSelectedValuesList();
        String[] selectionArr = listToStringArray(selectionList);
        return selectionArr;
    }

    public int getSelectedIndexFromString(String selectedString, JList list) {
        Integer selectedStringIndex = null;
        ListModel listModel = list.getModel();
        for (int i=0; i<listModel.getSize(); i++) {
            String thisListItem = listModel.getElementAt(i).toString();
            if (thisListItem.equals(selectedString)) {
                selectedStringIndex = i;
            }
        }
        if (selectedString == null) {
            new ExceptionMessage("Selected string not found in JList at ListHelper.java:133");
        }
        return selectedStringIndex;
    }

    public int getSelectedIndexFromNumberString(String selectedString, JList list) {
        Integer selectedStepInt = Integer.parseInt(selectedString);
        Integer selectedIndex = selectedStepInt;
        // selectedIndex = selectedStepInt - 2; // not sure why necessary
        return selectedIndex;
    }


    public int[] getSelectedIndicesFromStrings(String[] selectedStrings, JList list) {
        ArrayList<Integer> selectedIndicesArrList = new ArrayList<>();
        ListModel listModel = list.getModel();
        for (int i=0; i<listModel.getSize(); i++) {
            String thisListItem = listModel.getElementAt(i).toString();
            for (int j=0; j<selectedStrings.length; j++) {
                String thisSelectedString = selectedStrings[j];
                if (thisListItem.equals(thisSelectedString)) {
                    selectedIndicesArrList.add(i);
                }
            }
        }
        int[] selectedIndicesArr = new int[selectedIndicesArrList.size()];
        for (int i=0; i<selectedIndicesArrList.size(); i++) {
            selectedIndicesArr[i] = (int) selectedIndicesArrList.get(i);
        }
        return selectedIndicesArr;
    }

    // takes in vertexList and returns array of state numbers (ex: [0, 1, 2] which means s0, s1, s2)
    public Integer[] vertexListToIntArray(VertexList vertexList) {
        ArrayList<Vertex> vertexArrList = vertexList.getList();
        int vertexArrListLen = vertexArrList.size();

        // first get integer array (array of states' numbers, ex: [1, 2, 3])
        Integer[] stateListNums = new Integer[vertexArrListLen];
        for (int i = 0; i < vertexArrListLen; i++) {
            stateListNums[i] = vertexArrList.get(i).getNumber();
        }

        // https://howtodoinjava.com/java-programs/sort-string-array-alphabetically/
        // sort the integer array
        stateListNums = Stream.of(stateListNums).sorted().toArray(Integer[]::new);
        return stateListNums;
    }

    // takes in vertexList and returns string array of state names (ex: ["s0", "s1", "s2"])
    public String[] vertexListToStateNamesArr(VertexList vertexList) {
        Integer[] stateListNums = vertexListToIntArray(vertexList);

        // convert int array to string array (ex: ["s0", "s1", "s2"])
        String[] stateListStrings = new String[stateListNums.length];
        for (int i=0; i<stateListNums.length; i++) {
            stateListStrings[i] = "s" + stateListNums[i];
        }
        return stateListStrings;
    }

    // takes in vertexList and returns string arraylist of state names (ex: ["s0", "s1", "s2"])
    public ArrayList<String> vertexListToStateArrList(VertexList vertexList) {
        ArrayList<String> stateNamesArrList = new ArrayList<>();
        Integer[] stateListNums = vertexListToIntArray(vertexList);

        // convert int array to string array (ex: ["s0", "s1", "s2"])
        String[] stateListStrings = new String[stateListNums.length];
        for (int i=0; i<stateListNums.length; i++) {
            stateNamesArrList.add("s" + stateListNums[i]);
        }
        return stateNamesArrList;
    }

    /*
    public String[] getModelList(int numProperties, Options options) throws FileNotFoundException {
        String[] modelList = new String[0];
        ListHelper listHelper = new ListHelper();
        if (numProperties == 1) {
            modelList = listHelper.textFileLinesToArr(options.getModelsPath1Var());
        } else if (numProperties == 2) {
            modelList = listHelper.textFileLinesToArr(options.getModelsPath2Vars());
        } else {
            new ExceptionMessage("Controller.java " + getLineNumber() + ": Number of properties must be 1 or 2");
        }
        return modelList;
    }
    */

    // creates a list of vertices with new memory addresses, with same properties as the old ones
    public ArrayList<Vertex> copyVertexArrList(ArrayList<Vertex> origArrList) {
        ArrayList<Vertex> newArrList = new ArrayList<>();
        for (Vertex origVertex : origArrList) {
            newArrList.add(origVertex.copyVertex());
        }
        return newArrList;
    }

    public ArrayList<Vertex> copyVertexArrListSameMemAddresses(ArrayList<Vertex> origArrList) {
        ArrayList<Vertex> newArrList = new ArrayList<>();
        if (origArrList == null) {
            new ExceptionMessage("copying null array list at ListHelper:208");
        } else {
            for (Vertex vertex : origArrList) {
                if (vertex != null) {
                    newArrList.add(vertex);
                } else {
                    newArrList.add(null);
                }
            }
        }
        return newArrList;
    }

}
