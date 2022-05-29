package kr.hs.dgsw.network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileCopyClient {
    public static  void main(String[] args) throws UnknownHostException, IOException {
        Socket sc = new Socket("10.80.162.4", 5110);

        OutputStream os = sc.getOutputStream();
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);

        File fl = new File("/Users/tomato/Documents/a.png");
        FileInputStream fis = new FileInputStream(fl);

        int readsize = 0;
        byte[] bytes = new byte[1024];

        dos.writeUTF(fl.getName());

        while ((readsize =  fis.read(bytes)) != -1) {
            dos.write(bytes, 0, readsize);
        }
    }
}
