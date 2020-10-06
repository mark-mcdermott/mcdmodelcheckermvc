package controller.filesCache;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Stores all files that must be read from disk.
 * Subsequent reads are from here instead of disk to prevent unnecessary slowdown.
 *
 */
public class FilesCache {

    File[] files;

    public FilesCache(String pathToFiles) {
        this.files = getFilesFromDir(pathToFiles);
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

    public File[] getFiles() {
        return files;
    }
}
