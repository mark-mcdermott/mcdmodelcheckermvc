package controller.filesCache;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Stores all files that must be read from disk
 * Future reads will be read from here instead of from disk to prevent unnecessary slowdown.
 *
 */
public class filesCache {

    File[] files;

    public filesCache(String pathToFiles) {
        files = getFilesFromDir(pathToFiles);
    }

    public File[] getFilesFromDir(String pathToFiles) {
        File folder;
        File[] fileArr;
        List<File> files;
        Vector<String> listItems;
        folder = new File(pathToFiles);
        fileArr = folder.listFiles();
        return fileArr;
    }

}
