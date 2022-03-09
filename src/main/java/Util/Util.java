package Util;

import java.io.File;

public class Util {
    /**
     * static method to delete all file in directory
     * @param dirPath path of directory
     */
    public static void deleteFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        for(File file : filesList) {
            if(file.isFile()) {
                file.delete();
            } else {
                deleteFiles(file);
            }
        }
    }
}
