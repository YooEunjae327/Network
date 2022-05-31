package kr.co.swk.multichat.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static kr.co.swk.multichat.server.ServerBackground.Receiver.run;
import static kr.co.swk.multichat.server.ServerGui.*;

public class ServerBackground {

    // 지금까지 한일. GUi연동시키면서 서버Gui에 메시지띄움.
    // 다음 이슈. Gui 상에서 일단 1:1 채팅을 하고 싶다.
    private ServerSocket serverSocket;
    private Socket socket;
    private static ServerGui gui;
    private static String msg;

    /* XXX 03. 세번째 중요한것. 사용자들의 정보를 저장하는 맵입니다. */
    private static Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

    public final void setGui(ServerGui gui) {
        this.gui = gui;
    }

    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap);
        serverSocket = new ServerSocket(7777);


        while (true) {
            /* XXX 01. 첫번째. 서버가 할일 분담. 계속 접속받는것. */
            System.out.println("서버 대기중...");
            Socket socket  = serverSocket.accept(); // 먼저 서버가 할일은 계속 반복해서 사용자를 받는다.

            System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
            // 여기서 새로운 사용자 쓰레드 클래스 생성해서 소켓정보를 넣어줘야겠죠?!
            Receiver receiver = new Receiver(socket);
            run(socket);
            receiver.start();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerBackground serverBackground = new ServerBackground();
        serverBackground.setting();
    }

    // 맵의내용(클라이언트) 저장과 삭제
    public static void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage(nick + "님이 접속하셨습니다.");
        clientsMap.put(nick, out);
    }

    public static void removeClient(String nick) {
        sendMessage(nick + "님이 나가셨습니다.");
        clientsMap.remove(nick);
    }

    // 메시지 내용 전파
    public static void sendMessage(String msg) {
        Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // -----------------------------------------------------------------------------
    static class Receiver extends Thread {
        private static DataInputStream in;
        private DataOutputStream out;
        private static String nick;

        /** XXX 2. 리시버가 한일은 자기 혼자서 네트워크 처리 계속..듣기.. 처리해주는 것. */
        public Receiver(Socket socket) throws IOException {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            nick = in.readUTF();
            addClient(nick, out);
        }

        public static void run(Socket socket) {
            try {// 계속 듣기만!!
                System.out.print(nick);
                while (in != null) {
                    msg = in.readUTF();

                    sendMessage(msg);
                    gui.appendMsg(msg);

                    String[] ch= msg.split("\\s");

                    System.out.println(String.join("", ch));
                    if(msg.equals(nick + ":/파일목록\n")) {
                        ls();
                    } else if (String.join("", ch).equals(nick + ":/업로드")) {
                        Serverintput(socket);
                        System.out.println("while esc");
                    }


                }
            } catch (IOException e) {
                removeClient(nick);
            }
        }
    }
}