package Core.Image.ByteOfImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ByteOfImage implements Serializable {
    byte[] byteOfImage;

    public ByteOfImage(BufferedImage bufferedImage) throws IOException {
        if(bufferedImage==null){
            throw new NullPointerException("buffered image == null");
        }else {
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            this.byteOfImage = byteArrayOutputStream.toByteArray();
        }
    }

    public byte[] getByteOfImage() {
        return byteOfImage;
    }
}
