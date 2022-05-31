package kr.co.swk.multichat.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class ClientGui extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextArea jta = new JTextArea(40, 25);
    private static JTextField jtf = new JTextField(25);
    private static ClientBackground client = new ClientBackground();
    private static String nickName;

    public ClientGui() {

        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(800, 100, 400, 600);
        setTitle("클라이언트");

        client.setGui(this);
        client.setNickname(nickName);
        client.connet();
    }

    public static void main(String[] args) throws InterruptedException {
        String id;
        String ps;
        Scanner scanner = new Scanner(System.in);
//        while(true) {
//            System.out.println("정확한 아이디와 비밀번호를 입력하세요");
//
//            System.out.print("ID : ");
//            id = scanner.next();
//
//            System.out.print("Pssword : ");
//            ps = scanner.next();
//
//            if(id.equals("admin") && ps.equals("1234")) {
//                System.out.println("확인되었습니다.");
//                break;
//            }
//        }
        System.out.print("이제 닉네임부터 설정하세요 : ");
        nickName = scanner.next();
        scanner.close();

        new ClientGui();
    }

    @Override
    // 말치면 보내는 부분
    public void actionPerformed(ActionEvent e) {
        String a = nickName + ":" + jtf.getText() + "\n";
        client.sendMessage(a);
        jtf.setText("");
    }

    public void appendMsg(String msg) {
        jta.append(msg);
    }

    public static void clientOutput(Socket socket) throws IOException {



            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);

            File fl = new File("/Users/tomato/Documents/a.png");
            FileInputStream fis = new FileInputStream(fl);

            int readsize = 0;
            byte[] bytes = new byte[1024];

            dos.writeUTF(fl.getName());

            while ((readsize = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, readsize);
            }
            dos.flush();
            fis.close();

        System.out.println("--- 파일 전송 완료 ---");

    }
}
