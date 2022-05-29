package kr.hs.dgsw.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileCopyServer {

    public  static  void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5110);
        Socket sc = ss.accept();

        InputStream is = sc.getInputStream();
        BufferedInputStream bir = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bir);

        String filename = dis.readUTF();
        FileOutputStream fos = new FileOutputStream("/Users/tomato/Documents/copy" + filename);

        int readsize = 0;
        byte[] bytes = new byte[1024];

        while ((readsize =  dis.read(bytes)) != -1) {
            fos.write(bytes, 0, readsize);
        }
    }
}
