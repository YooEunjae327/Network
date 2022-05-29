package kr.hs.dgsw.network;

import java.io.*;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        File fl = new File("/Users/tomato/Documents/copy.png");
        FileInputStream fis = new FileInputStream(fl);

        FileOutputStream fos = new FileOutputStream("/Users/tomato/Documents/Java/copy.png");

        int readsize = 0;
        byte[] bytes = new byte[1024];

        while ((readsize =  fis.read(bytes)) != -1) {
            fos.write(bytes, 0, readsize);
        }
    }
}
