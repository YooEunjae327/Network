package kr.hs.dgsw.network.Java_ChattingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientThread extends Thread {
	private static List<ClientThread> ClientList = Collections.synchronizedList(new ArrayList<ClientThread>());
	
	private Socket sc = null;
	private Scanner scan = new Scanner(System.in);
	
	OutputStream os = null;
	PrintWriter pw = null;
	
	public ClientThread(Socket sc) {
		this.sc = sc;
		ClientList.add(this);
		
		try {
			os = sc.getOutputStream();
			pw = new PrintWriter(os, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		pw.println(msg);
	}
	public void run() {
		try {
			InputStream is = sc.getInputStream();		// 클라이언트 메시지 입력 받음
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while(true) {
				String brmsg = br.readLine();
				for(ClientThread cttmp:ClientList) {
					cttmp.sendMessage(brmsg);			// 출력
				}
			}
		} catch(Exception e) {
			System.out.println("연결 종료");
			e.printStackTrace();
			ClientList.remove(this);
		}
	}
}
