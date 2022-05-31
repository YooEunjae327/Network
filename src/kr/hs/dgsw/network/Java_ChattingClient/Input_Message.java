package kr.hs.dgsw.network.Java_ChattingClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Input_Message extends Thread {
	private Socket sc = null;

	
	public Input_Message(Socket sc) {
		this.sc = sc;
	}
	
	public void run() {
		try {
			InputStream is = sc.getInputStream();		// 클라이언트 메시지 입력 받음
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while(true) {
				System.out.println(br.readLine());			// 출력
			}
		} catch(Exception e) {
			System.out.println("연결 종료");
			e.printStackTrace();
		}
	}
}
