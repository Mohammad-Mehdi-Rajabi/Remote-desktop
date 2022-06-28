package Core.File.FileList;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileList implements Serializable {
    public static final long serialVersionUID = 258963111110l;
    private HashMap<String, Boolean> fileList;

    public FileList(File[] files) {
        this.fileList = new HashMap<>();
        for (File f : files) {
            fileList.put(f.getPath(), f.isDirectory());
        }
    }

    public HashMap<String, Boolean> getFileList() {
        return fileList;
    }

    public void setFileList(HashMap<String, Boolean> fileList) {
        this.fileList = fileList;
    }
}
