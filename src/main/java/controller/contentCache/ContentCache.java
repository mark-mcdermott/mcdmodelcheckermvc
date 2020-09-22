package controller.contentCache;

import controller.content.Models;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Stores all possible content that must be read from disk.
 * Future reads will be read from here instead of from disk to prevent unnecessary slowdown.
 *
 */
public class ContentCache {

    File[] files;
    String[][] models;

    public ContentCache() {
        // files here
        models = new Models().getModels();
    }

    public String[] fetchXmlFiles(String filePath) {
        Vector<String> fileListVector;
        Vector<String> alphabatizedFileListVector;
        String[] fileListArr;
        fileListVector = getFilenamesVectorFromDisk(filePath);
        alphabatizedFileListVector = alphabatizeVector(fileListVector);
        fileListArr = vectorToArray(alphabatizedFileListVector);
        return fileListArr;
    }

    public File[] getFilesFromDir(String dirPath) {
        File folder;
        File[] fileArr;
        List<File> files;
        Vector<String> listItems;

        folder = new File(dirPath);
        fileArr = folder.listFiles();
        return fileArr;
    }

}
