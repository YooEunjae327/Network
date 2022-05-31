package kr.co.swk.multichat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static kr.co.swk.multichat.client.ClientGui.clientOutput;

public class ClientBackground {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientGui gui;
    private String msg;
    private String nickName;

    public final void setGui(ClientGui gui) {
        this.gui = gui;
    }

    public void connet() {
        try {
            Socket socket = new Socket("10.80.161.182", 7777);
            System.out.println("서버 연결됨.");

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF(nickName);
            while (true) {
                msg = in.readUTF();
                System.out.println(out);
                String[] ch= msg.split("\s");


                if (String.join("", ch).equals(nickName+ ":/업로드\n")) {
                    clientOutput(socket);
                }
                gui.appendMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientBackground clientBackground = new ClientBackground();
        clientBackground.connet();
    }

    public void sendMessage(String msg2) {
        try {
            out.writeUTF(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

}
