package si.fri.rso.rsoimageupload.lib;

import java.io.*;
import java.util.Base64;

public class Base64FileConverter {

    public static File base64ToFile(String base64File) throws IOException {
        byte[] imgBytesData = Base64.getDecoder().decode(base64File);

        File file = File.createTempFile("image", ".png");

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
