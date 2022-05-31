package kr.co.swk.multichat.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static kr.co.swk.multichat.server.ServerBackground.Receiver.run;

public class ServerGui extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextArea jta = new JTextArea(40, 25);
    private JTextField jtf = new JTextField(25);
    private static   ServerBackground server = new ServerBackground();

    public ServerGui() throws IOException {

        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(200, 100, 400, 600);
        setTitle("서버부분");

        server.setGui(this);
        server.setting();
    }

    public static void main(String[] args) throws IOException {
        new ServerGui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "서버 : " + jtf.getText() + "\n";
        System.out.print(msg);
        server.sendMessage(msg);
        jtf.setText("");
    }

    public void appendMsg(String msg) {
        jta.append(msg);
    }

    public static void ls() {
        File f = null;
        String[] paths;
        int ch = 0;

        try{
            f = new File("/Users/tomato/Documents/filefolder");
            paths = f.list();

            server.sendMessage("--- File List ---\n" );
            for(String path:paths) {
                if(path.equals(".DS_Store")) continue;
                server.sendMessage(path + "\n");
                ch++;
            }
            server.sendMessage("--- " + ch + "개 파일 ---\n" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Serverintput(Socket socket) throws IOException {


        InputStream is = socket.getInputStream();
        BufferedInputStream bir = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bir);

        String filename = dis.readUTF();
        System.out.println(filename);
        FileOutputStream fos = new FileOutputStream("/Users/tomato/Documents/Java/" + filename);

        int readsize = 0;
        byte[] bytes = new byte[1024];

        while ((readsize =  dis.read(bytes)) != -1) {
            fos.write(bytes, 0, readsize);
        }
        socket.close();

        System.out.println("파일받기");
    }

}