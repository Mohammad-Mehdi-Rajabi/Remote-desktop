package Core.File.ByteOfFile;

import java.io.Serializable;

public class ByteOfFile implements Serializable {
    public static final long serialVersionUID = 66131865l;
    byte[] byteOfFile;

    public ByteOfFile(byte[] byteOfFile) {
        this.byteOfFile = byteOfFile;
    }

    public byte[] getByteOfFile() {
        return byteOfFile;
    }

    public void setByteOfFile(byte[] byteOfFile) {
        this.byteOfFile = byteOfFile;
    }
}
