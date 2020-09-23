package controller.content;

import controller.types.DisplayType;

public class DisplayTypes {

    private String[] displayTypes;

    public DisplayTypes() {
        displayTypes = displayTypesStrArr();
    }

    public String[] displayTypesStrArr() {
        DisplayType[] enumArr = DisplayType.values();
        int enumArrLen = enumArr.length;
        String[] enumStrArr = new String[enumArrLen];
        for (int i=0; i<enumArr.length; i++) {
            enumStrArr[i] = enumArr[i].toString();
        }
        return enumStrArr;
    }

    public String[] getDisplayTypes() {
        return displayTypes;
    }

}
