package kr.hs.dgsw.network.Java_ChattingServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ChattingServerMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5010);  	// 서버 시작(클라이언트 접속 대기)

			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				Socket sc = ss.accept();
				System.out.println(sc.getInetAddress() +": 접속하였습니다.");

				ClientThread ct = new ClientThread(sc);
				ct.start();
			}
		} catch(Exception e) {
			System.out.println("연결 종료");
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch(IOException e) {}
		}
	}
}